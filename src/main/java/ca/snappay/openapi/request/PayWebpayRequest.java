package com.wiseasy.openapi.request;

import com.alibaba.fastjson.JSONObject;
import com.wiseasy.openapi.response.PayWebpayResponse;

/**
 * @Auther: liqie
 * @Date: 2018/11/2 21:49
 * @Description:
 */
public class PayWebpayRequest extends OpenApiRequest<PayWebpayResponse>{

    /**
     * 服务商编号
     */
    private String sp_id;

    /**
     * 商户号
     */
    private String merchant_no;

    /**
     * 商户号
     */
    private String store_no;

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
     * 后台通知地址
     */
    private String notify_url;

    /**
     * 前台回调地址
     */
    private String return_url;

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

    /**
     * 浏览器类型
     * PC:电脑网站支付，默认
     * WAP:手机网站支付
     */
    private String browser_type;

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

    public String getNotify_url() {
        return notify_url;
    }

    public void setNotify_url(String notify_url) {
        this.notify_url = notify_url;
    }

    public String getReturn_url() {
        return return_url;
    }

    public void setReturn_url(String return_url) {
        this.return_url = return_url;
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

    public JSONObject getExtension_parameters() {
        return extension_parameters;
    }

    public void setExtension_parameters(JSONObject extension_parameters) {
        this.extension_parameters = extension_parameters;
    }

    public String getBrowser_type() {
        return browser_type;
    }

    public void setBrowser_type(String browser_type) {
        this.browser_type = browser_type;
    }
}
