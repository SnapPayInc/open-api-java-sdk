package com.wiseasy.openapi.response;

/**
 * @Auther: liqie
 * @Date: 2018/11/2 21:49
 * @Description:
 */
public class PayWebpayResponse extends OpenApiResponse{

    /**
     * 交易号
     */
    private String trans_no;

    /**
     * 商户订单号
     */
    private String out_order_no;

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
     * 交易状态:
     * USERPAYING-交易创建，等待买家付款
     * CLOSE-交易关闭，包括撤销关闭，超时未支付关闭，交易失败关闭
     * SUCCESS-交易完成
     */
    private String trans_status;

    /**
     * pc收银台支付地址
     */
    private String pcpay_url;

    /**
     * 请求pc收银台支付地址的http方法 GET/POST
     */
    private String http_method;

    public String getTrans_no() {
        return trans_no;
    }

    public void setTrans_no(String trans_no) {
        this.trans_no = trans_no;
    }

    public String getOut_order_no() {
        return out_order_no;
    }

    public void setOut_order_no(String out_order_no) {
        this.out_order_no = out_order_no;
    }

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

    public String getTrans_status() {
        return trans_status;
    }

    public void setTrans_status(String trans_status) {
        this.trans_status = trans_status;
    }

    public String getPcpay_url() {
        return pcpay_url;
    }

    public void setPcpay_url(String pcpay_url) {
        this.pcpay_url = pcpay_url;
    }

    public String getHttp_method() {
        return http_method;
    }

    public void setHttp_method(String http_method) {
        this.http_method = http_method;
    }
}