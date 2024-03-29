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
import ca.snappay.openapi.config.OpenApiConfigurationExcepiton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * A chain of configuration providers, and the first provider that can provide a valid configuration will be used.
 *
 * @author shawndu
 * @version 1.0
 */
public class ConfigurationProviderChain implements ConfigurationProvider {

    private final List<ConfigurationProvider> configurationProviders;

    private ConfigurationProviderChain(List<ConfigurationProvider> configurationProviders) {
        this.configurationProviders = Collections.unmodifiableList(configurationProviders);
    }

    /**
     * Builds a chain with given configuration providers.
     *
     * @param configurationProviders the configuration providers.
     * @return the configuration provider chain.
     */
    public static ConfigurationProviderChain of(ConfigurationProvider... configurationProviders) {
        return new ConfigurationProviderChain(Arrays.asList(configurationProviders));
    }

    /**
     * Tries each configuration provider from the chain, and returns the configuration from the first provider that can resolve a valid configuration.
     *
     * @return the configuration.
     */
    @Override
    public ConfigurationHolder resolveConfiguration() {
        List<String> errorMessages = null;

        for (ConfigurationProvider provider : configurationProviders) {
            try {
                ConfigurationHolder config = provider.resolveConfiguration();

                return config;
            } catch (OpenApiConfigurationExcepiton e) {
                String message = provider + ": " + e.getMessage();
                if (errorMessages == null) {
                    errorMessages = new ArrayList<>();
                }
                errorMessages.add(message);
            }
        }

        throw new OpenApiConfigurationExcepiton("Unable to load configuration from any of the providers in the chain " +
                this + " : " + errorMessages);
    }

    @Override
    public String toString() {
        return "ConfigurationProviderChain";
    }
}
