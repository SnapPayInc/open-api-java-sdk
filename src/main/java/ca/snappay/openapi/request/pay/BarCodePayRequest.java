package ca.snappay.openapi.request.pay;

import ca.snappay.openapi.constant.Currency;
import ca.snappay.openapi.constant.PaymentMethod;
import ca.snappay.openapi.request.ExtensionParameters;
import ca.snappay.openapi.request.OpenApiRequest;
import ca.snappay.openapi.response.pay.BarCodePayResponse;
import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

/**
 * The request for barcode payment.
 *
 * @author shawndu
 * @version 1.0
 */
@Data
public class BarCodePayRequest extends OpenApiRequest<BarCodePayResponse> {

    private static final String REQUEST_METHOD = "pay.barcodepay";

    @SerializedName("payment_method")
    private PaymentMethod paymentMethod;

    @SerializedName("out_order_no")
    private String orderNo;

    @SerializedName("trans_currency")
    private Currency currency;

    @SerializedName("trans_amount")
    private Double amount;

    @SerializedName("auth_code")
    private String authCode;

    private String description;

    @SerializedName("notify_url")
    private String notifyUrl;

    private JsonObject attach;

    private String longitude;

    private String latitude;

    @SerializedName("effective_minutes")
    private Integer effectiveMinutes;

    @SerializedName("extension_parameters")
    private ExtensionParameters extensionParameters;

    @Override
    public String getRequestMethod() {
        return REQUEST_METHOD;
    }

    @Override
    public void validate() {
        validateRequired("paymentMethod", paymentMethod);
        validateRequired("orderNo", orderNo);
        validateLength("orderNo", orderNo, 64);
        validateRequired("amount", amount);
        validateRange("amount", amount, 0D, 100000000D);
        validateRequired("authCode", authCode);
        validateLength("authCode", authCode, 32);
        validateRequired("description", description);
        validateLength("description", description, 128);
        validateLength("notifyUrl", notifyUrl, 256);
        validateLength("longitude", longitude, 32);
        validateLength("latitude", latitude, 32);
        if (effectiveMinutes != null) {
            validateRange("effectiveMinutes", effectiveMinutes.intValue(), 5, 60);
        }
        if (extensionParameters != null) {
            validateLength("extensionParameters.storeNo", extensionParameters.getStoreNo(), 8);
        }
    }

}
