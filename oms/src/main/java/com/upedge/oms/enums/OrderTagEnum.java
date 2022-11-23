package com.upedge.oms.enums;

public enum  OrderTagEnum {

    /**
     * 查所有
     */
    ALL(null,null,null,null,null,null,null,null),

    UNQUOTED(0,0,0,0,null,null,null,null),

    QUOTING(0,0,0,1,null,null,null,null),

    PART_QUOTED(0,0,0,2,null,null,null,null),

    /**
     * 未付款
     */
    QUOTED(0,0,0,3,null,null,null,null),

    /**
     * 已付款未发货
     */
    PAID(1,0,0,null,null,null,null,null),

    /**
     * 已发货
     */
    SHIPPED(1,1,0,null,null,null,null,null),

    /**
     * 取消订单
     */
    CANCELED(-1,0,0,null,null,null,null,null),

    /**
     * 退款：包括退款申请中 部分退款  全部退款
     */
    REFUNDS(1,null,1,null,null,null,null,null),

    /**
     * 补发
     */
    RESHIPPED(1,0,1,null,null,null,null,null),

    ADMIN_ALL(1,null,null,null,null,null,null,null),
    /**
     * 生包失败
     */
    PACK_FAILED(1,0,null,null,null,2,null,null),
    //处理中
    PACK_PENDING(1,0,0,null,null,0,null,null),
    //有货
    IN_STOCK(1,0,null,null,1,1,false,0),
    //缺货
    OUT_STOCK(1,0,null,null,0,1,false,0),
    //已发货
    ADMIN_SHIPPED(1,1,null,null,null,null,null,null),
    //已搁置
    SHUNT(1,null,null,null,null,-1,null,null),
    //面单已打印
    LABEL_PRINTED(1,0,null,null,1,1,true,1),
    //已拣货
    PICKED(1,0,null,null,1,1,false,1);



    Integer payState;

    Integer shipState;

    Integer refundState;
    //报价状态
    Integer quoteState;
    //缺货状态
    Integer stockState;
    //包裹状态
    Integer packState;
    //是否已打印面单
    Boolean isPrintLabel;
    //贱货状态
    Integer pickState;


    OrderTagEnum(Integer payState, Integer shipState, Integer refundState, Integer quoteState, Integer stockState, Integer packState,Boolean isPrintLabel,Integer pickState) {
        this.payState = payState;
        this.shipState = shipState;
        this.refundState = refundState;
        this.quoteState = quoteState;
        this.stockState = stockState;
        this.packState = packState;
        this.isPrintLabel = isPrintLabel;
        this.pickState = pickState;
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

    public Boolean getPrintLabel() {
        return isPrintLabel;
    }

    public void setPrintLabel(Boolean printLabel) {
        isPrintLabel = printLabel;
    }

    public Integer getPickState() {
        return pickState;
    }

    public void setPickState(Integer pickState) {
        this.pickState = pickState;
    }
}
