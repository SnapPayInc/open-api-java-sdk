package ca.snappay.openapi.request.pay;

import ca.snappay.openapi.constant.PaymentMethod;
import ca.snappay.openapi.response.pay.BarCodePayResponse;
import java.util.EnumSet;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * The request for barcode payment.
 *
 * @author shawndu
 * @version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class BarCodePayRequest extends AbstractPayRequest<BarCodePayResponse> {

    private static final String REQUEST_METHOD = "pay.barcodepay";

    @SerializedName("auth_code")
    private String authCode;

    @Override
    public String getRequestMethod() {
        return REQUEST_METHOD;
    }

    public BarCodePayRequest(String orderNo, Double amount, String description, String authCode) {
        setOrderNo(orderNo);
        setAmount(amount);
        setDescription(description);
        this.authCode = authCode;
    }

    @Override
    public void validate() {
        super.validate();

        validateRequired("authCode", authCode);
        validateLength("authCode", authCode, 32);
    }

    @Override
    protected EnumSet<PaymentMethod> applicablePaymentMethods() {
        return EnumSet.of(PaymentMethod.ALIPAY, PaymentMethod.WECHATPAY, PaymentMethod.UNIONPAY_QR,
            PaymentMethod.SNAPLII);
    }

    @Override
    protected boolean isPaymentMethodRequired() {
        return false;
    }

}
