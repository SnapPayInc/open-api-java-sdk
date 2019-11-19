package ca.snappay.openapi.config.provider;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Optional;
import java.util.Properties;

/**
 * The configuration provider that reads from profile file.
 *
 * @author shawndu
 * @version 1.0
 */
public class ProfileConfigurationProvider extends SystemSettingsConfigurationProvider {

    private static final String PROFILE_FILE_PATH = System.getProperty("user.home") + "/.snappay/config";

    private final boolean fileExists;
    private final Properties properties;

    private ProfileConfigurationProvider() {
        File propertyFile = new File(PROFILE_FILE_PATH);
        this.fileExists = propertyFile.exists();

        properties = new Properties();
        if (this.fileExists) {
            try {
                this.properties.load(new FileInputStream(propertyFile));
            } catch (IOException e) {
                // ignore
            }
        }
    }

    public static ProfileConfigurationProvider create() {
        return new ProfileConfigurationProvider();
    }

    @Override
    protected Optional<String> loadSetting(SystemSetting setting) {
        if (!fileExists) {
            return Optional.empty();
        }
        String key = setting.property().replace("snappay.", "");
        return Optional.ofNullable(properties.getProperty(key));
    }

}
