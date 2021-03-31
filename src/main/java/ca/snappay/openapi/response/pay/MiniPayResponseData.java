package ca.snappay.openapi.response.pay;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * The response data for mini payment.
 *
 * @author shawndu
 * @version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class MiniPayResponseData extends AbstractPayResponseData {

    @SerializedName("request_payment")
    private String requestPayment;

    @SerializedName("trade_no")
    private String tradeNo;

}
