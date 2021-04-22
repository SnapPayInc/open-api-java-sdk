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
package ca.snappay.openapi.extension;

import ca.snappay.openapi.config.ConfigurationHolder;

/**
 * The extension of the API to support partial payment. User might need to implement this interface on order to
 * customize the order number of the partial payment (which is different from the original order number).
 *
 * @author shawndu
 * @version 1.0
 */
public interface AlternativeOrderNumberGenerator {

    /**
     * Generates alternative order number based on initial order number.
     *
     * @param config the configuration for the API client.
     * @param orderNo the initial order number.
     * @return the generated alternative order number.
     */
    String generate(ConfigurationHolder config, String orderNo);

}
