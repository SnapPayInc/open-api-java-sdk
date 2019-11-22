package ca.snappay.openapi.request.pay;

import ca.snappay.openapi.constant.PaymentMethod;
import ca.snappay.openapi.response.pay.BarCodePayResponse;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.ToString;

/**
 * The request for barcode payment.
 *
 * @author shawndu
 * @version 1.0
 */
@Data
@ToString(callSuper = true)
public class BarCodePayRequest extends AbstractPayRequest<BarCodePayResponse> {

    private static final String REQUEST_METHOD = "pay.barcodepay";

    @SerializedName("auth_code")
    private String authCode;

    @Override
    public String getRequestMethod() {
        return REQUEST_METHOD;
    }

    @Override
    public void validate() {
        super.validate();

        if (getPaymentMethod() == PaymentMethod.UNIONPAY) {
            throw new IllegalArgumentException("UnionPay does not support barcode payment");
        }
        validateRequired("authCode", authCode);
        validateLength("authCode", authCode, 32);
    }

}
