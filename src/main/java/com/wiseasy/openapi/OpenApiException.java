/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2012 All Rights Reserved.
 */
package com.wiseasy.openapi;


/**
 * 异常对象
 * @author liqie
 */
public class OpenApiException extends Exception {

    private String            errCode;
    private String            errMsg;

    public OpenApiException() {
        super();
    }


    public OpenApiException(String errCode, String errMsg) {
        super(errCode + ":" + errMsg);
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    public String getErrCode() {
        return this.errCode;
    }

    public String getErrMsg() {
        return this.errMsg;
    }

}