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

import org.apache.commons.lang.StringUtils;
import ca.snappay.openapi.config.ConfigurationHolder;

public class DefaultAlternativeOrderNumberGenerator implements AlternativeOrderNumberGenerator {

    @Override
    public String generate(ConfigurationHolder config, String orderNo) {
        StringBuilder alternativeOrderNo = new StringBuilder();
        if (StringUtils.isNotBlank(config.getAlternativeOrderNumberPrefix())) {
            alternativeOrderNo.append(config.getAlternativeOrderNumberPrefix());
        }
        alternativeOrderNo.append(orderNo);
        if (StringUtils.isNotBlank(config.getAlternativeOrderNumberSuffix())) {
            alternativeOrderNo.append(config.getAlternativeOrderNumberSuffix());
        }
        orderNo.substring(0, orderNo.length() - 3);
        String.format("%3d", 1);
        return alternativeOrderNo.toString();
    }

}
