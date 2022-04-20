package com.upedge.common.enums;

public enum TransactionType {

    //交易类型：支付/扣款 = 0，退款/收款 = 1，还款 = 2

    //订单类型：充值 = 0,备库 = 1，普通 = 2，批发 = 3，转账 = 4，提现 = 5

    //支付方式：账户 = 0，paypal = 1，payoneer = 2，佣金 = 3

    //括号里的内容（transactionType，orderType，payMethod）

    //转账充值
    TRANSFER_RECHARGE(1,0,0),

    //Paypal充值
    PAYPAY_RECHARGE(1,0,1),

    //payoneer充值
    PAYONEER_RECHARGE(1,0,2),

    //余额支付普通订单
    BALANCE_PAY_ORDER(0,2,0),

    //Paypal支付普通订单
    PAYPAL_PAY_ORDER(0,2,1),

    //余额支付备库订单
    BALANCE_PAY_STOCK(0,1,0),

    //paypal支付备库订单
    PAYPAL_PAY_STOCK(0,1,1),

    //余额支付批发订单
    BALANCE_PAY_WHOLESALE(0,3,0),

    //余额支付批发订单
    BALANCE_PAY_OVERSEA_WAREHOUSE_SERVICE_ORDER(0,4,0),

    //paypal支付批发订单
    PAYPAL_PAY_WHOLESALE(0,3,1),

    //普通订单退款
    ORDER_REFUND(1,2,0),

    //备库订单退款
    STOCK_ORDER_REFUND(1,1,0),

    //批发订单退款
    WHOLESALE_ORDER_REFUND(1,3,0),

    //内部转账收款
    INNER_TRANSFER_PAYEE(1,4,0),

    //内部转账付款
    INNER_TRANSFER_PAYER(0,4,0),

    //余额提现
    BALANCE_WITHDRAW(1,5,0),

    //佣金提现
    COMMISSION_WITHDRAW(1,5,3),

    //普通订单退款还款
    ORDER_REFUND_REPAYMENT(2,2,0),

    //批发单退款还款信用额度
    WHOLESALE_ORDER_REFUND_REPAYMENT(2,3,0),

    //Paypal充值还款信用额度
    PAYPAY_RECHARGE_REPAYMENT(2,0,1),

    //payoneer充值还款信用额度
    PAYONEER_RECHARGE_REPAYMENT(2,0,2),

    //备库单退款还款信用额度
    STOCK_ORDER_REFUND_REPAYMENT(2,1,0),

    //转账还款
    TRANSFER_RECHARGE_REPAYMENT(2,6,0),

    //内部转账收款还款
    INNER_TRANSFER_PAYEE_REPAYMENT(2,4,0),

    //余额提现取消
    BALANCE_WITHDRAW_CANCEL(1,5,0),

    //返点奖励
    REBATE_REWARD(1,0,0),
    ;

    Integer transactionType;

    Integer orderType;

    Integer payMethod;

    TransactionType(Integer transactionType, Integer orderType, Integer payMethod) {
        this.transactionType = transactionType;
        this.orderType = orderType;
        this.payMethod = payMethod;
    }

    public Integer getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(Integer transactionType) {
        this.transactionType = transactionType;
    }

    public Integer getOrderType() {
        return orderType;
    }

    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
    }

    public Integer getPayMethod() {
        return payMethod;
    }

    public void setPayMethod(Integer payMethod) {
        this.payMethod = payMethod;
    }
}
