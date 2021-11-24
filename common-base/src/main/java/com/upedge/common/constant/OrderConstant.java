package com.upedge.common.constant;

public class OrderConstant {

    public static Integer PAY_STATE_UNPAID = 0;
    public static Integer PAY_STATE_PAID = 1;
    public static Integer PAY_STATE_CANCELED = -1;
    public static Integer PAY_STATE_PENDING = 2;


    public static Integer NO_REFUND = 0;
    public static Integer REFUND_PROCESSING = 1;
    public static Integer PART_REFUND = 2;
    public static Integer FULL_REFUND = 3;

    public static Integer SHIP_STATE_UNSHIPPED = 0;
    public static Integer SHIP_STATE_SHIPPED = 1;

}
