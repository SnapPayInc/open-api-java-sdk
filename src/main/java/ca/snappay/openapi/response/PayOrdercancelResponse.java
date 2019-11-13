package com.wiseasy.openapi.response;

/**
 * @Auther: liqie
 * @Date: 2018/11/2 21:49
 * @Description:
 */
public class PayOrdercancelResponse extends OpenApiResponse{

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
    private String cancel_trans_no;

    /**
     * 退款完成时间
     */
    private String cancel_trans_end_time;

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

    public String getCancel_trans_no() {
        return cancel_trans_no;
    }

    public void setCancel_trans_no(String cancel_trans_no) {
        this.cancel_trans_no = cancel_trans_no;
    }

    public String getCancel_trans_end_time() {
        return cancel_trans_end_time;
    }

    public void setCancel_trans_end_time(String cancel_trans_end_time) {
        this.cancel_trans_end_time = cancel_trans_end_time;
    }
}
