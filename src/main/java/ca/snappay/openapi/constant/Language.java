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
