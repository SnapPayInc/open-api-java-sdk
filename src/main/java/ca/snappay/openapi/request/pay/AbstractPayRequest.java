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

import ca.snappay.openapi.constant.Currency;
import ca.snappay.openapi.constant.PaymentMethod;
import ca.snappay.openapi.request.ExtensionParameters;
import ca.snappay.openapi.request.OpenApiRequest;
import ca.snappay.openapi.response.OpenApiResponse;
import java.util.EnumSet;
import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * This abstract class contains the common attributes for all payment requests.
 *
 * @param <T> the type of response.
 * @author shawndu
 * @version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
public abstract class AbstractPayRequest<T extends OpenApiResponse<?>> extends OpenApiRequest<T> {

    @SerializedName("payment_method")
    private PaymentMethod paymentMethod;

    @SerializedName("out_order_no")
    private String orderNo;

    @SerializedName("trans_currency")
    private Currency currency;

    @SerializedName("trans_amount")
    private Double amount;

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
    public void validate() {
        if (isPaymentMethodRequired()) {
            validateRequired("paymentMethod", paymentMethod);
            validatePaymentMethod();
        }
        validateRequired("orderNo", orderNo);
        validateLength("orderNo", orderNo, 64);
        validateRequired("amount", amount);
        validateRange("amount", amount, 0D, 100000000D);
        validateRequired("description", description);
        validateLength("description", description, 128);
        validateLength("notifyUrl", notifyUrl, 256);
        validateLength("longitude", longitude, 32);
        validateLength("latitude", latitude, 32);
        if (effectiveMinutes != null) {
            validateRange("effectiveMinutes", effectiveMinutes.intValue(), 5, 60);
        }
        if (extensionParameters != null) {
            if (extensionParameters.getStoreNo() != null) {
                validateLength("extensionParameters.storeNo", extensionParameters.getStoreNo(), 8);
            }
            if (extensionParameters.getQrCodeHeight() != null) {
                validateRange("extensionParameters.qrCodeHeight", extensionParameters.getQrCodeHeight(), 200, 400);
                validateRequired("extensionParameters.qrCodeWidth", extensionParameters.getQrCodeWidth());
            }
            if (extensionParameters.getQrCodeWidth() != null) {
                validateRange("extensionParameters.qrCodeWidth", extensionParameters.getQrCodeWidth(), 200, 400);
                validateRequired("extensionParameters.qrCodeHeight", extensionParameters.getQrCodeHeight());
            }
        }
    }

    private void validatePaymentMethod() {
        if (paymentMethod != null && !applicablePaymentMethods().contains(paymentMethod)) {
            throw new IllegalArgumentException("Given payment_method is not applicable");
        }
    }

    /**
     * Returns the set of applicable payment methods. If the client gives a payment method that is not applicable
     * for the type of payment, an error will return.
     * 
     */
    abstract protected EnumSet<PaymentMethod> applicablePaymentMethods();

    protected boolean isPaymentMethodRequired() {
        return true;
    }

}
