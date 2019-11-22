package ca.snappay.openapi.response.misc;

import ca.snappay.openapi.response.OpenApiResponse;
import lombok.Data;
import lombok.ToString;

/**
 * The response for order revoke.
 *
 * @author shawndu
 * @version 1.0
 */
@Data
@ToString(callSuper = true)
public class QueryExchangeRateResponse extends OpenApiResponse<QueryExchangeRateResponseData> {
}
