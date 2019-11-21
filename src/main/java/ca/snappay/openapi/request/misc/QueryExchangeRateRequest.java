package ca.snappay.openapi.request.misc;

import ca.snappay.openapi.constant.Currency;
import ca.snappay.openapi.constant.PaymentMethod;
import ca.snappay.openapi.constant.PaymentType;
import ca.snappay.openapi.request.OpenApiRequest;
import ca.snappay.openapi.response.misc.QueryExchangeRateResponse;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.ToString;

/**
 * The request for exchange rate query.
 *
 * @author shawndu
 * @version 1.0
 */
@Data
@ToString(callSuper = true)
public class QueryExchangeRateRequest extends OpenApiRequest<QueryExchangeRateResponse> {

    private static final String REQUEST_METHOD = "pay.exchangerate";

    @SerializedName("basic_currency_unit")
    private Currency currency;

    @SerializedName("payment_method")
    private PaymentMethod paymentMethod;

    @SerializedName("pay_type")
    private PaymentType paymentType;

    @Override
    public String getRequestMethod() {
        return REQUEST_METHOD;
    }

    @Override
    public boolean needMerchant() {
        return false;
    }

    @Override
    public void validate() {
        validateRequired("currency", currency);
        validateRequired("paymentMethod", paymentMethod);
        if (paymentMethod == PaymentMethod.UNIONPAY) {
            throw new IllegalArgumentException("UnionPay does not support exchange rate query");
        }
    }
}
