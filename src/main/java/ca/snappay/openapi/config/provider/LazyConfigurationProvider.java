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

import java.util.function.Supplier;

/**
 * A lazy configuration provider that delegate to another provider only when necessary.
 *
 * @author shawndu
 * @version 1.0
 */
public class LazyConfigurationProvider implements ConfigurationProvider {

    private final Supplier<ConfigurationProvider> delegateConstructor;
    private volatile ConfigurationProvider delegate;

    private LazyConfigurationProvider(Supplier<ConfigurationProvider> delegateConstructor) {
        this.delegateConstructor = delegateConstructor;
    }

    public static LazyConfigurationProvider create(Supplier<ConfigurationProvider> delegateConstructor) {
        return new LazyConfigurationProvider(delegateConstructor);
    }

    @Override
    public ConfigurationHolder resolveConfiguration() {
        if (delegate == null) {
            synchronized (this) {
                if (delegate == null) {
                    delegate = delegateConstructor.get();
                }
            }
        }
        return delegate.resolveConfiguration();
    }

}
