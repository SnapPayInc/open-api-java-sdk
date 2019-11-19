package ca.snappay.openapi.response.pay;

import ca.snappay.openapi.response.OpenApiResponseData;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

/**
 * The response data for QR code payment.
 *
 * @author shawndu
 * @version 1.0
 */
@Data
public class QRCodePayResponseData extends AbstractPayResponseData {

    @SerializedName("qrcode_url")
    private String qrCodeUrl;

}
