package ca.snappay.openapi.response.pay;

import ca.snappay.openapi.constant.TransactionStatus;
import ca.snappay.openapi.response.OpenApiResponseData;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

/**
 * This abstract class contains the common attributes for all payment response data.
 *
 * @author shawndu
 * @version 1.0
 */
@Data
public class AbstractPayResponseData implements OpenApiResponseData {

    @SerializedName("trans_no")
    private String transactionNo;

    @SerializedName("out_order_no")
    private String orderNo;

    @SerializedName("merchant_no")
    private String merchantNo;

    @SerializedName("trans_status")
    private TransactionStatus transactionStatus;

}
