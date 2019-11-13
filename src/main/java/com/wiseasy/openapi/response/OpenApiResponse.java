package com.wiseasy.openapi.response;

import com.wiseasy.openapi.utils.Constants;

import java.util.List;

/**
 * @Auther: liqie
 * @Date: 2018/11/2 16:40
 * @Description:
 */
public class OpenApiResponse{

    /**
     * 状态码，0 表示请求成功，其他表示失败
     */
    private String code;

    /**
     * 错误信息
     */
    private String msg;

    /**
     * 满足条件的数据总数，获取分页数据时有效
     */
    private Long total;

    /**
     * 数组中包括1个或多个JSONObject，具体参数由各业务API定义
     */
    private List data;

    /**
     * 接口序列号，用于错误查找及请求记录
     */
    private String psn;

    /**
     * 签名
     */
    private String sign;

    /**
     * 判断接口请求是否成功
     * @return
     */
    public boolean isSuccess(){
        return Constants.CODE_SUCCESS.equals(code);
    }


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public List getData() {
        return data;
    }

    public void setData(List data) {
        this.data = data;
    }

    public String getPsn() {
        return psn;
    }

    public void setPsn(String psn) {
        this.psn = psn;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}
