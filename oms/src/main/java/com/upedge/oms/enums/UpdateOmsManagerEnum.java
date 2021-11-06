package com.upedge.oms.enums;

/**
 * 夕雾
 * 2021/07/03
 * 根据customerid修改managerCode    此枚举存的是将要修改的表明
 */

public enum UpdateOmsManagerEnum {
    ORDER("`order`"),
    ORDER_REFUND("order_refund"),
    STOCK_ORDER("stock_order"),
    STOCK_ORDER_REFUND("stock_order_refund"),
    SUPPORT_TICKETS("support_tickets"),
    WHOLESALE_ORDER("wholesale_order"),
    WHOLESALE_REFUND("wholesale_refund"),
    ;

    private String tableName;

    UpdateOmsManagerEnum(String tableName) {
        this.tableName = tableName;
    }

    UpdateOmsManagerEnum() {
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

}
