package com.wiseasy.openapi.request;

import com.alibaba.fastjson.JSONObject;
import com.wiseasy.openapi.response.PayOrdercancelResponse;

/**
 * @Auther: liqie
 * @Date: 2018/11/2 21:49
 * @Description:
 */
public class PayOrdercancelRequest extends OpenApiRequest<PayOrdercancelResponse>{

    /**
     * 服务商编号
     */
    private String sp_id;

    /**
     * 商户号
     */
    private String merchant_no;

    /**
     * 商户订单号
     */
    private String out_order_no;

    /**
     * 商户撤销单号
     */
    private String out_cancel_no;

    /**
     * 扩展参数
     */
    private JSONObject extension_parameters;

    public String getSp_id() {
        return sp_id;
    }

    public void setSp_id(String sp_id) {
        this.sp_id = sp_id;
    }

    public String getMerchant_no() {
        return merchant_no;
    }

    public void setMerchant_no(String merchant_no) {
        this.merchant_no = merchant_no;
    }

    public String getOut_order_no() {
        return out_order_no;
    }

    public void setOut_order_no(String out_order_no) {
        this.out_order_no = out_order_no;
    }

    public String getOut_cancel_no() {
        return out_cancel_no;
    }

    public void setOut_cancel_no(String out_cancel_no) {
        this.out_cancel_no = out_cancel_no;
    }

    public JSONObject getExtension_parameters() {
        return extension_parameters;
    }

    public void setExtension_parameters(JSONObject extension_parameters) {
        this.extension_parameters = extension_parameters;
    }
}
