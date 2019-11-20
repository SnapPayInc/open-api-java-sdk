package ca.snappay.openapi;

import ca.snappay.openapi.request.OpenApiRequest;
import ca.snappay.openapi.response.OpenApiResponse;

/**
 * The OpenAPI client.
 *
 * @author shawndu
 * @version 1.0
 */
public interface OpenApiClient {

    /**
     * Executes an OpenAPI request.
     *
     * @param <T>     the type of response.
     * @param request the request.
     * @return the response.
     * @throws OpenApiException if any error occurred.
     */
    public <T extends OpenApiResponse> T execute(OpenApiRequest<T> request) throws OpenApiException;

}
