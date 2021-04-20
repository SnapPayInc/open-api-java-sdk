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

import ca.snappay.openapi.response.order.QueryOrderResponse;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.apache.commons.lang.StringUtils;

/**
 * The response for order query.
 *
 * @author shawndu
 * @version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class QueryOrderRequest extends AbstractOrderRequest<QueryOrderResponse> {

    private static final String REQUEST_METHOD = "pay.orderquery";

    @SerializedName("trans_no")
    private String transactionNo;

    @Override
    public String getRequestMethod() {
        return REQUEST_METHOD;
    }

    @Override
    public void validate() {
        if (StringUtils.isEmpty(getOrderNo()) && StringUtils.isEmpty(transactionNo)) {
            throw new IllegalArgumentException("Either orderNo or transactionNo needs to be provided");
        }
        validateLength("orderNo", getOrderNo(), 64);
        validateLength("transactionNo", transactionNo, 32);
    }

}
