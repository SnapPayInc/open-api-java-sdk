package ca.snappay.openapi.request.order;

import ca.snappay.openapi.request.ExtensionParameters;
import ca.snappay.openapi.response.order.QueryOrderResponse;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import org.apache.commons.lang.StringUtils;

/**
 * The response for order query.
 *
 * @author shawndu
 * @version 1.0
 */
@Data
public class QueryOrderRequest extends AbstractOrderRequest<QueryOrderResponse> {

    private static final String REQUEST_METHOD = "pay.orderquery";

    @SerializedName("out_order_no")
    private String orderNo;

    @SerializedName("trans_no")
    private String transactionNo;

    @SerializedName("extension_parameters")
    private ExtensionParameters extensionParameters;

    @Override
    public String getRequestMethod() {
        return REQUEST_METHOD;
    }

    @Override
    public void validate() {
        if (StringUtils.isEmpty(orderNo) && StringUtils.isEmpty(transactionNo)) {
            throw new IllegalArgumentException("Either orderNo or transactionNo needs to be provided");
        }
        validateLength("orderNo", orderNo, 64);
        validateLength("transactionNo", transactionNo, 32);
    }

}
