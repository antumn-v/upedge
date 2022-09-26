package com.upedge.oms.enums;

public enum  OrderTagEnum {

    /**
     * 查所有
     */
    ALL(null,null,null,null,null,null),

    UNQUOTED(0,0,0,0,null,null),

    QUOTING(0,0,0,1,null,null),

    PART_QUOTED(0,0,0,2,null,null),

    /**
     * 未付款
     */
    QUOTED(0,0,0,3,null,null),

    /**
     * 已付款未发货
     */
    PAID(1,0,0,null,null,null),

    /**
     * 已发货
     */
    SHIPPED(1,1,0,null,null,null),

    /**
     * 取消订单
     */
    CANCELED(-1,0,0,null,null,null),

    /**
     * 退款：包括退款申请中 部分退款  全部退款
     */
    REFUNDS(1,null,1,null,null,null),

    /**
     * 补发
     */
    RESHIPPED(1,0,1,null,null,null),

    ADMIN_ALL(1,null,null,null,null,null),

    PACK_FAILED(1,0,null,null,null,0),

    IN_STOCK(1,0,null,null,1,1),

    OUT_STOCK(1,0,null,null,0,1),

    ADMIN_SHIPPED(1,1,null,null,null,null),

    SHUNT(1,null,null,null,null,-1);



    Integer payState;

    Integer shipState;

    Integer refundState;

    Integer quoteState;

    Integer stockState;

    Integer packState;


    OrderTagEnum(Integer payState, Integer shipState, Integer refundState, Integer quoteState, Integer stockState, Integer packState) {
        this.payState = payState;
        this.shipState = shipState;
        this.refundState = refundState;
        this.quoteState = quoteState;
        this.stockState = stockState;
        this.packState = packState;
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

    public Integer getStockState() {
        return stockState;
    }

    public void setStockState(Integer stockState) {
        this.stockState = stockState;
    }

    public Integer getPackState() {
        return packState;
    }

    public void setPackState(Integer packState) {
        this.packState = packState;
    }
}
