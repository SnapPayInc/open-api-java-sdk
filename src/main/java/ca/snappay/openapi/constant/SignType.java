package ca.snappay.openapi.constant;

/**
 * This enum represents the signature type the client uses. Currently OpenAPI supports MD5 and RSA signatures.
 *
 * @author shawndu
 * @version 1.0
 */
public enum SignType {

    /**
     * MD5 signature.
     */
    MD5("md5"),

    /**
     * RSA signature.
     */
    RSA("rsa");

    private String key;

    SignType(String key) {
        this.key = key;
    }

    /**
     * Gets the key of the signature type.
     *
     * @return the key.
     */
    public String getKey() {
        return key;
    }

    /**
     * Gets the signature type from key.
     *
     * @param key the key.
     * @return the enum value for the given key, or null if the signature is not supported.
     */
    public static SignType getForKey(String key) {
        for (SignType signType : SignType.values()) {
            if (signType.key.equals(key)) {
                return signType;
            }
        }
        return null;
    }
}
