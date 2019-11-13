package com.wiseasy.openapi;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wiseasy.openapi.request.OpenApiRequest;
import com.wiseasy.openapi.response.OpenApiResponse;
import com.wiseasy.openapi.sign.SignHandler;
import com.wiseasy.openapi.utils.Constants;
import com.wiseasy.openapi.utils.DateUtil;
import com.wiseasy.openapi.utils.HttpUtil;
import com.wiseasy.openapi.utils.StringUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * 开放服务网关默认客户端
 *
 */
public class DefaultOpenApiClient implements OpenApiClient{

    private Log log = LogFactory.getLog(this.getClass());

    /**
     * API请求执行
     * @param <T>
     * @param request 请求对象
     * @param osgServerUrl 开放服务网关服务器地址,请参照Constants类的定义
     * @param md5KeyOrRsaPrivateKey MD5密钥（MD5签名验签）或RSA客户私钥 (RSA签名)，请根据你所选择的signType决定具体的赋值
     * @param osgRsaPublicKey 开放服务网关的RSA公钥，RSA签名时不能为空
     * @param language 客户端可接受语言,如：en-US,zh-CN等
     * @return 响应对象
     * @throws OpenApiException
     */
    public <T extends OpenApiResponse> T execute(OpenApiRequest<T> request, String osgServerUrl, String md5KeyOrRsaPrivateKey,
                                                 String osgRsaPublicKey, String language) throws OpenApiException{
        // 基本参数检查
        String appId = request.getApp_id();
        String signType = request.getSign_type();
        paramsCheck(osgRsaPublicKey, appId, signType);

        // 转换请求OpenApiRequest为 JSON
        JSONObject requestParams = JSONObject.parseObject(JSON.toJSONString(request));

        // 构建公共参数
        buildCommonParameters(request, requestParams);
        Map<String, Object> headers = new HashMap<String, Object>();
        headers.put("Accept-Language", StringUtil.isEmpty(language) ? Constants.ACCEPT_LANGUAGE_US : language);

        // 签名
        String signStr = SignHandler.sign(appId, md5KeyOrRsaPrivateKey, signType, requestParams);
        if(StringUtil.isEmpty(signStr)){
            throw new OpenApiException("-101", "Sign fail");
        }
        requestParams.put(Constants.SIGN, signStr);

        // 请求OSG服务器
        String resultStr = "";
        try {
            log.info("Request to osg[" + osgServerUrl + "] send data[" + requestParams.toJSONString() + "]");
            resultStr = HttpUtil.doPost(osgServerUrl, headers, requestParams);
            log.info("Response from osg[" + osgServerUrl + "] receive data[" + resultStr + "]");
        } catch (Exception e) {
            log.error("Request to osg[" + osgServerUrl + "] fail", e);
            throw new OpenApiException("-102", "Request to open service gateway fail");
        }
        JSONObject resultJson = JSONObject.parseObject(resultStr);

        // 验签
        boolean isPass = SignHandler.verifySign(appId, md5KeyOrRsaPrivateKey, osgRsaPublicKey, signType, resultJson);
        if(! isPass){
            throw new OpenApiException("-103", "Response data signature error");
        }

        // 转换返回JSON为OpenApiResponse
        if(resultJson.getLong(Constants.TOTAL) == 1){
            resultJson.putAll(resultJson.getJSONArray(Constants.DATA).getJSONObject(0));
        }
        T resp = JSON.toJavaObject(resultJson, request.getResponseClass());
        return resp;
    }

    private void paramsCheck(String osgRsaPublicKey, String appId, String signType) throws OpenApiException {
        if(StringUtil.isEmpty(appId)){
            throw new OpenApiException("-100", "The parameter [appId] cannot be empty");
        }
        if(signType.isEmpty()){
            throw new OpenApiException("-100", "The parameter [signType] cannot be empty");
        }
        if(! signType.equals(Constants.SIGN_TYPE_MD5) && ! signType.equals(Constants.SIGN_TYPE_RSA)){
            throw new OpenApiException("-100", "The parameter [appId] can only be assigned to MD5 or RSA");
        }
        if(signType.equals(Constants.SIGN_TYPE_RSA) && StringUtil.isEmpty(osgRsaPublicKey)){
            throw new OpenApiException("-100", "You are using the RSA signature method,The parameter [osgRsaPublicKey] cannot be empty");
        }
    }

    private <T extends OpenApiResponse> void buildCommonParameters(OpenApiRequest<T> request, JSONObject requestParams) {
        requestParams.put(Constants.METHOD, request.getRequestMethod());
        requestParams.put(Constants.FORMAT, Constants.FORMAT_JSON);
        requestParams.put(Constants.CHARSET, Constants.CHARSET_UTF8);
        requestParams.put(Constants.VERSION, Constants.VERSION_1);
        requestParams.put(Constants.TIMESTAMP, DateUtil.getCurrDateTimeStr(2));
        requestParams.remove("responseClass");
        requestParams.remove("requestMethod");
    }

}
