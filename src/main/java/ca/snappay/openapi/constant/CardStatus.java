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
package ca.snappay.openapi.constant;

import com.google.gson.annotations.SerializedName;

/**
 * This enum represents the different card statuses.
 *
 * @author shawndu
 * @version 1.0
 */
public enum CardStatus {

    /**
     * Card is reported lost.
     */
    @SerializedName("3")
    LOST,

    /**
     * Card is not activated.
     */
    @SerializedName("4")
    UNACTIVATED,

    /**
     * Card is activated but not used.
     */
    @SerializedName("5")
    ACTIVATED,

    /**
     * Card is used (activated in phone app).
     */
    @SerializedName("6")
    USED,

    /**
     * Card is refunded.
     */
    @SerializedName("7")
    REFUNDED;

}
