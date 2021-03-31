package ca.snappay.openapi.response.pay;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * The response data for QR code payment.
 *
 * @author shawndu
 * @version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class QRCodePayResponseData extends AbstractPayResponseData {

    @SerializedName("qrcode_url")
    private String qrCodeUrl;

}
