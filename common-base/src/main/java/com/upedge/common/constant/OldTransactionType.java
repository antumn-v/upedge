package com.upedge.common.constant;

public class OldTransactionType {

    //1:转账充值 2:余额支付 3paypal支付订单 4订单退款 5余额还款,6paypal充值，
    // 7paypal还款  8内部转账收款 9内部转账扣款 10:余额支付备库订单  11paypal支付备库订单，12:账外提现，13佣金提现 14备库单退款
    // 15 备库单还款信用额度 16payoneer充值 17payoneer还款 18返点奖励，19余额支付批发订单，20 paypal支付批发订单，21批发订单退款，
    // 22批发订单退款还款信用额度 23账外提现取消 24内部转账收款还信用额度，25：payoneer api充值，26：payoneer api充值还款


    //交易类型：支付/扣款 = 0，退款/收款 = 1，

    //订单类型：充值，普通，备库，批发，转账，提现,还款

    //支付方式：账户，paypal，payoneer，佣金

    public final static int TRANSFER_RECHARGE = 1;

    public final static int BALANCE_PAY_ORDER = 2;

    public final static int PAYPAL_PAY_ORDER = 3;

    public final static int NORMAL_ORDER_REFUND = 4;

    public final static int TRANSFER_REPAYMENT = 5;

    public final static int PAYPAY_RECHARGE = 6;

    public final static int PAYPAL_REPAYMENT = 7;

    public final static int INNER_TRANSFER_PAYEE = 8;

    public final static int INNER_TRANSFER_PAYER = 9;

    public final static int BALANCE_PAY_STOCK = 10;

    public final static int PAYPAL_PAY_STOCK = 11;

    public final static int BALANCE_WITHDRAW = 12;

    public final static int COMMISSION_WITHDRAW = 13;

    public final static int STOCK_ORDER_REFUND = 14;

    public final static int STOCK_ORDER_REFUND_REPAYMENT = 15;

    public final static int PAYONEER_RECHARGE = 16;

    public final static int PAYONEER_REPAYMENT = 17;

    public final static int REBATE_REWARD = 18;

    public final static int BALANCE_PAY_WHOLESALE = 19;

    public final static int PAYPAL_PAY_WHOLESALE = 20;

    public final static int WHOLESALE_ORDER_REFUND = 21;

    public final static int WHOLESALE_ORDER_REFUND_REPAYMENT = 22;

    public final static int BALANCE_WITHDRAW_CANCEL = 23;

    public final static int INNER_TRANSFER_PAYEE_REPAYMENT = 24;

    public final static int PAYONEER_API_RECHARGE = 25;

    public final static int PAYONEER_API_REPAYMENT = 26;


}
