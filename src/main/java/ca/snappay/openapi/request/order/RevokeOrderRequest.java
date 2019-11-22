package ca.snappay.openapi.request.order;

import ca.snappay.openapi.response.order.RevokeOrderResponse;
import lombok.ToString;

/**
 * The response for order revoke.
 *
 * @author shawndu
 * @version 1.0
 */
@ToString(callSuper = true)
public class RevokeOrderRequest extends AbstractOrderRequest<RevokeOrderResponse> {

    private static final String REQUEST_METHOD = "pay.ordercancel";

    @Override
    public String getRequestMethod() {
        return REQUEST_METHOD;
    }

}
