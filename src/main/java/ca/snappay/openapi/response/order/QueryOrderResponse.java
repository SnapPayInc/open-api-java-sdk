package ca.snappay.openapi.response.order;

import ca.snappay.openapi.response.OpenApiResponse;
import lombok.Data;
import lombok.ToString;

/**
 * The response for order query.
 *
 * @author shawndu
 * @version 1.0
 */
@Data
@ToString(callSuper = true)
public class QueryOrderResponse extends OpenApiResponse<QueryOrderResponseData> {
}
