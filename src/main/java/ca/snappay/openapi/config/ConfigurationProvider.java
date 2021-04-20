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

/**
 * Implementations of this interface is capable to resolve configuration from certain source.
 *
 * @author shawndu
 * @version 1.0
 */
public interface ConfigurationProvider {

    /**
     * Resolves the configuration from certain source.
     *
     * @return the configuration.
     */
    ConfigurationHolder resolveConfiguration();

}
