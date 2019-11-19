package ca.snappay.openapi.request.order;

import ca.snappay.openapi.request.OpenApiRequest;
import ca.snappay.openapi.response.OpenApiResponse;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import org.apache.commons.lang.StringUtils;

/**
 * This abstract class contains the common attributes for all order requests.
 *
 * @param <T> the type of response.
 * @author shawndu
 * @version 1.0
 */
@Data
public abstract class AbstractOrderRequest<T extends OpenApiResponse> extends OpenApiRequest<T> {

    @SerializedName("out_order_no")
    private String orderNo;

    @Override
    public void validate() {
        validateRequired("orderNo", orderNo);
        validateLength("orderNo", orderNo, 64);
    }

}
