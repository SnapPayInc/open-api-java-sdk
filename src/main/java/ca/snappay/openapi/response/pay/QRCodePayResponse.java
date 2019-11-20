package ca.snappay.openapi.response.pay;

import ca.snappay.openapi.response.OpenApiResponse;
import lombok.Data;

/**
 * The response for QR code payment.
 *
 * @author shawndu
 * @version 1.0
 */
@Data
public class QRCodePayResponse extends OpenApiResponse<QRCodePayResponseData> {

    @Override
    public String toString() {
        return super.toString();
    }

}
