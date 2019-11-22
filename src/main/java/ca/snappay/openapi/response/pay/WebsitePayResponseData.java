package ca.snappay.openapi.response.pay;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.ToString;

/**
 * The response data for website payment.
 *
 * @author shawndu
 * @version 1.0
 */
@Data
@ToString(callSuper = true)
public class WebsitePayResponseData extends AbstractPayResponseData {

    @SerializedName("webpay_url")
    private String webpayUrl;

}
