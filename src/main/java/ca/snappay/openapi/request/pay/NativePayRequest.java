package ca.snappay.openapi.request.pay;

import ca.snappay.openapi.response.pay.NativePayResponse;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

/**
 * The request for native payment.
 *
 * @author shawndu
 * @version 1.0
 */
@Data
public class NativePayRequest extends AbstractPayRequest<NativePayResponse> {

    private static final String REQUEST_METHOD = "pay.inapppay";

    @SerializedName("refer_url")
    private String referUrl;

    @Override
    public String getRequestMethod() {
        return REQUEST_METHOD;
    }

    @Override
    public void validate() {
        validateRequired("referUrl", referUrl);
        validateLength("referUrl", referUrl, 256);
    }

}
