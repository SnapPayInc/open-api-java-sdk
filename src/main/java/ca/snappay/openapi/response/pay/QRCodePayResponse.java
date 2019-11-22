package ca.snappay.openapi.response.pay;

import ca.snappay.openapi.response.OpenApiResponse;
import lombok.Data;
import lombok.ToString;

/**
 * The response for QR code payment.
 *
 * @author shawndu
 * @version 1.0
 */
@Data
@ToString(callSuper = true)
public class QRCodePayResponse extends OpenApiResponse<QRCodePayResponseData> {
}
