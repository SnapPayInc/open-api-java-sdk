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
package ca.snappay.openapi.config;

import ca.snappay.openapi.constant.Language;
import ca.snappay.openapi.constant.SignType;
import lombok.Data;

/**
 * The basic implementation of <code>ConfigurationHolder</code>.
 *
 * @author shawndu
 * @version 1.0
 */
@Data
public final class BasicConfigurationHolder implements ConfigurationHolder {

    private String gatewayHost;
    private String merchantNo;
    private String appId;
    private Language language = Language.ENGLISH;
    private SignType signType;
    private String publicKey;
    private String privateKey;

    private boolean orderSplitSupported = true;
    private int connectionTimeout = 30;
    private int readTimeout = 30;

}
