package ca.snappay.openapi.response.order;

import ca.snappay.openapi.response.pay.BarCodePayResponseData;
import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

/**
 * The response data for order query.
 *
 * @author shawndu
 * @version 1.0
 */
@Data
public class QueryOrderResponseData extends BarCodePayResponseData {

    @SerializedName("attach")
    private JsonObject attach;

}
