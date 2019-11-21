package ca.snappay.openapi.response.pay;

import ca.snappay.openapi.response.OpenApiResponse;
import lombok.Data;
import lombok.ToString;

/**
 * The response for barcode payment.
 *
 * @author shawndu
 * @version 1.0
 */
@Data
@ToString(callSuper = true)
public class BarCodePayResponse extends OpenApiResponse<BarCodePayResponseData> {
}
