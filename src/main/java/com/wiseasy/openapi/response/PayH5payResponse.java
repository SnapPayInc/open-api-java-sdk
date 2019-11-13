package com.wiseasy.openapi.response;

/**
 * @Auther: liqie
 * @Date: 2018/11/2 21:49
 * @Description:
 */
public class PayH5payResponse extends OpenApiResponse{

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
     * H5收银台支付地址
     */
    private String h5pay_url;

    /**
     * 支付宝交易号
     */
    private String alipay_trade_no;

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

    public String getH5pay_url() {
        return h5pay_url;
    }

    public void setH5pay_url(String h5pay_url) {
        this.h5pay_url = h5pay_url;
    }

    public String getAlipay_trade_no() {
        return alipay_trade_no;
    }

    public void setAlipay_trade_no(String alipay_trade_no) {
        this.alipay_trade_no = alipay_trade_no;
    }
}
