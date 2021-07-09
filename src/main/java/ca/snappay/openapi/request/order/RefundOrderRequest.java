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
package ca.snappay.openapi.request.order;

import ca.snappay.openapi.response.order.RefundOrderResponse;
import org.apache.commons.lang.StringUtils;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * The request for order refund.
 *
 * @author shawndu
 * @version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class RefundOrderRequest extends AbstractOrderRequest<RefundOrderResponse> {

    private static final String REQUEST_METHOD = "pay.orderrefund";

    private String transactionNo;

    @SerializedName("out_refund_no")
    private String refundOrderNo;

    @SerializedName("refund_amount")
    private Double refundAmount;

    @SerializedName("refund_desc")
    private String refundDescription;

    @Override
    public String getRequestMethod() {
        return REQUEST_METHOD;
    }

    public RefundOrderRequest(String refundOrderNo, Double refundAmount) {
        this.refundOrderNo = refundOrderNo;
        this.refundAmount = refundAmount;
    }

    @Override
    public void validate() {
        if ((StringUtils.isEmpty(getOrderNo()) && StringUtils.isEmpty(transactionNo))
                || (StringUtils.isNotEmpty(getOrderNo()) && StringUtils.isNotEmpty(transactionNo))) {
            throw new IllegalArgumentException("Either orderNo or transactionNo needs to be provided, but not both");
        }

        validateLength("orderNo", getOrderNo(), 64);
        validateLength("transactionNo", transactionNo, 64);

        validateRequired("refundOrderNo", refundOrderNo);
        validateLength("refundOrderNo", refundOrderNo, 64);
        validateRequired("refundAmount", refundAmount);
        validateRange("refundAmount", refundAmount, 0D, 100000000D);
        validateLength("refundDescription", refundDescription, 64);
    }

}
