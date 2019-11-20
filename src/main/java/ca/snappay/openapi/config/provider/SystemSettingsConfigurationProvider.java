package ca.snappay.openapi.config.provider;

import ca.snappay.openapi.config.BasicConfigurationHolder;
import ca.snappay.openapi.config.ConfigurationHolder;
import ca.snappay.openapi.config.ConfigurationProvider;
import ca.snappay.openapi.config.OpenApiConfigurationExcepiton;
import ca.snappay.openapi.constant.Language;
import ca.snappay.openapi.constant.SignType;
import org.apache.commons.lang.StringUtils;

import java.util.Optional;

/**
 * The configuration provider that reads certain system setting.
 *
 * @author shawndu
 * @version 1.0
 */
public abstract class SystemSettingsConfigurationProvider implements ConfigurationProvider {

    public ConfigurationHolder resolveConfiguration() {
        String gatewayHost = StringUtils.trim(loadSetting(SystemSetting.SNAPPAY_GATEWAY_HOST).orElse(SystemSetting.SNAPPAY_GATEWAY_HOST.defaultValue()));
        String merchantNo = StringUtils.trim(loadSetting(SystemSetting.SNAPPAY_MERCHANT_NO).orElse(null));
        String appId = StringUtils.trim(loadSetting(SystemSetting.SNAPPAY_APP_ID).orElse(null));
        String language = StringUtils.trim(loadSetting(SystemSetting.SNAPPAY_LANGUAGE).orElse(SystemSetting.SNAPPAY_LANGUAGE.defaultValue()));
        String signType = StringUtils.trim(loadSetting(SystemSetting.SNAPPAY_SIGN_TYPE).orElse(SystemSetting.SNAPPAY_SIGN_TYPE.defaultValue()));
        String publicKey = StringUtils.trim(loadSetting(SystemSetting.SNAPPAY_PUBLIC_KEY).orElse(null));
        String privateKey = StringUtils.trim(loadSetting(SystemSetting.SNAPPAY_PRIVATE_KEY).orElse(null));

        if (gatewayHost == null) {
            throw new OpenApiConfigurationExcepiton("Gateway host is missing from configuration");
        }
        if (merchantNo == null) {
            throw new OpenApiConfigurationExcepiton("Merchant number is missing from configuration");
        }
        if (appId == null) {
            throw new OpenApiConfigurationExcepiton("App ID is missing from configuration");
        }
        Language lang = Language.getForLanguage(language);
        if (lang == null) {
            throw new OpenApiConfigurationExcepiton("Language is not valid");
        }
        SignType sign = SignType.valueOf(signType);
        if (sign == null) {
            throw new OpenApiConfigurationExcepiton("Sign type is not valid");
        }
        if (privateKey == null) {
            throw new OpenApiConfigurationExcepiton("Private key is missing from configuration");
        }
        if (sign == SignType.RSA && publicKey == null) {
            throw new OpenApiConfigurationExcepiton("Public key is missing from configuration");
        }

        BasicConfigurationHolder config = new BasicConfigurationHolder();
        config.setGatewayHost(gatewayHost);
        config.setMerchantNo(merchantNo);
        config.setAppId(appId);
        config.setLanguage(lang);
        config.setSignType(sign);
        config.setPublicKey(publicKey);
        config.setPrivateKey(privateKey);

        return config;
    }

    /**
     * Loads configuration value for the given setting.
     *
     * @param setting the setting.
     * @return the configuration value.
     */
    protected abstract Optional<String> loadSetting(SystemSetting setting);

}
