package ca.snappay.openapi.request.order;

import ca.snappay.openapi.response.order.QueryOrderResponse;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.apache.commons.lang.StringUtils;

/**
 * The request for order query.
 *
 * @author shawndu
 * @version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class QueryOrderRequest extends AbstractOrderRequest<QueryOrderResponse> {

    private static final String REQUEST_METHOD = "pay.orderquery";

    @SerializedName("trans_no")
    private String transactionNo;

    @Override
    public String getRequestMethod() {
        return REQUEST_METHOD;
    }

    @Override
    public void validate() {
        if (StringUtils.isEmpty(getOrderNo()) && StringUtils.isEmpty(transactionNo)) {
            throw new IllegalArgumentException("Either orderNo or transactionNo needs to be provided");
        }
        validateLength("orderNo", getOrderNo(), 64);
        validateLength("transactionNo", transactionNo, 32);
    }

}
