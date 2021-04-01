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
