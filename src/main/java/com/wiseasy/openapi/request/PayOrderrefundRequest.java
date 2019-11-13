package com.wiseasy.openapi.request;

import com.alibaba.fastjson.JSONObject;
import com.wiseasy.openapi.response.PayOrderrefundResponse;

/**
 * @Auther: liqie
 * @Date: 2018/11/2 21:49
 * @Description:
 */
public class PayOrderrefundRequest extends OpenApiRequest<PayOrderrefundResponse>{

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
     * 商户退款单号
     */
    private String out_refund_no;

    /**
     * 退款金额
     */
    private Double refund_amount;

    /**
     * 退款原因
     */
    private String refund_desc;

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

    public String getOut_refund_no() {
        return out_refund_no;
    }

    public void setOut_refund_no(String out_refund_no) {
        this.out_refund_no = out_refund_no;
    }

    public Double getRefund_amount() {
        return refund_amount;
    }

    public void setRefund_amount(Double refund_amount) {
        this.refund_amount = refund_amount;
    }

    public String getRefund_desc() {
        return refund_desc;
    }

    public void setRefund_desc(String refund_desc) {
        this.refund_desc = refund_desc;
    }

    public JSONObject getExtension_parameters() {
        return extension_parameters;
    }

    public void setExtension_parameters(JSONObject extension_parameters) {
        this.extension_parameters = extension_parameters;
    }
}
