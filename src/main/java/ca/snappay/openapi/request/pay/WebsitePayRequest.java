package ca.snappay.openapi.request.pay;

import ca.snappay.openapi.constant.BrowserType;
import ca.snappay.openapi.constant.PaymentMethod;
import ca.snappay.openapi.response.pay.WebsitePayResponse;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.ToString;

/**
 * The request for website payment.
 *
 * @author shawndu
 * @version 1.0
 */
@Data
@ToString(callSuper = true)
public class WebsitePayRequest extends AbstractPayRequest<WebsitePayResponse> {

    private static final String REQUEST_METHOD = "pay.webpay";

    @SerializedName("browser_type")
    private BrowserType browserType;

    @SerializedName("return_url")
    private String returnUrl;

    @Override
    public String getRequestMethod() {
        return REQUEST_METHOD;
    }

    @Override
    public void validate() {
        super.validate();

        if (getPaymentMethod() == PaymentMethod.WECHATPAY) {
            throw new IllegalArgumentException("WeChatPay does not support website payment");
        }
        if (getPaymentMethod() == PaymentMethod.UNIONPAY && browserType == BrowserType.WAP) {
            throw new IllegalArgumentException("UnionPay does not support WAP browser");
        }
        validateLength("returnUrl", returnUrl, 256);
    }

}
