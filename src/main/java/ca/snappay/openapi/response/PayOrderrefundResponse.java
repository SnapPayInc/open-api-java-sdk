package com.wiseasy.openapi.response;

/**
 * @Auther: liqie
 * @Date: 2018/11/2 21:49
 * @Description:
 */
public class PayOrderrefundResponse extends OpenApiResponse{

    /**
     * 交易号
     */
    private String trans_no;

    /**
     * 商户订单号
     */
    private String out_order_no;

    /**
     * 交易状态:
     * USERPAYING-交易创建，等待买家付款
     * CLOSE-交易关闭，包括撤销关闭，超时未支付关闭，交易失败关闭
     * SUCCESS-交易完成
     */
    private String trans_status;

    /**
     * 退款交易号
     */
    private String refund_trans_no;

    /**
     * 退款完成时间
     */
    private String refund_trans_end_time;

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

    public String getTrans_status() {
        return trans_status;
    }

    public void setTrans_status(String trans_status) {
        this.trans_status = trans_status;
    }

    public String getRefund_trans_no() {
        return refund_trans_no;
    }

    public void setRefund_trans_no(String refund_trans_no) {
        this.refund_trans_no = refund_trans_no;
    }

    public String getRefund_trans_end_time() {
        return refund_trans_end_time;
    }

    public void setRefund_trans_end_time(String refund_trans_end_time) {
        this.refund_trans_end_time = refund_trans_end_time;
    }
}
