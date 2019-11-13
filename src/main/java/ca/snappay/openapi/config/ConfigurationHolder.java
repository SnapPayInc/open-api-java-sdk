package ca.snappay.openapi.config;

import ca.snappay.openapi.constant.Language;
import ca.snappay.openapi.constant.SignType;
import ca.snappay.openapi.utils.Constants;

/**
 * The interface defining the configuration properties.
 *
 * @author shawndu
 * @version 1.0
 */
public interface ConfigurationHolder {

    /**
     * Gets OpenAPI Gateway host.
     *
     * @return the OpenAPI Gateway host.
     */
    public String getGatewayHost();

    /**
     * Gets the App ID.
     *
     * @return the App ID.
     */
    public String getAppId();

    /**
     * Gets the merchant number.
     *
     * @return the merchant number.
     */
    public String getMerchantNo();

    /**
     * Gets the message format.
     *
     * @return the message format.
     */
    public default String getFormat() {
        return Constants.FORMAT_JSON;
    }

    /**
     * Gets the message charset.
     *
     * @return the message charset.
     */
    public default String getCharset() {
        return Constants.CHARSET_UTF8;
    }

    /**
     * Gets the API version number.
     *
     * @return the API version number.
     */
    public default String getVersion() {
        return Constants.VERSION_1;
    }

    /**
     * Gets the language.
     *
     * @return the language.
     */
    public Language getLanguage();

    /**
     * Gets the signature type.
     *
     * @return the signature type.
     */
    public SignType getSignType();

    /**
     * Gets the public key (used by RSA only).
     *
     * @return the public key.
     */
    public String getPublicKey();

    /**
     * Gets the private key.
     *
     * @return the private key.
     */
    public String getPrivateKey();

}
