package com.upedge.common.constant.key;

public class RedisKey {
    //待处理的woocommerce订单
    public static final String LIST_WOOCOMMERCE_ORDER_WEBHOOK = "webhook:woocommerce:order";
    //待处理的shoplazza订单
    public static final String LIST_SHOPLAZZA_ORDER_WEBHOOK = "webhook:shoplazza:order";
    //待处理的shopify订单
    public static final String LIST_SHOPIFY_ORDER_WEBHOOK = "webhook:shopify:order";
    //待统计的支付订单信息
    public static final String LIST_CUSTOMER_ORDER_DAILY_COUNT_UPDATE = "customer:order:daily:count:update";
    //待统计的退款订单信息
    public static final String LIST_CUSTOMER_ORDER_DAILY_REFUND_COUNT_UPDATE = "customer:order:daily:refund:count:update";
    //当前月包裹美元汇率
    public static final String STRING_PACKAGE_CURRENT_MONTH_USD_RATE = "package:current:month:usd:rate";
    //客户经理信息，拼接客户经理ID
    public static final String STRING_MANAGER_INFO = "manager:info:";
    //客户信息，拼接客户ID
    public static final String STRING_CUSTOMER_INFO = "customer:info:";
    //账户佣金提现加锁
    public static final String STRING_ACCOUNT_COMMISSION_WITHDRAWAL = "account:commission:withdrawal:";
    //店铺订单生成订单加锁
    public static final String STRING_STORE_ORDER_CREATE = "storeOrder:create:order:";
    //shopify店铺locations
    public static final String STRING_STORE_SHOPIFY_LOCATIONS = "store:locations:";
    //用户获取最近一次的没有接收过的notice
    public static final String STRING_USER_RECENT_NOTICE = "user:recent:notice:";
    //订单备注
    public static final String STRING_ORDER_NOTE = "order:note:";
    //邮箱验证码
    public static final String STRING_EMAIL_SEND_CODE = "email:send:code:";
    //店铺订单信息完善
    public static final String STRING_STORE_ORDER_COMPLETE = "store:order:complete:";
    //店铺平台信息更新
    public static final String STRING_STORE_PALT_ORDER_UPDATE = "store:plat:order:update:";
    //货币汇率
    public static final String HASH_CURRENCY_RATE = "currency:rate:";
    //国家vat规则
    public static final String STRING_AREA_VAT_RULE = "vat:rule:area:";
    //店铺信息
    public static final String STRING_STORE = "store:";
    //店铺产品信息更新
    public static final String STRING_STORE_PLAT_PRODUCT = "store:plat:product:";
    //支付中的订单
    public static final String STRING_ORDER_ID_PENDING = "order:id:pending:";
    //账户支付加锁
    public static final String KEY_USER_ACCOUNT_PAY = "user:account:pay:";
    //订单根据交易ID支付加锁
    public static final String STRING_ORDER_PAY = "order:pay:paymentId:";
    //paypal支付信息
    public static final String HASH_PAYPAL_TOKEN = "paypal:token:";
    //运输方式
    public static final String HASH_SHIP_METHOD = "ship:method:";
    //SaiheTransport
    public static final String STRING_SAIHE_TRANSPORT_IDKEY = "saihe_transport:idkey:";
    //国家信息
    public static final String HASH_AREA = "area:name:";
    //客户ID与客户经理ID关联
    public static final String HASH_CUSTOMER_MANAGER_RELATE = "customer:manager:relate";
    //登录名和CustomerId
    public static final String HASH_LOGINNAME_CUSTOMERID = "loginname:customerId";
    //运输方式信息  map
    public static final String SHIPPING_METHOD="shipping:method";
    //运输模板与运输方式关联信息
    public static final String SHIPPING_METHODS="shipping:methods:";
    // 运输模板信息
    public static final String SHIPPING_TEMPLATE="shipping:template:";
    //客户备库建议
    public static final String ZSET_CUSTOMER_STOCK_ADVICE = "customer:stock:advice:";
    //速卖通验证信息
    public static final String AE_TOKEN="aeToken";
    /**
     * 赛盒运输方式
     */
    public static final String SAIHE_TRANSPORT_REDIS = "SAIHE_TRANSPORT_REDIS";
    /**
     * 普通订单产品未导入赛盒的产品集合hash 导入产品时移除
     */
    public static final String HASH_BAD_NORMAL_ORDER_PRODUCT="badNormalOrderProduct";
    /**
     * 批发订单产品未导入赛盒的产品集合hash 导入产品时移除
     */
    public static final String HASH_BAD_WHOLESALE_ORDER_PRODUCT="badWholesaleOrderProduct";
    //Wooc插件回传 未配置运输方式缓存
    public static final String WOOC_SHIP_METHOD="wookShipMethod:";
    //回传物流 店铺信息缓存
    public static final String HASH_TRACK_STORE="trackStore";

    /**
     * 国家表数据
     */
    public static final String AREA = "area";

    public static final String HASH_COUNTRY_AREA_ID = "country:area:id:";

    /**
     * redis锁    创建价格申请
     */
    public static final String STRING_CREAT_PRODUCT_PRICE_APPLY = "creatProductPriceApply";

    /**
     * 审核产品价格调整申请 redis锁
     */
    public static final String STRING_AUTO_PRODUCT_PRICE_APPLY = "autoProductPriceApply";

    /**
     * redis锁    提现申请通过
     */
    public static final String WITHDRAWAL_APPLICATION_PASSED = "Withdrawal application passed";

    /**
     * redis锁    客户上传sourcing商品
     */
    public static final String UPLOAD_SOURCING_PRODUCT = "upload_sourcing_product";
}
