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
package ca.snappay.openapi.response;

import ca.snappay.openapi.constant.Constants;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.List;

/**
 * This class is the generic type of OpenAPI response.
 *
 * @author shawndu
 * @version 1.0
 */
@Data
public abstract class OpenApiResponse<T extends OpenApiResponseData> {

    private String code;

    @SerializedName("msg")
    private String message;

    private Long total;

    private List<T> data;

    private String psn;

    private String sign;

    /**
     * Checks if the request is successful.
     *
     * @return true if the request is successful; or false otherwise.
     */
    public boolean isSuccessful() {
        return Constants.CODE_SUCCESS.equals(code);
    }

    /**
     * Gets the single result.
     *
     * @return the result, if any.
     */
    public T getResult() {
        if (isSuccessful() && total == 1) {
            return data.get(0);
        } else {
            return null;
        }
    }

}
