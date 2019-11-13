package com.wiseasy.openapi.request;

import com.alibaba.fastjson.JSONArray;
import com.wiseasy.openapi.response.PayQrcodepayResponse;

/**
 * @Auther: liqie
 * @Date: 2018/11/2 21:49
 * @Description:
 */
public class PayQrcodepayRequest extends OpenApiRequest<PayQrcodepayResponse>{

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
     * 终端号
     */
    private String terminal_no;

    /**
     * 支付方式
     * 目前支持的支付方式有：
     * ALIPAY-支付宝
     * WECHATPAY-微信支付
     * UNIONPAY_QRCODE-银联二维码
     */
    private String payment_method;

    /**
     * 商户订单号
     */
    private String out_order_no;

    /**
     * 标价金额
     */
    private Double trans_amount;

    /**
     * 标价币种
     */
    private String trans_currency;

    /**
     * 商品描述
     */
    private String description;

    /**
     * 参与优惠计算的金额
     */
    private Double discountable_amount;

    /**
     * 订单包含的商品列表信息
     */
    private JSONArray goods_detail;

    /**
     * 后台通知地址
     */
    private String notify_url;

    /**
     * 商户附加信息
     */
    private String attach;

    /**
     * 经度
     */
    private String longitude;

    /**
     * 纬度
     */
    private String latitude;

    /**
     * 订单有效分钟数
     */
    private Integer effective_minutes;

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

    public String getPayment_method() {
        return payment_method;
    }

    public void setPayment_method(String payment_method) {
        this.payment_method = payment_method;
    }

    public String getOut_order_no() {
        return out_order_no;
    }

    public void setOut_order_no(String out_order_no) {
        this.out_order_no = out_order_no;
    }

    public Double getTrans_amount() {
        return trans_amount;
    }

    public void setTrans_amount(Double trans_amount) {
        this.trans_amount = trans_amount;
    }

    public String getTrans_currency() {
        return trans_currency;
    }

    public void setTrans_currency(String trans_currency) {
        this.trans_currency = trans_currency;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getDiscountable_amount() {
        return discountable_amount;
    }

    public void setDiscountable_amount(Double discountable_amount) {
        this.discountable_amount = discountable_amount;
    }

    public JSONArray getGoods_detail() {
        return goods_detail;
    }

    public void setGoods_detail(JSONArray goods_detail) {
        this.goods_detail = goods_detail;
    }

    public String getNotify_url() {
        return notify_url;
    }

    public void setNotify_url(String notify_url) {
        this.notify_url = notify_url;
    }

    public String getAttach() {
        return attach;
    }

    public void setAttach(String attach) {
        this.attach = attach;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public Integer getEffective_minutes() {
        return effective_minutes;
    }

    public void setEffective_minutes(Integer effective_minutes) {
        this.effective_minutes = effective_minutes;
    }
}
