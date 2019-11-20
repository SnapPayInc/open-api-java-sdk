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
    MD5,

    /**
     * RSA signature.
     */
    RSA;

}
