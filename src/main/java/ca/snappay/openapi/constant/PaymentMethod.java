package ca.snappay.openapi.constant;

/**
 * The payment method. Can be any of AliPay, WeChatPay, or UnionPay.
 *
 * @author shawndu
 * @version 1.0
 */
public enum PaymentMethod {

    ALIPAY("ALIPAY"),

    WECHATPAY("WECHATPAY"),

    UNIONPAY("UNIONPAY"),

    UNIONPAY_QR("UNIONPAY_QR"),

    SNAPLII("SNAPLII"),

    CREDITCARD_PAYBYTOKEN("CREDITCARD.PAYBYTOKEN");

    private String name;

    private PaymentMethod(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
