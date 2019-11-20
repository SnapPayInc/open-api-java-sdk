package ca.snappay.openapi.request.pay;

import ca.snappay.openapi.constant.PaymentMethod;
import ca.snappay.openapi.response.pay.QRCodePayResponse;
import lombok.Data;

/**
 * The request for QR code payment.
 *
 * @author shawndu
 * @version 1.0
 */
@Data
public class QRCodePayRequest extends AbstractPayRequest<QRCodePayResponse> {

    private static final String REQUEST_METHOD = "pay.qrcodepay";

    @Override
    public String getRequestMethod() {
        return REQUEST_METHOD;
    }

    @Override
    public void validate() {
        super.validate();

        if (getPaymentMethod() == PaymentMethod.UNIODPAY) {
            throw new IllegalArgumentException("UnionPay does not support QR code payment");
        }
    }
}
