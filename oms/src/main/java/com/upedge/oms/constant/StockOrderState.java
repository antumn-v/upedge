package com.upedge.oms.constant;

/**
 * @author 海桐
 */
public class StockOrderState {

    final public static int UN_PAID = 0;

    final public static int PAID = 1;

    final public static int CANCELED = -1;

    final public static int PENDING = 2;

    final public static int UN_REFUND = 0;

    final public static int REFUND_PROCESSING = 1;

    final public static int REFUND_CANCELED = 2;

    final public static int PARTIAL_REFUND = 3;

    final public static int FULL_REFUND = 4;
}
