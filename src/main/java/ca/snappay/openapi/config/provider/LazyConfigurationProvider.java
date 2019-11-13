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
