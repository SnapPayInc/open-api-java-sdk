package ca.snappay.openapi.response.pay;

import ca.snappay.openapi.constant.Currency;
import ca.snappay.openapi.constant.PaymentMethod;
import ca.snappay.openapi.constant.PaymentOperationMethod;
import ca.snappay.openapi.response.OpenApiResponseData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class BarCodePayResponseData implements OpenApiResponseData {

    @SerializedName("payment_method")
    private PaymentMethod paymentMethod;

    @SerializedName("pay_operation_method")
    private int paymentOperationMethodCode;

    @Expose(deserialize = false, serialize = false)
    private PaymentOperationMethod paymentOperationMethod;

    @SerializedName("pay_user_account_id")
    private String userAccountId;

    @SerializedName("pay_user_account_name")
    private String userAccountName;

    @SerializedName("trans_currency")
    private Currency currency;

    @SerializedName("exchange_rate")
    private BigDecimal exchangeRate;

    @SerializedName("trans_amount")
    private Double transactionAmount;

    @SerializedName("c_trans_fee")
    private Double transactionFee;

    @SerializedName("customer_paid_amount")
    private Double paidAmount;

    @SerializedName("discount_bmopc")
    private Double merchantDiscount;

    @SerializedName("discount_bpc")
    private Double channelDiscount;

    @SerializedName("trans_end_time")
    private LocalDateTime completionTime;

}
