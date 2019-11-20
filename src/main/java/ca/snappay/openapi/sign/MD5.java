package ca.snappay.openapi.sign;

import org.apache.commons.codec.digest.DigestUtils;

import java.io.UnsupportedEncodingException;

/**
 * Utility class for MD5 signature.
 *
 * @author shawndu
 * @version 1.0
 */
public class MD5 {

    /**
     * Signs a string.
     *
     * @param text    the string to be signed.
     * @param key     the private key.
     * @param charset the character set.
     * @return the signature.
     */
    public static String md5sign(String text, String key, String charset) {
        text = text + key;
        return DigestUtils.md5Hex(getContentBytes(text, charset));
    }

    private static byte[] getContentBytes(String content, String charset) {
        if (charset == null || "".equals(charset)) {
            return content.getBytes();
        }
        try {
            return content.getBytes(charset);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Unsupported character set: " + charset);
        }
    }
}
