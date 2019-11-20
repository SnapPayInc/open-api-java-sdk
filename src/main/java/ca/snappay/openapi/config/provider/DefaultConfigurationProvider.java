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
