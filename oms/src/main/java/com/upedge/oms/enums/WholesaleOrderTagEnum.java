package com.upedge.oms.enums;

public enum WholesaleOrderTagEnum {

    TO_ORDER(0,0,0),

    PAID(1,0,0),

    SHIPPED(1,0,1),

    CANCELED(-1,0,0),

    REFUND(1,1,null);


    Integer payState;

    Integer shipState;

    Integer refundState;


    WholesaleOrderTagEnum(Integer payState, Integer refundState, Integer shipState) {
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
