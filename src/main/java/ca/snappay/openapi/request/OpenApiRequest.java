package com.wiseasy.openapi.request;

import com.wiseasy.openapi.response.OpenApiResponse;

import java.lang.reflect.ParameterizedType;

/**
 * @Auther: liqie
 * @Date: 2018/11/2 16:40
 * @Description:  API请求对象
 */
public abstract class OpenApiRequest <T extends OpenApiResponse>{

    /**
     * 应用ID,开放服务网关分配给开发者的应用ID
     */
    private String app_id;

    /**
     * 商户生成签名字符串所使用的签名算法类型,目前支持RSA和MD5，推荐使用RSA
     */
    private String sign_type;

    /**
     * 获取API输出对象类型
     * @return
     */
    public  Class<T> getResponseClass(){
        return (Class<T>)((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    /**
     * 获取API请求接口方法名
     * @return
     */
    public String getRequestMethod(){
        String className = this.getClass().getSimpleName();
        className = className.replace("Request", "");
        char[] chars = className.toCharArray();
        String method = "";
        for (char c : chars){
            if("".equals(method)){
                method += (String.valueOf(c)).toLowerCase();
            }else{
                if( c >='A' && c <= 'Z'){
                    method += "." + (String.valueOf(c)).toLowerCase();
                }else{
                    method += String.valueOf(c);
                }
            }
        }
        return method;
    }


    public String getApp_id() {
        return app_id;
    }

    public void setApp_id(String app_id) {
        this.app_id = app_id;
    }

    public String getSign_type() {
        return sign_type;
    }

    public void setSign_type(String sign_type) {
        this.sign_type = sign_type;
    }
}
