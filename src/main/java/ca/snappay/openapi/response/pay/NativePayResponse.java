package ca.snappay.openapi.response.pay;

import ca.snappay.openapi.response.OpenApiResponse;
import lombok.Data;

/**
 * The response for native payment.
 *
 * @author shawndu
 * @version 1.0
 */
@Data
public class NativePayResponse extends OpenApiResponse<NativePayResponseData> {

    @Override
    public String toString() {
        return super.toString();
    }

}
