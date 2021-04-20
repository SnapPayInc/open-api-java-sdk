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
package ca.snappay.openapi.response.card;

import java.time.LocalDateTime;
import com.google.gson.annotations.SerializedName;
import ca.snappay.openapi.response.OpenApiResponseData;
import lombok.Data;

/**
 * This abstract class contains the common attributes for all card response data.
 *
 * @author shawndu
 * @version 1.0
 */
@Data
public abstract class AbstractCardResponseData implements OpenApiResponseData {

    @SerializedName("face_value")
    private Double faceValue;

    @SerializedName("giftcard_type")
    private String cardType;

    @SerializedName("activate_end_time")
    private LocalDateTime activateEndTime;

    @SerializedName("expiry_date")
    private LocalDateTime expiryDate;

    @SerializedName("jrn_no")
    private String jrnNo;

}
