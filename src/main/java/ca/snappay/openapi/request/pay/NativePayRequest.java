package ca.snappay.openapi.request.pay;

import ca.snappay.openapi.constant.PaymentMethod;
import ca.snappay.openapi.response.pay.NativePayResponse;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.ToString;

/**
 * The request for native payment.
 *
 * @author shawndu
 * @version 1.0
 */
@Data
@ToString(callSuper = true)
public class NativePayRequest extends AbstractPayRequest<NativePayResponse> {

    private static final String REQUEST_METHOD = "pay.inapppay";

    @SerializedName("refer_url")
    private String referUrl;

    @Override
    public String getRequestMethod() {
        return REQUEST_METHOD;
    }

    @Override
    public void validate() {
        super.validate();

        if (getPaymentMethod() == PaymentMethod.UNIONPAY) {
            throw new IllegalArgumentException("UnionPay does not support native payment");
        }
        validateRequired("referUrl", referUrl);
        validateLength("referUrl", referUrl, 256);
    }

}
