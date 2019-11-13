package com.wiseasy.openapi;


import com.alibaba.fastjson.JSON;
import com.wiseasy.openapi.request.PayBarcodepayRequest;
import com.wiseasy.openapi.response.PayBarcodepayResponse;
import com.wiseasy.openapi.utils.Constants;
import org.junit.Test;

/**
 * Unit test for simple OpenApiClient.
 */
public class BarcodepayTest
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void test() {
        //实例化客户端
        OpenApiClient openapiClient = new DefaultOpenApiClient();
        //实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：pay.barcodepay
        PayBarcodepayRequest request = new PayBarcodepayRequest();
        //SDK已经封装了公共参数，这里只需要传入业务参数
        request.setApp_id("fd5ecb65f8ac9a1c");
        request.setSign_type(Constants.SIGN_TYPE_MD5);
        // 此处只是演示，其他参数请根据接口定义进行传输
        //.....

        String md5KeyOrRsaPrivateKey = "1e2281e855d43ad528c8ee63a7a40c3a";

        PayBarcodepayResponse response = null;
        try {
            response = openapiClient.execute(request, Constants.OSG_SERVER_URL_DEV, md5KeyOrRsaPrivateKey, null, "en-US");
        } catch (OpenApiException e) {
            // 调用失败，输出错误信息
            System.out.println("error:" + e.getErrCode() + "->>" + e.getErrMsg());
            return;
        }

        if(response.isSuccess()){

        }else{
            // 调用失败，输出错误信息
            System.out.println("error:" + response.getCode() + "->>" + response.getMsg());
        }

    }
}
