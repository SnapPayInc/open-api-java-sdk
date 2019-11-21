package ca.snappay.openapi.response.pay;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.ToString;

/**
 * The response data for H5 payment.
 *
 * @author shawndu
 * @version 1.0
 */
@Data
@ToString(callSuper = true)
public class H5PayResponseData extends AbstractPayResponseData {

    @SerializedName("h5pay_url")
    private String h5PaymentUrl;

    @SerializedName("alipay_trade_no")
    private String alipayTradeNo;

}
