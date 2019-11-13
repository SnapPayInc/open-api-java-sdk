package com.wiseasy.openapi.sign;

import com.alibaba.fastjson.JSONObject;
import com.wiseasy.openapi.utils.StringUtil;
import java.util.*;

/**
 * @Auther: liqie
 * @Date: 2018/11/1 13:55
 * @Description: 签名验签处理
 */
public class SignHandler {

    // 字符编码格式 目前支持UTF-8
    public static String INPUT_CHARSET = "UTF-8";

    /**
     * 签名
     * @param appId 应用ID
     * @param md5KeyOrPrivateKey MD5密钥（MD5签名验签）或RSA客户私钥 (RSA签名)，请根据你所选择的signType决定具体的赋值
     * @param signType 签名方法 RSA 或 MD5
     * @param params 待签名参数
     * @return 签名字符串
     */
    public static String sign(String appId, String md5KeyOrPrivateKey, String signType, Map<String, Object> params){
        if(StringUtil.isEmpty(appId) || StringUtil.isEmpty(signType)){
            return null;
        }else {
            String sign = "";
            switch (signType) {
                case "MD5":
                    sign = signWithMD5(params, md5KeyOrPrivateKey);
                    break;
                case "RSA":
                    sign = signWithRSA(params, md5KeyOrPrivateKey);
                    break;
            }
            if(StringUtil.isEmpty(sign)){
                return null;
            }
            return sign;
        }
    }


    /**
     * 验签
     * @param appId  应用ID
     * @param md5KeyOrOsgPrivateKey MD5密钥（MD5签名验签）或RSA客户私钥 (RSA签名)，请根据你所选择的signType决定具体的赋值
     * @param osgRsaPublicKey RSA开放服务网关公钥
     * @param signType 签名方法 RSA 或 MD5
     * @param params 待验签参数
     * @return
     */
    public static boolean verifySign(String appId, String md5KeyOrOsgPrivateKey, String osgRsaPublicKey, String signType, Map<String, Object> params){
        String sign = (String)params.get("sign");
        if(StringUtil.isEmpty(appId) || StringUtil.isEmpty(md5KeyOrOsgPrivateKey) || StringUtil.isEmpty(sign)){
            return true;
        }else {
            boolean isPass = false;
            switch (signType) {
                case "MD5":
                    String signStr = signWithMD5(params, md5KeyOrOsgPrivateKey);
                    if(StringUtil.isEmpty(signStr)){
                        return false;
                    }
                    isPass = sign.equals(signStr);
                    break;
                case "RSA":
                    isPass = verifySignWithRSA(params, osgRsaPublicKey, sign);
                    break;
            }
            return isPass;
        }
    }


    public static String signWithMD5(Map<String, Object> sParaTemp, String signatureKey){
        //除去数组中的空值和签名参数
        Map<String, Object> sPara = paraFilter(sParaTemp);

        //把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串并根据参数key排序
        String prestr = createLinkString(sPara);

        // 签名
        String mysign = MD5.md5sign(prestr, signatureKey, INPUT_CHARSET);
        return mysign;
    }


    public static String signWithRSA(Map<String, Object> sParaTemp, String privateKey){
        //除去数组中的空值和签名参数
        Map<String, Object> sPara = paraFilter(sParaTemp);

        //把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串并根据参数key排序
        String prestr = createLinkString(sPara);

        // 签名
        String mysign = RSA.sign(prestr, privateKey, INPUT_CHARSET);
        return mysign;
    }


    private static boolean verifySignWithRSA(Map<String, Object> sParaTemp, String publicKey, String sign){
        //除去数组中的空值和签名参数
        Map<String, Object> sPara = paraFilter(sParaTemp);

        //把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串并根据参数key排序
        String prestr = createLinkString(sPara);

        // 验证签名签名
        return RSA.verify(prestr, sign, publicKey, INPUT_CHARSET);
    }


    /**
     * 除去数组中的空值和签名参数
     * @param sArray 签名参数组
     * @return 去掉空值与签名参数后的新签名参数组
     */
    private static Map<String, Object> paraFilter(Map<String, Object> sArray) {

        Map<String, Object> result = new HashMap<String, Object>();

        if (sArray == null || sArray.size() <= 0) {
            return result;
        }

        JSONObject sJAray = new JSONObject(sArray);
        for (String key : sJAray.keySet()) {
            String value = sJAray.getString(key);
            if (value == null || value.equals("") || key.equalsIgnoreCase("sign")
                    || key.equalsIgnoreCase("sign_type")) {
                continue;
            }
            result.put(key, value);
        }

        return result;
    }

    /**
     * 把数组所有元素排序，并按照“参数=参数值”的模式用“&”字符拼接成字符串
     * @param params 需要排序并参与字符拼接的参数组
     * @return 拼接后字符串
     */
    private static String createLinkString(Map<String, Object> params) {
        JSONObject jparams = new JSONObject(params);
        List<String> keys = new ArrayList<String>(jparams.keySet());
        Collections.sort(keys);

        String prestr = "";

        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = jparams.getString(key);

            if (i == keys.size() - 1) {//拼接时，不包括最后一个&字符
                prestr = prestr + key + "=" + value;
            } else {
                prestr = prestr + key + "=" + value + "&";
            }
        }

        return prestr;
    }

}
