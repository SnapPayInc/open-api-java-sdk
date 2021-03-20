package ca.snappay.openapi.response.pay;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * The response data for native payment.
 *
 * @author shawndu
 * @version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class NativePayResponseData extends AbstractPayResponseData {

    @SerializedName("sdk_params")
    private String sdkParams;

    @SerializedName("trade_no")
    private String tradeNo;

}
