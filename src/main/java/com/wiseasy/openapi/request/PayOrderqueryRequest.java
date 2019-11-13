package com.wiseasy.openapi.request;

import com.alibaba.fastjson.JSONObject;
import com.wiseasy.openapi.response.PayOrderqueryResponse;

/**
 * @Auther: liqie
 * @Date: 2018/11/2 21:49
 * @Description:
 */
public class PayOrderqueryRequest extends OpenApiRequest<PayOrderqueryResponse>{

    /**
     * 服务商编号
     */
    private String sp_id;

    /**
     * 商户号
     */
    private String merchant_no;

    /**
     * 门店编号
     */
    private String store_no;

    /**
     * 终端编号
     */
    private String terminal_no;

    /**
     * 商户订单号
     */
    private String out_order_no;

    /**
     * 交易号
     */
    private String trans_no;

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

    public String getStore_no() {
        return store_no;
    }

    public void setStore_no(String store_no) {
        this.store_no = store_no;
    }

    public String getTerminal_no() {
        return terminal_no;
    }

    public void setTerminal_no(String terminal_no) {
        this.terminal_no = terminal_no;
    }

    public String getOut_order_no() {
        return out_order_no;
    }

    public void setOut_order_no(String out_order_no) {
        this.out_order_no = out_order_no;
    }

    public String getTrans_no() {
        return trans_no;
    }

    public void setTrans_no(String trans_no) {
        this.trans_no = trans_no;
    }

    public JSONObject getExtension_parameters() {
        return extension_parameters;
    }

    public void setExtension_parameters(JSONObject extension_parameters) {
        this.extension_parameters = extension_parameters;
    }
}
