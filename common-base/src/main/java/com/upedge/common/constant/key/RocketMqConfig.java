package com.upedge.common.constant.key;

public class RocketMqConfig {

    public final static String NAME_SERVER = "localhost:9876";
//    public final static String NAME_SERVER = "172.23.127.10:9876";

    /**
     * 保存订单交易信息
     */
    public final static String TOPIC_SAVE_ORDER_TRANSACTION = "save_order_transaction";
    /**
     * paypal支付验证
     */
    public final static String TOPIC_PAYPAL_VERIFICATION = "paypal_verification";
    /**
     * 客户产品报价修改
     */
    public final static String TOPIC_CUSTOMER_PRODUCT_QUOTE_UPDATE = "customer_product_quote_update";
    /**
     * 变体信息修改
     */
    public final static String TOPIC_VARIANT_UPDATE = "variant_update";
    /**
     * 确认关联
     */
    public final static String TOPIC_RELATE_CONFIRM = "relate_confirm";
    /**
     * 订单上传赛盒
     */
    public final static String TOPIC_ORDER_UPLOAD_SAIHE = "order_upload_saihe";

    public final static String TOPIC_ORDER_CHECK_STOCK = "order_check_stock";
    /**
     * 修改客户经理
     */
    public final static String TOPIC_CHANGE_OMS_MANAGER_CODE = "topic_customer_change_manager_code";
    /**
     * Ship Unit信息修改
     */
    public final static String TOPIC_SHIP_UNIT_UPDATE = "ship_unit_update";
    /**
     * 客户IOSS信息修改
     */
    public final static String TOPIC_CUSTOMER_IOSS_UPDATE = "customer_ioss_update";
    /**
     * 订单物流回传店铺
     */
    public final static String TOPIC_ORDER_FULFILLMENT = "order_fulfillment";
    //店铺订单更新webhook
    public final static String TOPIC_STORE_ORDER_UPDATE_WEBHOOK = "store_order_update_webhook";
    //店铺产品更新webhook
    public final static String TOPIC_STORE_PRODUCT_UPDATE_WEBHOOK = "store_product_update_webhook";
    //获取店铺数据
    public final static String TOPIC_GET_STORE_DATA = "get_store_data";

}
