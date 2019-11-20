package ca.snappay.openapi.response.order;

import ca.snappay.openapi.response.OpenApiResponseData;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

/**
 * This abstract class contains the common attributes for all order response data.
 *
 * @author shawndu
 * @version 1.0
 */
@Data
public class AbstractOrderResponseData implements OpenApiResponseData {

    @SerializedName("trans_no")
    private String transactionNo;

    @SerializedName("out_order_no")
    private String orderNo;

}
