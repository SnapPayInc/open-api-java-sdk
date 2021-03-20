package ca.snappay.openapi.request.pay;

import ca.snappay.openapi.constant.PaymentChannelTradeType;
import ca.snappay.openapi.constant.PaymentMethod;
import ca.snappay.openapi.response.pay.H5PayResponse;
import java.util.EnumSet;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * The request for H5 payment.
 *
 * @author shawndu
 * @version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class H5PayRequest extends AbstractPayRequest<H5PayResponse> {

    private static final String REQUEST_METHOD = "pay.h5pay";

    @SerializedName("pay_channel_trade_type")
    private PaymentChannelTradeType tradeType;

    @SerializedName("pay_user_account_id")
    private String userAccountId;

    @SerializedName("return_url")
    private String returnUrl;

    @Override
    public String getRequestMethod() {
        return REQUEST_METHOD;
    }

    public H5PayRequest(PaymentMethod paymentMethod, String orderNo, Double amount, String description) {
        setPaymentMethod(paymentMethod);
        setOrderNo(orderNo);
        setAmount(amount);
        setDescription(description);
    }

    @Override
    public void validate() {
        super.validate();

        if (getPaymentMethod() == PaymentMethod.WECHATPAY && tradeType == PaymentChannelTradeType.JSAPI) {
            throw new IllegalArgumentException("WeChat does not support JSAPI payment");
        }
        if (tradeType == PaymentChannelTradeType.JSAPI) {
            validateRequired("userAccountId", userAccountId);
            validateLength("userAccountId", userAccountId, 64);
        }
        validateLength("returnUrl", returnUrl, 256);
    }

    @Override
    protected EnumSet<PaymentMethod> applicablePaymentMethods() {
        return EnumSet.of(PaymentMethod.ALIPAY, PaymentMethod.WECHATPAY);
    }

}
