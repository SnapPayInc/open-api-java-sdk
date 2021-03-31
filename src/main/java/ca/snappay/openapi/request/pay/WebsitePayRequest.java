package ca.snappay.openapi.request.pay;

import ca.snappay.openapi.constant.BrowserType;
import ca.snappay.openapi.constant.PaymentMethod;
import ca.snappay.openapi.response.pay.WebsitePayResponse;
import java.util.EnumSet;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * The request for website payment.
 *
 * @author shawndu
 * @version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
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

    public WebsitePayRequest(PaymentMethod paymentMethod, String orderNo, Double amount, String description) {
      setPaymentMethod(paymentMethod);
      setOrderNo(orderNo);
      setAmount(amount);
      setDescription(description);
  }

    @Override
    public void validate() {
        super.validate();

        if ((getPaymentMethod() == PaymentMethod.UNIONPAY || getPaymentMethod() == PaymentMethod.CREDITCARD_PAYBYTOKEN)
                && browserType == BrowserType.WAP) {
            throw new IllegalArgumentException("WAP browser is not supported");
        }
        validateLength("returnUrl", returnUrl, 256);
    }

    @Override
    protected EnumSet<PaymentMethod> applicablePaymentMethods() {
        return EnumSet.of(PaymentMethod.ALIPAY, PaymentMethod.UNIONPAY, PaymentMethod.CREDITCARD_PAYBYTOKEN);
    }

}
