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
package ca.snappay.openapi.config;

import ca.snappay.openapi.constant.Language;
import ca.snappay.openapi.constant.SignType;
import ca.snappay.openapi.constant.Constants;
import org.apache.commons.lang.StringUtils;

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

    /**
     * Gets whether the client supports partial payment.
     * This feature is only available for Snaplii payment.
     *
     * @return true if the client supports partial payment, or false otherwise.
     */
    public boolean isPartialPaymentSupported();

    /**
     * Gets the prefix for generating alternative order number when making partial payment.
     *
     * @return the alternative order number prefix.
     */
    public String getAlternativeOrderNumberPrefix();

    /**
     * Gets the suffix for generating alternative order number when making partial payment.
     *
     * @return the alternative order number suffix.
     */
    public String getAlternativeOrderNumberSuffix();

    /**
     * Gets the connection timeout setting.
     *
     * @return connection timeout in seconds.
     */
    public int getConnectionTimeout();

    /**
     * Gets the read timeout setting.
     *
     * @return read timeout in seconds.
     */
    public int getReadTimeout();

    /**
     * Validates the configuration.
     *
     * @throws OpenApiConfigurationExcepiton if any configuration is missing.
     */
    public default void validate() throws OpenApiConfigurationExcepiton {
        if (StringUtils.isEmpty(getGatewayHost())) {
            throw new OpenApiConfigurationExcepiton("Gateway host is not configured");
        }
        if (StringUtils.isEmpty(getAppId())) {
            throw new OpenApiConfigurationExcepiton("App ID is not configured");
        }
        if (StringUtils.isEmpty(getMerchantNo())) {
            throw new OpenApiConfigurationExcepiton("Merchant number is not configured");
        }
        if (StringUtils.isEmpty(getFormat())) {
            throw new OpenApiConfigurationExcepiton("Format is not configured");
        }
        if (StringUtils.isEmpty(getCharset())) {
            throw new OpenApiConfigurationExcepiton("Charset is not configured");
        }
        if (getLanguage() == null) {
            throw new OpenApiConfigurationExcepiton("Language is not configured");
        }
        if (getSignType() == null) {
            throw new OpenApiConfigurationExcepiton("Signature type is not configured");
        }
        if (StringUtils.isEmpty(getPrivateKey())) {
            throw new OpenApiConfigurationExcepiton("Private key is not configured");
        }
        if (SignType.RSA == getSignType() && StringUtils.isEmpty(getPublicKey())) {
            throw new OpenApiConfigurationExcepiton("Public key is not configured");
        }
        if (getConnectionTimeout() <= 0 || getConnectionTimeout() > 60) {
            throw new OpenApiConfigurationExcepiton("Connection timeout needs to be between 1 and 60");
        }
        if (getReadTimeout() <= 0 || getReadTimeout() > 60) {
          throw new OpenApiConfigurationExcepiton("Read timeout needs to be between 1 and 60");
      }
    }

}
