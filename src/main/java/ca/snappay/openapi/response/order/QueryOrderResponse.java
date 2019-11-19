package ca.snappay.openapi.response.order;

import ca.snappay.openapi.response.OpenApiResponse;
import lombok.Data;

/**
 * The response for order query.
 *
 * @author shawndu
 * @version 1.0
 */
@Data
public class QueryOrderResponse extends OpenApiResponse<QueryOrderResponseData> {

    @Override
    public String toString() {
        return super.toString();
    }

}
