package ca.snappay.openapi.request.pay;

import ca.snappay.openapi.constant.PaymentMethod;
import ca.snappay.openapi.response.pay.MiniPayResponse;
import java.util.EnumSet;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * The request for mini payment.
 *
 * @author shawndu
 * @version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class MiniPayRequest extends AbstractPayRequest<MiniPayResponse> {

    private static final String REQUEST_METHOD = "pay.minipay";

    @SerializedName("pay_user_account_id")
    private String payUserAccountId;

    @SerializedName("notify_url")
    private String notifyUrl;

    @Override
    public String getRequestMethod() {
        return REQUEST_METHOD;
    }

    public MiniPayRequest(PaymentMethod paymentMethod, String orderNo, Double amount, String description, String payUserAccountId) {
      setPaymentMethod(paymentMethod);
      setOrderNo(orderNo);
      setAmount(amount);
      setDescription(description);
      this.payUserAccountId = payUserAccountId;
  }

    @Override
    public void validate() {
        super.validate();

        validateRequired("payUserAccountId", payUserAccountId);
        validateLength("payUserAccountId", payUserAccountId, 128);
        validateLength("notifyUrl", notifyUrl, 256);
    }

    @Override
    protected EnumSet<PaymentMethod> applicablePaymentMethods() {
        return EnumSet.of(PaymentMethod.WECHATPAY);
    }

}
