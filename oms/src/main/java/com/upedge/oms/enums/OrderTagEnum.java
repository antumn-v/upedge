package com.upedge.oms.enums;

public enum  OrderTagEnum {

    /**
     * 查所有
     */
    ALL(null,null,null),

    /**
     * 未付款
     */
    TO_ORDER(0,0,0),

    /**
     * 已付款未发货
     */
    IN_PROCESSING(1,0,0),

    /**
     * 已发货
     */
    SHIPPED(1,0,1),

    /**
     * 取消订单
     */
    CANCELED(-1,0,0),

    /**
     * 退款：包括退款申请中 部分退款  全部退款
     */
    REFUNDS(1,1,null),

    /**
     * 补发
     */
    RESHIPPED(1,0,1);



    Integer payState;

    Integer shipState;

    Integer refundState;


    OrderTagEnum(Integer payState, Integer refundState, Integer shipState) {
        this.payState = payState;
        this.refundState = refundState;
        this.shipState = shipState;
    }

    public Integer getPayState() {
        return payState;
    }

    public void setPayState(Integer payState) {
        this.payState = payState;
    }

    public Integer getShipState() {
        return shipState;
    }

    public void setShipState(Integer shipState) {
        this.shipState = shipState;
    }

    public Integer getRefundState() {
        return refundState;
    }

    public void setRefundState(Integer refundState) {
        this.refundState = refundState;
    }


}
