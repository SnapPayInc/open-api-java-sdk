package ca.snappay.openapi.request.order;

import ca.snappay.openapi.response.order.RefundOrderResponse;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.ToString;

/**
 * The response for order refund.
 *
 * @author shawndu
 * @version 1.0
 */
@Data
@ToString(callSuper = true)
public class RefundOrderRequest extends AbstractOrderRequest<RefundOrderResponse> {

    private static final String REQUEST_METHOD = "pay.orderrefund";

    @SerializedName("out_refund_no")
    private String refundOrderNo;

    @SerializedName("refund_amount")
    private Double refundAmount;

    @SerializedName("refund_desc")
    private String refundDescription;

    @Override
    public String getRequestMethod() {
        return REQUEST_METHOD;
    }

    @Override
    public void validate() {
        super.validate();

        validateRequired("refundOrderNo", refundOrderNo);
        validateLength("refundOrderNo", refundOrderNo, 64);
        validateRequired("refundAmount", refundAmount);
        validateRange("refundAmount", refundAmount, 0D, 100000000D);
        validateLength("refundDescription", refundDescription, 64);
    }

}
