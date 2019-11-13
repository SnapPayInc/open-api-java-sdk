package ca.snappay.openapi.config.provider;

import java.util.Optional;

/**
 * The configuration provider that reads from environment variables.
 *
 * @author shawndu
 * @version 1.0
 */
public class EnvironmentVariableConfigurationProvider extends SystemSettingsConfigurationProvider {

    public static EnvironmentVariableConfigurationProvider create() {
        return new EnvironmentVariableConfigurationProvider();
    }

    @Override
    protected Optional<String> loadSetting(SystemSetting setting) {
        return Optional.ofNullable(System.getenv(setting.environmentVariable()));
    }

}
