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
package ca.snappay.openapi.config.provider;

import ca.snappay.openapi.config.ConfigurationHolder;
import ca.snappay.openapi.config.ConfigurationProvider;

/**
 * The default configuration provider that uses all existing providers in order.
 *
 * @author shawndu
 * @version 1.0
 */
public class DefaultConfigurationProvider implements ConfigurationProvider {

    private final LazyConfigurationProvider providerChain;

    private DefaultConfigurationProvider() {
        this.providerChain = createChain();
    }

    public static DefaultConfigurationProvider create() {
        return new DefaultConfigurationProvider();
    }

    private static LazyConfigurationProvider createChain() {
        return LazyConfigurationProvider.create(() -> {
            ConfigurationProvider[] configurationProviders = new ConfigurationProvider[]{
                    SystemPropertyConfigurationProvider.create(),
                    EnvironmentVariableConfigurationProvider.create(),
                    ProfileConfigurationProvider.create()
            };

            return ConfigurationProviderChain.of(configurationProviders);
        });
    }

    @Override
    public ConfigurationHolder resolveConfiguration() {
        return providerChain.resolveConfiguration();
    }

}
