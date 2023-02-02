package com.upedge.oms.enums;

public enum  StockOrderTagEnum {

    All(null,null,null),

    To_Order(0,0,0),

    Paid(1,0,0),

    Refund(1,1,null),

    Canceled(-1,0,null),

    Arrived(1,0,1);


    Integer payState;

    Integer refundState;

    Integer purchaseState;

    StockOrderTagEnum(Integer payState, Integer refundState,Integer purchaseState) {
        this.payState = payState;
        this.refundState = refundState;
        this.purchaseState = purchaseState;
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

    public Integer getPurchaseState() {
        return purchaseState;
    }

    public void setPurchaseState(Integer purchaseState) {
        this.purchaseState = purchaseState;
    }
}
