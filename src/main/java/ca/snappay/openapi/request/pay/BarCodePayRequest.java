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
package ca.snappay.openapi.request.pay;

import ca.snappay.openapi.constant.PaymentMethod;
import ca.snappay.openapi.response.pay.BarCodePayResponse;
import java.util.EnumSet;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * The request for barcode payment.
 *
 * @author shawndu
 * @version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class BarCodePayRequest extends AbstractPayRequest<BarCodePayResponse> {

    private static final String REQUEST_METHOD = "pay.barcodepay";

    private static final String SNAPLII_PAYMENT_PATTERN = "^88\\d{16}$";

    @SerializedName("auth_code")
    private String authCode;

    @Override
    public String getRequestMethod() {
        return REQUEST_METHOD;
    }

    public BarCodePayRequest(String orderNo, Double amount, String description, String authCode) {
        setOrderNo(orderNo);
        setAmount(amount);
        setDescription(description);
        this.authCode = authCode;
    }

    @Override
    public void validate() {
        super.validate();

        validateRequired("authCode", authCode);
        validateLength("authCode", authCode, 32);
    }

    @Override
    protected EnumSet<PaymentMethod> applicablePaymentMethods() {
        return EnumSet.of(PaymentMethod.ALIPAY, PaymentMethod.WECHATPAY, PaymentMethod.UNIONPAY_QR,
            PaymentMethod.SNAPLII);
    }

    @Override
    protected boolean isPaymentMethodRequired() {
        return false;
    }

    /**
     * Checks if the payment is for Snaplii.
     *
     * @return true if this is Snaplii payment, or false otherwise.
     */
    public boolean isSnapliiPayment() {
        return authCode.matches(SNAPLII_PAYMENT_PATTERN);
    }

}
