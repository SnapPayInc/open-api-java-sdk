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
import ca.snappay.openapi.response.pay.OneForAllPayResponse;
import java.util.EnumSet;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * The request for one-for-all payment.
 *
 * @author shawndu
 * @version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class OneForAllPayRequest extends AbstractPayRequest<OneForAllPayResponse> {

    private static final String REQUEST_METHOD = "pay.oneforall";

    @SerializedName("return_url")
    private String returnUrl;

    @Override
    public String getRequestMethod() {
        return REQUEST_METHOD;
    }

    public OneForAllPayRequest(PaymentMethod paymentMethod, String orderNo, Double amount, String description, String payUserAccountId) {
      setPaymentMethod(paymentMethod);
      setOrderNo(orderNo);
      setAmount(amount);
      setDescription(description);
  }

    @Override
    public void validate() {
        super.validate();

        validateLength("returnUrl", returnUrl, 1024);
    }

    @Override
    protected boolean isPaymentMethodRequired() {
      return false;
    }

    @Override
    protected EnumSet<PaymentMethod> applicablePaymentMethods() {
        return null;
    }

}
