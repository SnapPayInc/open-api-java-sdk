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

import ca.snappay.openapi.request.ExtensionParameters;
import ca.snappay.openapi.request.OpenApiRequest;
import ca.snappay.openapi.response.OpenApiResponse;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * This abstract class contains the common attributes for all order requests.
 *
 * @param <T> the type of response.
 * @author shawndu
 * @version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
public abstract class AbstractOrderRequest<T extends OpenApiResponse<?>> extends OpenApiRequest<T> {

    @SerializedName("out_order_no")
    private String orderNo;

    @SerializedName("extension_parameters")
    private ExtensionParameters extensionParameters;

    @Override
    public void validate() {
        validateRequired("orderNo", orderNo);
        validateLength("orderNo", orderNo, 64);
    }

}
