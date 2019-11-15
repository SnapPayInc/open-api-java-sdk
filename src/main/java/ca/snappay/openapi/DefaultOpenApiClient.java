package ca.snappay.openapi;

import ca.snappay.openapi.config.ConfigurationHolder;
import ca.snappay.openapi.config.OpenApiConfigurationExcepiton;
import ca.snappay.openapi.config.provider.DefaultConfigurationProvider;
import ca.snappay.openapi.request.OpenApiRequest;
import ca.snappay.openapi.sign.SignHandler;
import ca.snappay.openapi.constant.Constants;
import ca.snappay.openapi.response.OpenApiResponse;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * The default implementation of <code>OpenApiClient</code> using Apache HttpClient.
 *
 * @author shawndu
 * @version 1.0
 */
public class DefaultOpenApiClient implements OpenApiClient {

    private static final Log log = LogFactory.getLog(DefaultOpenApiClient.class);

    private static final String GATEWAY_PATH = "/api/gateway";

    private final ConfigurationHolder config;
    private final URI requestUri;

    private final CloseableHttpClient httpClient;

    /**
     * Creates an instance of this client using the given configuration.
     *
     * @param config the configuration.
     * @throws OpenApiConfigurationExcepiton if the given configuration is not valid.
     */
    public DefaultOpenApiClient(ConfigurationHolder config) throws OpenApiConfigurationExcepiton {
        this.config = config;
        this.config.validate();

        httpClient = HttpClients.createDefault();
        try {
            requestUri = new URIBuilder().setScheme("https").setHost(config.getGatewayHost()).setPath(GATEWAY_PATH).build();
        } catch (URISyntaxException e) {
            throw new OpenApiConfigurationExcepiton("Gateway host is invalid", e);
        }
    }

    /**
     * Creates an instance of this client using the default configuration provider chain.
     *
     * @throws OpenApiConfigurationExcepiton if the configuration cannot be determined.
     */
    public DefaultOpenApiClient() throws OpenApiConfigurationExcepiton {
        this(DefaultConfigurationProvider.create().resolveConfiguration());
    }

    public <T extends OpenApiResponse> T execute(OpenApiRequest<T> request) throws OpenApiException {
        // convert the request object to JsonObject
        JsonObject requestParams = SignHandler.GSON.toJsonTree(request).getAsJsonObject();

        // build common parameters
        buildCommonParameters(config.getAppId(), request.getRequestMethod(), requestParams);

        // sign
        String signStr = SignHandler.sign(config, requestParams);
        if (StringUtils.isEmpty(signStr)) {
            throw new OpenApiException("-101", "Sign failed");
        }
        requestParams.addProperty(Constants.SIGN, signStr);

        // send request, and process response
        String resultStr = null;
        CloseableHttpResponse response = null;
        try {
            String requestStr = SignHandler.GSON.toJson(requestParams);
            log.debug("Request to OpenAPI gateway, send data[" + requestStr + "]");
            HttpEntity body = new StringEntity(requestStr, ContentType.APPLICATION_JSON);

            HttpPost post = new HttpPost(requestUri);
            post.addHeader("Accept-Language", config.getLanguage().getLanguage());
            post.setEntity(body);

            response = httpClient.execute(post);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != 200) {
                throw new OpenApiException("-102", "Request to OpenAPI gateway failed with status code " + statusCode);
            }

            HttpEntity entity = response.getEntity();
            resultStr = EntityUtils.toString(entity, Constants.CHARSET_UTF8);
            log.debug("Response from OpenAPI gateway, receive data[" + resultStr + "]");
        } catch (Exception e) {
            log.error("Request to OpenAPI gateway failed", e);
            throw new OpenApiException("-102", "Request to open service gateway fail");
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    // ignore
                }
            }
        }

        JsonObject resultJson = JsonParser.parseString(resultStr).getAsJsonObject();

        boolean isPass = SignHandler.verifySign(config, resultJson);
        if (!isPass) {
            throw new OpenApiException("-103", "Response data signature is not valid");
        }

        // convert to response object
        if (resultJson.get(Constants.TOTAL).getAsNumber().intValue() == 1) {
            JsonObject data = resultJson.get(Constants.DATA).getAsJsonArray().get(0).getAsJsonObject();
            for (String key : data.keySet()) {
                resultJson.add(key, data.get(key));
            }
        }
        T resp = SignHandler.GSON.fromJson(resultJson, request.getResponseClass());
        return resp;
    }

    private void buildCommonParameters(String appId, String requestMethod, JsonObject requestParams) {
        requestParams.addProperty(Constants.APP_ID, appId);
        requestParams.addProperty(Constants.METHOD, requestMethod);
        requestParams.addProperty(Constants.FORMAT, config.getFormat());
        requestParams.addProperty(Constants.CHARSET, config.getCharset());
        requestParams.addProperty(Constants.VERSION, config.getVersion());
        requestParams.addProperty(Constants.SIGN_TYPE, config.getSignType().name());
    }

}
