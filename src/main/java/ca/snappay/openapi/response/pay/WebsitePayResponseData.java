package ca.snappay.openapi.response.pay;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

/**
 * The response data for website payment.
 *
 * @author shawndu
 * @version 1.0
 */
@Data
public class WebsitePayResponseData extends AbstractPayResponseData {

    @SerializedName("webpay_url")
    private String webpayUrl;

}
