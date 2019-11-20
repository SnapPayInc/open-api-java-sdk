package ca.snappay.openapi.config.provider;

import java.util.Optional;

/**
 * The configuration provider that reads from system properties.
 *
 * @author shawndu
 * @version 1.0
 */
public class SystemPropertyConfigurationProvider extends SystemSettingsConfigurationProvider {

    public static SystemSettingsConfigurationProvider create() {
        return new SystemPropertyConfigurationProvider();
    }

    @Override
    protected Optional<String> loadSetting(SystemSetting setting) {
        return Optional.ofNullable(System.getProperty(setting.property()));
    }

}
