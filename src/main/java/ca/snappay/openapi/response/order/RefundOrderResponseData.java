package ca.snappay.openapi.response.order;

import ca.snappay.openapi.constant.TransactionStatus;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * The response data for order refund.
 *
 * @author shawndu
 * @version 1.0
 */
@Data
@ToString(callSuper = true)
public class RefundOrderResponseData extends AbstractOrderResponseData {

    @SerializedName("out_refund_no")
    private String refundOrderNo;

    @SerializedName("trans_status")
    private TransactionStatus transactionStatus;

    @SerializedName("refund_trans_no")
    private String refundTransactionNo;

    @SerializedName("refund_trans_end_time")
    private LocalDateTime refundCompletionTime;

}
