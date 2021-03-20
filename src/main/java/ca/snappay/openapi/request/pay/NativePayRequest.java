package ca.snappay.openapi.request.pay;

import ca.snappay.openapi.constant.PaymentMethod;
import ca.snappay.openapi.response.pay.NativePayResponse;
import java.util.EnumSet;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * The request for native payment.
 *
 * @author shawndu
 * @version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class NativePayRequest extends AbstractPayRequest<NativePayResponse> {

    private static final String REQUEST_METHOD = "pay.inapppay";

    @SerializedName("refer_url")
    private String referUrl;

    @Override
    public String getRequestMethod() {
        return REQUEST_METHOD;
    }

    public NativePayRequest(PaymentMethod paymentMethod, String orderNo, Double amount, String description) {
        setPaymentMethod(paymentMethod);
        setOrderNo(orderNo);
        setAmount(amount);
        setDescription(description);
    }

    @Override
    public void validate() {
        super.validate();

        validateRequired("referUrl", referUrl);
        validateLength("referUrl", referUrl, 256);
    }

    @Override
    protected EnumSet<PaymentMethod> applicablePaymentMethods() {
        return EnumSet.of(PaymentMethod.ALIPAY, PaymentMethod.WECHATPAY);
    }

}
