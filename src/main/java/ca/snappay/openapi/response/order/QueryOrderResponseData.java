package ca.snappay.openapi.response.order;

import ca.snappay.openapi.response.pay.BarCodePayResponseData;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * The response data for order query.
 *
 * @author shawndu
 * @version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class QueryOrderResponseData extends BarCodePayResponseData {

    @SerializedName("attach")
    private String attach;

}
