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
 * This enum represents the signature type the client uses. Currently OpenAPI supports MD5 and RSA signatures.
 *
 * @author shawndu
 * @version 1.0
 */
public enum SignType {

    /**
     * MD5 signature.
     */
    MD5,

    /**
     * RSA signature.
     */
    RSA;

}
