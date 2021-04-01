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

/**
 * The payment operation method.
 *
 * @author shawndu
 * @version 1.0
 */
public enum PaymentOperationMethod {

    QR_CODE_PAY,

    BAR_CODE_PAY,

    H5_PAY,

    NATIVE_APP_PAY,

    WEB_PAY;

    /**
     * Gets a payment operation method from the given code.
     *
     * @param code the code.
     * @return the corresponding payment operation method.
     */
    public static PaymentOperationMethod getFromCode(int code) {
        switch (code) {
            case 4:
                return QR_CODE_PAY;
            case 5:
                return BAR_CODE_PAY;
            case 6:
                return H5_PAY;
            case 8:
                return NATIVE_APP_PAY;
            case 9:
                return WEB_PAY;
            default:
                return null;
        }
    }

}
