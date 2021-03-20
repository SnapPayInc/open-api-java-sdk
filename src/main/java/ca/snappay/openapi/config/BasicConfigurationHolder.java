package ca.snappay.openapi.config;

import ca.snappay.openapi.constant.Language;
import ca.snappay.openapi.constant.SignType;
import lombok.Data;

/**
 * The basic implementation of <code>ConfigurationHolder</code>.
 *
 * @author shawndu
 * @version 1.0
 */
@Data
public final class BasicConfigurationHolder implements ConfigurationHolder {

    private String gatewayHost;
    private String merchantNo;
    private String appId;
    private Language language = Language.ENGLISH;
    private SignType signType;
    private String publicKey;
    private String privateKey;

    private int connectionTimeout = 30;
    private int readTimeout = 30;

}
