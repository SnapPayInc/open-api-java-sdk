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
package ca.snappay.openapi.request.misc;

import ca.snappay.openapi.constant.Currency;
import ca.snappay.openapi.constant.PaymentMethod;
import ca.snappay.openapi.constant.PaymentType;
import ca.snappay.openapi.request.OpenApiRequest;
import ca.snappay.openapi.response.misc.QueryExchangeRateResponse;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * The request for exchange rate query.
 *
 * @author shawndu
 * @version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class QueryExchangeRateRequest extends OpenApiRequest<QueryExchangeRateResponse> {

    private static final String REQUEST_METHOD = "pay.exchangerate";

    @SerializedName("basic_currency_unit")
    private Currency currency;

    @SerializedName("payment_method")
    private PaymentMethod paymentMethod;

    @SerializedName("pay_type")
    private PaymentType paymentType;

    public QueryExchangeRateRequest(Currency currency, PaymentMethod paymentMethod) {
        this.currency = currency;
        this.paymentMethod = paymentMethod;
    }

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
        if (paymentMethod != PaymentMethod.ALIPAY && paymentMethod != PaymentMethod.WECHATPAY) {
            throw new IllegalArgumentException("Given payment_method is not applicable");
        }
    }
}
