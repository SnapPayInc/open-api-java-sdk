package com.wiseasy.openapi;

import com.wiseasy.openapi.request.OpenApiRequest;
import com.wiseasy.openapi.response.OpenApiResponse;

/**
 * 开放服务网关客户端接口
 */
public interface OpenApiClient {

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
                                                 String osgRsaPublicKey, String language) throws OpenApiException;

}
