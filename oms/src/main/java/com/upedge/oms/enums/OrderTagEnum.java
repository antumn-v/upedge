package com.upedge.oms.enums;

public enum  OrderTagEnum {

    /**
     * 查所有
     */
    ALL(null,null,null,null),

    UNQUOTED(0,0,0,0),

    QUOTING(0,0,0,1),

    PART_QUOTED(0,0,0,2),

    /**
     * 未付款
     */
    QUOTED(0,0,0,3),

    /**
     * 已付款未发货
     */
    PAID(1,0,0,null),

    /**
     * 已发货
     */
    SHIPPED(1,0,1,null),

    /**
     * 取消订单
     */
    CANCELED(-1,0,0,null),

    /**
     * 退款：包括退款申请中 部分退款  全部退款
     */
    REFUNDS(1,1,null,null),

    /**
     * 补发
     */
    RESHIPPED(1,0,1,null);



    Integer payState;

    Integer shipState;

    Integer refundState;

    Integer quoteState;


    OrderTagEnum(Integer payState, Integer refundState, Integer shipState,Integer quoteState) {
        this.payState = payState;
        this.refundState = refundState;
        this.shipState = shipState;
        this.quoteState = quoteState;
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

    public Integer getQuoteState() {
        return quoteState;
    }

    public void setQuoteState(Integer quoteState) {
        this.quoteState = quoteState;
    }
}
