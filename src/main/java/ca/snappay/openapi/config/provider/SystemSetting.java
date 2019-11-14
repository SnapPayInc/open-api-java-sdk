package ca.snappay.openapi.config.provider;

/**
 * The configuration system settings.
 *
 * @author shawndu
 * @version 1.0
 */
public enum SystemSetting {

    SNAPPAY_GATEWAY_HOST("snappay.openapi.gateway-host", "open.snappay.ca"),
    SNAPPAY_MERCHANT_NO("snappay.openapi.merchant-no", null),
    SNAPPAY_APP_ID("snappay.openapi.app-id", null),
    SNAPPAY_LANGUAGE("snappay.openapi.language", "en-US"),
    SNAPPAY_SIGN_TYPE("snappay.openapi.sign-type", "MD5"),
    SNAPPAY_PUBLIC_KEY("snappay.openapi.public-key", null),
    SNAPPAY_PRIVATE_KEY("snappay.openapi.private-key", null);

    private final String systemProperty;
    private final String defaultValue;

    SystemSetting(String systemProperty, String defaultValue) {
        this.systemProperty = systemProperty;
        this.defaultValue = defaultValue;
    }

    public String property() {
        return this.systemProperty;
    }

    public String environmentVariable() {
        return this.name();
    }

    public String defaultValue() {
        return this.defaultValue;
    }

}
