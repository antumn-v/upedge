package com.upedge.oms.enums;

public enum  StockOrderTagEnum {

    All(null,null),

    To_Order(0,0),

    Paid(1,0),

    Refund(1,1),

    Canceled(-1,0);


    Integer payState;

    Integer refundState;

    StockOrderTagEnum(Integer payState, Integer refundState) {
        this.payState = payState;
        this.refundState = refundState;
    }



    public Integer getPayState() {
        return payState;
    }

    public void setPayState(Integer payState) {
        this.payState = payState;
    }

    public Integer getRefundState() {
        return refundState;
    }

    public void setRefundState(Integer refundState) {
        this.refundState = refundState;
    }
}
