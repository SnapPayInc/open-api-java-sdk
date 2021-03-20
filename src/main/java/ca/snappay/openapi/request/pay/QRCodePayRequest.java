package ca.snappay.openapi.request.pay;

import java.util.EnumSet;
import ca.snappay.openapi.constant.PaymentMethod;
import ca.snappay.openapi.response.pay.QRCodePayResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * The request for QR code payment.
 *
 * @author shawndu
 * @version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class QRCodePayRequest extends AbstractPayRequest<QRCodePayResponse> {

    private static final String REQUEST_METHOD = "pay.qrcodepay";

    @Override
    public String getRequestMethod() {
        return REQUEST_METHOD;
    }

    public QRCodePayRequest(PaymentMethod paymentMethod, String orderNo, Double amount, String description) {
        setPaymentMethod(paymentMethod);
        setOrderNo(orderNo);
        setAmount(amount);
        setDescription(description);
    }

    @Override
    protected EnumSet<PaymentMethod> applicablePaymentMethods() {
        return EnumSet.of(PaymentMethod.ALIPAY, PaymentMethod.WECHATPAY, PaymentMethod.UNIONPAY_QR);
    }
}
