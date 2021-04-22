/*
 * Copyright 2021 SnapPay Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ca.snappay.openapi;

import ca.snappay.openapi.config.ConfigurationHolder;
import ca.snappay.openapi.config.OpenApiConfigurationExcepiton;
import ca.snappay.openapi.config.provider.DefaultConfigurationProvider;
import ca.snappay.openapi.request.OpenApiRequest;
import ca.snappay.openapi.request.card.ActivateCardRequest;
import ca.snappay.openapi.request.card.QueryCardRequest;
import ca.snappay.openapi.request.card.RefundCardRequest;
import ca.snappay.openapi.request.misc.QueryExchangeRateRequest;
import ca.snappay.openapi.request.order.QueryOrderRequest;
import ca.snappay.openapi.request.order.RefundOrderRequest;
import ca.snappay.openapi.request.order.RevokeOrderRequest;
import ca.snappay.openapi.request.pay.BarCodePayRequest;
import ca.snappay.openapi.request.pay.H5PayRequest;
import ca.snappay.openapi.request.pay.MiniPayRequest;
import ca.snappay.openapi.request.pay.NativePayRequest;
import ca.snappay.openapi.request.pay.QRCodePayRequest;
import ca.snappay.openapi.request.pay.WebsitePayRequest;
import ca.snappay.openapi.response.pay.BarCodePayResponse;
import ca.snappay.openapi.response.pay.H5PayResponse;
import ca.snappay.openapi.response.pay.MiniPayResponse;
import ca.snappay.openapi.response.pay.NativePayResponse;
import ca.snappay.openapi.response.pay.QRCodePayResponse;
import ca.snappay.openapi.response.pay.WebsitePayResponse;
import ca.snappay.openapi.sign.SignHandler;
import lombok.Getter;
import lombok.Setter;
import ca.snappay.openapi.constant.Constants;
import ca.snappay.openapi.extension.AlternativeOrderNumberGenerator;
import ca.snappay.openapi.extension.DefaultAlternativeOrderNumberGenerator;
import ca.snappay.openapi.response.OpenApiResponse;
import ca.snappay.openapi.response.card.ActivateCardResponse;
import ca.snappay.openapi.response.card.QueryCardResponse;
import ca.snappay.openapi.response.card.RefundCardResponse;
import ca.snappay.openapi.response.misc.QueryExchangeRateResponse;
import ca.snappay.openapi.response.order.QueryOrderResponse;
import ca.snappay.openapi.response.order.RefundOrderResponse;
import ca.snappay.openapi.response.order.RevokeOrderResponse;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The default implementation of <code>OpenApiClient</code> using Apache HttpClient.
 *
 * @author shawndu
 * @version 1.1
 * @since 1.0
 */
public class DefaultOpenApiClient implements OpenApiClient {

    private static final Log log = LogFactory.getLog(DefaultOpenApiClient.class);

    private static final String GATEWAY_PATH = "/api/gateway";

    private static final String INSUFFICIENT_BALANCE_ERROR_CODE = "E066006";
    private static final Pattern CARD_BALANCE_PATTERN = Pattern.compile("current balance is \\((\\d+\\.?\\d*)\\)");
    
    private final ConfigurationHolder config;
    private final URI requestUri;

    private final CloseableHttpClient httpClient;

    @Getter
    @Setter
    private AlternativeOrderNumberGenerator alternativeOrderNumberGenerator = new DefaultAlternativeOrderNumberGenerator();

    /**
     * Creates an instance of this client using the given configuration.
     *
     * @param config the configuration.
     * @throws OpenApiConfigurationExcepiton if the given configuration is not valid.
     */
    public DefaultOpenApiClient(ConfigurationHolder config) throws OpenApiConfigurationExcepiton {
        this.config = config;
        this.config.validate();

        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(config.getConnectionTimeout() * 1000)
                .setSocketTimeout(config.getReadTimeout() * 1000).build();
        httpClient = HttpClients.custom().setDefaultRequestConfig(requestConfig).build();
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

    @Override
    public BarCodePayResponse barCodePay(BarCodePayRequest request) throws OpenApiException {
        BarCodePayResponse response = execute(request);

        // if order split is supported and the API returns insufficient balance
        // submit another request which will use off the remaining balance and return a partial order
        if (config.isPartialPaymentSupported() && request.isSnapliiPayment()) {
            if (INSUFFICIENT_BALANCE_ERROR_CODE.equals(response.getCode())) {
                Matcher balanceMatcher = CARD_BALANCE_PATTERN.matcher(response.getMessage());
                if (balanceMatcher.matches()) {
                    double balance = Double.parseDouble(balanceMatcher.group(1));
                    double initialAmount = request.getAmount();
                    // new payment amount should be the same as current balance
                    request.setAmount(balance);
                    // new order number is required since the acquiring system does not allow duplicate order numbers
                    request.setOrderNo(alternativeOrderNumberGenerator.generate(config, request.getOrderNo()));
                    response = execute(request);
                    // if payment is successful, set additional fields for order split
                    if (response.isSuccessful()) {
                        response.getData().get(0).setPartialPayment(true);
                        response.getData().get(0).setTotalAmount(initialAmount);
                        response.getData().get(0).setOutstandingAmount(initialAmount - balance);
                    }
                }
            }
        }
        return response;
    }

    @Override
    public QRCodePayResponse qrCodePay(QRCodePayRequest request) throws OpenApiException {
        return execute(request);
    }

    @Override
    public H5PayResponse h5Pay(H5PayRequest request) throws OpenApiException {
        return execute(request);
    }

    @Override
    public NativePayResponse nativePay(NativePayRequest request) throws OpenApiException {
        return execute(request);
    }

    @Override
    public WebsitePayResponse websitePay(WebsitePayRequest request) throws OpenApiException {
        return execute(request);
    }

    @Override
    public MiniPayResponse miniPay(MiniPayRequest request) throws OpenApiException {
        return execute(request);
    }

    @Override
    public QueryOrderResponse queryOrder(QueryOrderRequest request) throws OpenApiException {
        return execute(request);
    }

    @Override
    public RevokeOrderResponse revokeOrder(RevokeOrderRequest request) throws OpenApiException {
        return execute(request);
    }

    @Override
    public RefundOrderResponse refundOrder(RefundOrderRequest request) throws OpenApiException {
        return execute(request);
    }

    @Override
    public QueryExchangeRateResponse queryExchangeRate(QueryExchangeRateRequest request) throws OpenApiException {
        return execute(request);
    }

    @Override
    public ActivateCardResponse activateCard(ActivateCardRequest request) throws OpenApiException {
        return execute(request);
    }

    @Override
    public QueryCardResponse queryCard(QueryCardRequest request) throws OpenApiException {
        return execute(request);
    }

    @Override
    public RefundCardResponse refundCard(RefundCardRequest request) throws OpenApiException {
        return execute(request);
    }

    @Override
    public <T extends OpenApiResponse<?>> T execute(OpenApiRequest<T> request) throws OpenApiException {
        request.validate();

        // convert the request object to JsonObject
        JsonObject requestParams = SignHandler.GSON.toJsonTree(request).getAsJsonObject();

        // build common parameters
        buildCommonParameters(request.getRequestMethod(), request.needMerchant(), requestParams);

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
            throw new OpenApiException("-102", "Request to open service gateway fail", e);
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
        T resp = SignHandler.GSON.fromJson(resultJson, request.getResponseClass());
        return resp;
    }

    /**
     * Gets the configuration used by this client.
     *
     * @return the configuration.
     */
    public ConfigurationHolder getConfig() {
        return config;
    }

    private void buildCommonParameters(String requestMethod, boolean needMerchant, JsonObject requestParams) {
        requestParams.addProperty(Constants.APP_ID, config.getAppId());
        requestParams.addProperty(Constants.METHOD, requestMethod);
        if (needMerchant) {
            requestParams.addProperty(Constants.MERCHANT_NO, config.getMerchantNo());
        }
        requestParams.addProperty(Constants.FORMAT, config.getFormat());
        requestParams.addProperty(Constants.CHARSET, config.getCharset());
        requestParams.addProperty(Constants.VERSION, config.getVersion());
        requestParams.addProperty(Constants.SIGN_TYPE, config.getSignType().name());
    }

}
