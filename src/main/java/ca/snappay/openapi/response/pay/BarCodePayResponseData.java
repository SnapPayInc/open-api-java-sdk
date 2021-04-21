/*
 * Copyright 2021 SnapPay Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ca.snappay.openapi.response.pay;

import ca.snappay.openapi.constant.Currency;
import ca.snappay.openapi.constant.PaymentMethod;
import ca.snappay.openapi.constant.PaymentOperationMethod;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * The response data for barcode payment.
 *
 * @author shawndu
 * @version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class BarCodePayResponseData extends AbstractPayResponseData {

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

    private Boolean partialPayment;

    private Double totalAmount;

    private Double outstandingAmount;

    public void setPaymentOperationMethodCode(int paymentOperationMethodCode) {
        this.paymentOperationMethodCode = paymentOperationMethodCode;
        this.paymentOperationMethod = PaymentOperationMethod.getFromCode(paymentOperationMethodCode);
    }

}
