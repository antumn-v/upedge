package com.upedge.common.enums;

public class TransactionConstant {

    //支付/扣款 = 0，退款/收款 = 1，还款 = 2
    public enum TransactionType{
        PAY_CUT_PAYMENT(0,"支付/扣款"),
        REFUND_GAIN_AMOUNT(1,"退款/收款"),
        REPAYMENT(2,"还款");

        private Integer code;

        private String msg;

        public Integer getCode() {
            return code;
        }

        public String getMsg() {
            return msg;
        }

        TransactionType(Integer code, String msg) {
            this.code = code;
            this.msg = msg;
        }
    }

    //充值 = 0,备库 = 1，普通 = 2，批发 = 3，转账 = 4，提现 = 5
    public enum OrderType{
        RECHARGE(0,"充值"),
        STOCK_ORDER(1,"备库"),
        NORMAL_ORDER(2,"普通"),
        WHOLESALE_ORDER(3,"批发"),
        EXTRA_SERVICE_OVERSEA_WAREHOUSE(4,"海外仓备库"),
        EXTRA_SERVICE_WHOLESALE(5,"服务批发单"),
        EXTRA_SERVICE_PRODUCT_PHOTOGRAPHY(6,"产品拍照"),
        EXTRA_SERVICE_WINNING_PRODUCT(7,"热卖品");

        private Integer code;

        private String msg;

        public Integer getCode() {
            return code;
        }

        public String getMsg() {
            return msg;
        }

        OrderType(Integer code, String msg) {
            this.code = code;
            this.msg = msg;
        }
    }

    //账户 = 0，paypal = 1，payoneer = 2，佣金 = 3
    public enum PayMethod{
        ACCOUNT(0,"账户"),
        PAYPAL(1,"paypal"),
        PAYONEER(2,"payoneer"),
        COMMISSION(3,"佣金");

        private Integer code;

        private String msg;

        public int getCode() {
            return code;
        }

        public String getMsg() {
            return msg;
        }

        PayMethod(Integer code, String msg) {
            this.code = code;
            this.msg = msg;
        }
    }

    public static String transactionDesc(Integer transactionType, Integer orderType, Integer payMethod){
        String transactionDesc="";
        String transactionCode=""+transactionType+orderType+payMethod;
        switch (transactionCode){
            //transaction_type=1 order_type=0 pay_method=0
            case "100":
                return "转账充值";
            //transaction_type=2 order_type=0 pay_method=0
            case "200":
                return "转账充值还款";
            //transaction_type=1 order_type=0 pay_method=1
            case "101":
                return "paypal充值";
            //transaction_type=2 order_type=0 pay_method=1
            case "201":
                return "paypal充值还款";
            //transaction_type=1 order_type=0 pay_method=2
            case "102":
                return "payoneer充值";
            //transaction_type=2 order_type=0 pay_method=2
            case "202":
                return "payoneer充值还款";
            //transaction_type=0 order_type=2 pay_method=0
            case "020":
                return "余额支付普通订单";
            //transaction_type=0 order_type=2 pay_method=1
            case "021":
                return "paypal支付普通订单";
            //transaction_type=0 order_type=2 pay_method=2
            case "022":
                return "payoneer支付普通订单";
            //transaction_type=1 order_type=2 pay_method=0
            case "120":
                return "普通订单退款";
            //transaction_type=2 order_type=2 pay_method=0
            case "220":
                return "普通订单退款还款";
            //transaction_type=0 order_type=1 pay_method=0
            case "010":
                return "余额支付备库订单";
            //transaction_type=0 order_type=1 pay_method=1
            case "011":
                return "paypal支付备库订单";
            //transaction_type=0 order_type=1 pay_method=2
            case "012":
                return "payoneer支付备库订单";
            //transaction_type=1 order_type=1 pay_method=0
            case "110":
                return "备库订单退款";
            //transaction_type=2 order_type=1 pay_method=0
            case "210":
                return "备库订单退款还款";
            //transaction_type=0 order_type=3 pay_method=0
            case "030":
                return "余额支付批发订单";
            //transaction_type=0 order_type=3 pay_method=1
            case "031":
                return "paypal支付批发订单";
            //transaction_type=0 order_type=3 pay_method=2
            case "032":
                return "payoneer支付批发订单";
            //transaction_type=1 order_type=3 pay_method=0
            case "130":
                return "批发订单退款";
            //transaction_type=2 order_type=3 pay_method=0
            case "230":
                return "批发订单退款还款";
            //transaction_type=0 order_type=4 pay_method=0
            case "040":
                return "内部转账扣款";
            //transaction_type=1 order_type=4 pay_method=0
            case "140":
                return "内部转账收款";
            //transaction_type=2 order_type=4 pay_method=0
            case "240":
                return "内部转账收款还款";
            //transaction_type=0 order_type=5 pay_method=0
            case "050":
                return "账外提现";
            //transaction_type=1 order_type=5 pay_method=0
            case "150":
                return "账外提现取消";
            //transaction_type=1 order_type=5 pay_method=3
            case "153":
                return "佣金提现到余额";
            //	返点奖励
        }
        return transactionDesc;
    }

}
