package ca.snappay.openapi.sign;

import ca.snappay.openapi.config.ConfigurationHolder;
import ca.snappay.openapi.utils.Constants;
import ca.snappay.openapi.utils.StringUtil;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Utility class for signature.
 *
 * @author shawndu
 * @version 1.0
 */
public class SignHandler {

    /**
     * Sign a request.
     *
     * @param config the configuration.
     * @param params the request.
     * @return the signature.
     */
    public static String sign(ConfigurationHolder config, JsonObject params) {
        String sign = null;
        switch (config.getSignType()) {
            case MD5:
                sign = signWithMD5(params, config.getPrivateKey());
                break;
            case RSA:
                sign = signWithRSA(params, config.getPrivateKey());
                break;
        }
        if (StringUtil.isEmpty(sign)) {
            return null;
        }
        return sign;
    }

    /**
     * Verifies the signature.
     *
     * @param config the configuration.
     * @param params the request.
     * @return true if the signature is valid; or false otherwise.
     */
    public static boolean verifySign(ConfigurationHolder config, JsonObject params) {
        String sign = params.get("sign").getAsString();

        boolean isPass = false;
        switch (config.getSignType()) {
            case MD5:
                String signStr = signWithMD5(params, config.getPrivateKey());
                if (StringUtil.isEmpty(signStr)) {
                    return false;
                }
                isPass = sign.equals(signStr);
                break;
            case RSA:
                isPass = verifySignWithRSA(params, config.getPublicKey(), sign);
                break;
        }
        return isPass;
    }


    private static String signWithMD5(JsonObject sParaTemp, String signatureKey) {
        String prestr = createLinkString(sParaTemp);

        String mysign = MD5.md5sign(prestr, signatureKey, Constants.CHARSET_UTF8);
        return mysign;
    }


    private static String signWithRSA(JsonObject sParaTemp, String privateKey) {
        String prestr = createLinkString(sParaTemp);

        String mysign = RSA.sign(prestr, privateKey, Constants.CHARSET_UTF8);
        return mysign;
    }


    private static boolean verifySignWithRSA(JsonObject sParaTemp, String publicKey, String sign) {
        String prestr = createLinkString(sParaTemp);

        return RSA.verify(prestr, sign, publicKey, Constants.CHARSET_UTF8);
    }

    /**
     * Filters out null and empty items in the given JSON element.
     *
     * @param param the JSON element.
     * @return the JSON element without null or empty items.
     */
    private static JsonElement paraFilter(JsonElement param) {
        if (param.isJsonNull()) {
            return null;
        } else if (param.isJsonPrimitive() && StringUtils.isEmpty(param.getAsString())) {
            return null;
        } else if (param.isJsonArray()) {
            boolean hasItem = false;
            JsonArray array = new JsonArray();
            for (JsonElement item : param.getAsJsonArray()) {
                JsonElement result = paraFilter(item);
                if (result != null) {
                    hasItem = true;
                    array.add(item);
                }
            }
            if (hasItem) {
                return array;
            } else {
                return null;
            }
        } else {
            boolean hasItem = false;
            JsonObject object = new JsonObject();
            for (String key : param.getAsJsonObject().keySet()) {
                if ("sign".equals(key) || "sign_type".equals(key)) {
                    continue;
                }
                JsonElement value = param.getAsJsonObject().get(key);
                JsonElement result = paraFilter(value);
                if (result != null) {
                    hasItem = true;
                    object.add(key, result);
                }
            }
            if (hasItem) {
                return object;
            } else {
                return null;
            }
        }
    }

    private static String createLinkString(JsonObject params) {
        params = (JsonObject) paraFilter(params);

        List<String> keys = new ArrayList<>(params.keySet());
        Collections.sort(keys);

        StringBuilder prestr = new StringBuilder();

        for (String key : keys) {
            String value = params.get(key).getAsString();
            prestr.append(key).append("=").append(value).append("&");
        }

        return prestr.deleteCharAt(prestr.length() - 1).toString();
    }

}
