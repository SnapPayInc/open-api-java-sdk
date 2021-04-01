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
package ca.snappay.openapi.constant;

/**
 * This enum represents the languages that the client wants to receive. Currently OpenAPI supports both English and Chinese.
 *
 * @author shawndu
 * @version 1.0
 */
public enum Language {

    /**
     * English
     */
    ENGLISH("en-US"),

    /**
     * Chinese
     */
    CHINESE("zh-CN");

    private String language;

    Language(String language) {
        this.language = language;
    }

    /**
     * Gets the language name.
     *
     * @return the language name.
     */
    public String getLanguage() {
        return language;
    }

    /**
     * Gets the enum from language name.
     *
     * @param language the language name.
     * @return the enum value for the given language name, or null if the language is not supported.
     */
    public static Language getForLanguage(String language) {
        for (Language lang : Language.values()) {
            if (lang.language.equals(language)) {
                return lang;
            }
        }
        return null;
    }
}
