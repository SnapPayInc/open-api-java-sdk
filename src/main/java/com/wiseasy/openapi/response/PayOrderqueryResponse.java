package com.wiseasy.openapi.response;

import com.alibaba.fastjson.JSONArray;


/**
 * @Auther: liqie
 * @Date: 2018/11/2 21:49
 * @Description:
 */
public class PayOrderqueryResponse extends OpenApiResponse{

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
     * 目前支持的支付方式有：
     * ALIPAY-支付宝
     * WECHATPAY-微信支付
     * UNIONPAY_QRCODE-银联二维码
     * UNIONPAY-银联国际
     */
    private String payment_method;

    /**
     * 支付操作方式
     * 4：扫码支付
     * 5：条码支付
     * 6：H5支付
     * 8：APP支付
     * 9：PC网页支付
     */
    private Integer pay_operation_method;

    /**
     * 买家用户标识
     */
    private String pay_user_account_id;

    /**
     * 买家账号
     */
    private String pay_user_account_name;

    /**
     * 标价币种
     */
    private String trans_currency;

    /**
     * 汇率
     */
    private String exchange_rate;

    /**
     * 交易总金额
     */
    private Double trans_amount;

    /**
     * 顾客实付金额
     */
    private Double customer_paid_amount;

    /**
     * 支付通道商户优惠金额
     */
    private Double discount_bmopc;

    /**
     * 支付通道优惠金额
     */
    private String discount_bpc;

    /**
     * 交易完成时间
     */
    private String trans_end_time;

    /**
     * 商品优惠信息
     */
    private JSONArray discount_goods_detail;

    /**
     * 卡属性
     * UNIONPAY_QRCODE-银联二维码支付方式会返回此参数，取值如下：
     * 01:借记卡
     * 02:贷记卡
     */
    private String card_attr;

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

    public String getPayment_method() {
        return payment_method;
    }

    public void setPayment_method(String payment_method) {
        this.payment_method = payment_method;
    }

    public Integer getPay_operation_method() {
        return pay_operation_method;
    }

    public void setPay_operation_method(Integer pay_operation_method) {
        this.pay_operation_method = pay_operation_method;
    }

    public String getPay_user_account_id() {
        return pay_user_account_id;
    }

    public void setPay_user_account_id(String pay_user_account_id) {
        this.pay_user_account_id = pay_user_account_id;
    }

    public String getPay_user_account_name() {
        return pay_user_account_name;
    }

    public void setPay_user_account_name(String pay_user_account_name) {
        this.pay_user_account_name = pay_user_account_name;
    }

    public String getTrans_currency() {
        return trans_currency;
    }

    public void setTrans_currency(String trans_currency) {
        this.trans_currency = trans_currency;
    }

    public String getExchange_rate() {
        return exchange_rate;
    }

    public void setExchange_rate(String exchange_rate) {
        this.exchange_rate = exchange_rate;
    }

    public Double getTrans_amount() {
        return trans_amount;
    }

    public void setTrans_amount(Double trans_amount) {
        this.trans_amount = trans_amount;
    }

    public Double getCustomer_paid_amount() {
        return customer_paid_amount;
    }

    public void setCustomer_paid_amount(Double customer_paid_amount) {
        this.customer_paid_amount = customer_paid_amount;
    }

    public Double getDiscount_bmopc() {
        return discount_bmopc;
    }

    public void setDiscount_bmopc(Double discount_bmopc) {
        this.discount_bmopc = discount_bmopc;
    }

    public String getDiscount_bpc() {
        return discount_bpc;
    }

    public void setDiscount_bpc(String discount_bpc) {
        this.discount_bpc = discount_bpc;
    }

    public String getTrans_end_time() {
        return trans_end_time;
    }

    public void setTrans_end_time(String trans_end_time) {
        this.trans_end_time = trans_end_time;
    }

    public JSONArray getDiscount_goods_detail() {
        return discount_goods_detail;
    }

    public void setDiscount_goods_detail(JSONArray discount_goods_detail) {
        this.discount_goods_detail = discount_goods_detail;
    }

    public String getCard_attr() {
        return card_attr;
    }

    public void setCard_attr(String card_attr) {
        this.card_attr = card_attr;
    }
}
