package ca.snappay.openapi.request.order;

import ca.snappay.openapi.response.order.RevokeOrderResponse;

/**
 * The response for order revoke.
 *
 * @author shawndu
 * @version 1.0
 */
public class RevokeOrderRequest extends AbstractOrderRequest<RevokeOrderResponse> {

    private static final String REQUEST_METHOD = "pay.ordercancel";

    @Override
    public String getRequestMethod() {
        return REQUEST_METHOD;
    }

}
