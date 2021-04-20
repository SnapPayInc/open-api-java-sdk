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

import ca.snappay.openapi.response.order.RevokeOrderResponse;
import lombok.ToString;

/**
 * The response for order revoke.
 *
 * @author shawndu
 * @version 1.0
 */
@ToString(callSuper = true)
public class RevokeOrderRequest extends AbstractOrderRequest<RevokeOrderResponse> {

    private static final String REQUEST_METHOD = "pay.ordercancel";

    @Override
    public String getRequestMethod() {
        return REQUEST_METHOD;
    }

    public RevokeOrderRequest(String orderNo) {
        setOrderNo(orderNo);
    }

}
