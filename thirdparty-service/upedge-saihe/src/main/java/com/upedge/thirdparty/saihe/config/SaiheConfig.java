package com.upedge.thirdparty.saihe.config;

public class SaiheConfig {

    public static final String ORDER_URL="http://rakj.irobotbox.com/Api/API_Irobotbox_Orders.asmx";
    public static final String PRODUCT_URL="http://rakj.irobotbox.com/Api/API_ProductInfoManage.asmx";

    //用户名
    public static final String USERNAME="15924173139";//"15924173139";
    public static final String PASSWORD="Brian198672..";//"Brian198672..";
    //客户号
    public static final Integer CUSTOMER_ID=1103;

    //产品相关
    public static final boolean SAIHE_PRODUCT_SWITCH=true;
    //订单、备库相关
    public static final boolean SAIHE_ORDER_SWITCH=true;

    //B2C网站平台
    public static final Integer SOURCINBOX_ORDER_SOURCE_TYPE=0;
    //潘达默认仓库
    public static final Integer SOURCINBOX_DEFAULT_WAREHOURSE_ID=1;
    //潘达默认订单来源渠道
    public static final Integer SOURCINBOX_DEFAULT_ORDER_SOURCE_ID=1;

    /**
     * 测试账号 ==》 客户id
     */
    public static  final  Long DEFAULT_ACCOUNT= 1071212785430777858L;

    /**
     * 普通订单OrderCode标识
     */
    public static  final  String Order_Code= "Order_Code";


    /**
     * 批量订单OrderCode标识
     */
    public static  final  String wholesale_Order_Code= "wholesale_Order_Code";
}
