package com.upedge.oms.enums;

public enum  StockOrderTagEnum {

    To_Order(0,0),

    Paid(1,0),

    Partial_Refund(1,3),

    Full_Refund(1,4),

    Canceled(-1,0);
    ;

    int payState;

    int refundState;

    StockOrderTagEnum(int payState, int refundState) {
        this.payState = payState;
        this.refundState = refundState;
    }



    public int getPayState() {
        return payState;
    }

    public void setPayState(int payState) {
        this.payState = payState;
    }

    public int getRefundState() {
        return refundState;
    }

    public void setRefundState(int refundState) {
        this.refundState = refundState;
    }
}
