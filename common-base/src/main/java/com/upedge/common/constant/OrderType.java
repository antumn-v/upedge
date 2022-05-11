package com.upedge.common.constant;

import com.upedge.common.enums.TransactionType;

public class OrderType {

    public static final int RECHARGE = 0;

    public static final int STOCK = 1;

    public static final int NORMAL = 2;

    public static final int WHOLESALE = 3;

    //额外服务海外仓订单
    public static final int EXTRA_SERVICE_OVERSEA_WAREHOUSE = 4;
    //额外服务批发订单
    public static final int EXTRA_SERVICE_WHOLESALE = 5;
    //产品拍摄服务
    public static final int EXTRA_SERVICE_PRODUCT_PHOTOGRAPHY = 6;
    //热卖品
    public static final int EXTRA_SERVICE_WINNING_PRODUCT = 7;


    public static TransactionType getOrderPayTransactionType(Integer orderType){
        TransactionType transactionType = null;
        switch (orderType) {
            case OrderType.NORMAL:
                transactionType = TransactionType.BALANCE_PAY_ORDER;
                break;
            case OrderType.STOCK:
                transactionType = TransactionType.BALANCE_PAY_STOCK;
                break;
            case OrderType.EXTRA_SERVICE_WHOLESALE:
                transactionType = TransactionType.BALANCE_PAY_EXTRA_SERVICE_WHOLESALE;
                break;
            case OrderType.EXTRA_SERVICE_OVERSEA_WAREHOUSE:
                transactionType = TransactionType.BALANCE_PAY_OVERSEA_WAREHOUSE_SERVICE_ORDER;
                break;
            case OrderType.EXTRA_SERVICE_PRODUCT_PHOTOGRAPHY:
                transactionType = TransactionType.BALANCE_PAY_PRODUCT_PHOTOGRAPHY;
                break;
            case OrderType.EXTRA_SERVICE_WINNING_PRODUCT:
                transactionType = TransactionType.BALANCE_PAY_EXTRA_SERVICE_WINNING_PRODUCT;
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + orderType);
        }
        return transactionType;
    }
}
