package ca.snappay.openapi.config;

import ca.snappay.openapi.constant.Language;
import ca.snappay.openapi.constant.SignType;

/**
 * The basic implementation of <code>ConfigurationHolder</code>.
 *
 * @author shawndu
 * @version 1.0
 */
public final class BasicConfigurationHolder implements ConfigurationHolder {

    private String gatewayHost;
    private String merchantNo;
    private String appId;
    private Language language = Language.ENGLISH;
    private SignType signType;
    private String publicKey;
    private String privateKey;

    public BasicConfigurationHolder() {
    }

    @Override
    public String getGatewayHost() {
        return gatewayHost;
    }

    public void setGatewayHost(String gatewayHost) {
        this.gatewayHost = gatewayHost;
    }

    @Override
    public String getMerchantNo() {
        return merchantNo;
    }

    public void setMerchantNo(String merchantNo) {
        this.merchantNo = merchantNo;
    }

    @Override
    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    @Override
    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    @Override
    public SignType getSignType() {
        return signType;
    }

    public void setSignType(SignType signType) {
        this.signType = signType;
    }

    @Override
    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    @Override
    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

}
