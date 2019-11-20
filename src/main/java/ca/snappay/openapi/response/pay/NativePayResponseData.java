package ca.snappay.openapi.response.pay;

import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

/**
 * The response data for native payment.
 *
 * @author shawndu
 * @version 1.0
 */
@Data
public class NativePayResponseData extends AbstractPayResponseData {

    @SerializedName("sdk_params")
    private JsonObject sdkParams;

    @SerializedName("trade_no")
    private String tradeNo;

}
