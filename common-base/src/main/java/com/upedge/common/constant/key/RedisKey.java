package com.upedge.common.constant.key;

public class RedisKey {

    public static final String STRING_CUSTOMER_IOSS = "customer:ioss:";

    public static final String STRING_ALI1688_API = "1688_api_token";

    public static final String STRING_METHOD_COUNTRY_UNIT_LIST = "method:country:unit:list";

    public static final String STRING_TRACKING_COMPANY = "tracking:company:list";

    public static final String STRING_WAREHOUSE = "warehouse:code:";

    public static final String LIST_CUSTOMER_NORMAL_ORDER_PAYMENT_ID = "normal:order：payment:id:list:customer:";

    public static final String HASH_CUSTOMER_SETTING = "customer:setting:";

    //包含拆分子体的店铺变体
    public static final String HASH_STORE_SPLIT_VARIANT = "storeVariant:split:hash";

    //报价中的店铺变体
    public static final String LIST_QUOTING_STORE_VARIANT = "storeVariant:quoting:list";

    //待统计的退款订单信息
    public static final String LIST_CUSTOMER_ORDER_DAILY_REFUND_COUNT_UPDATE = "customer:order:daily:refund:count:update";
    //客户登陆提示
    public static final String HASH_CUSTOMER_LOGIN_NOTICE = "customer:login:notice";
    //当前月包裹美元汇率
    public static final String STRING_PACKAGE_CURRENT_MONTH_USD_RATE = "package:current:month:usd:rate";
    //客户经理信息，拼接客户经理ID
    public static final String STRING_MANAGER_INFO = "manager:info:";
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
    public static final String STRING_STORE = "store:vo:";
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
    //需要重置密码的用户
    public static final String HASH_USER_NEED_RESET_PASSWTORD = "user:need:reset:password";
    //SaiheTransport
    public static final String STRING_SAIHE_TRANSPORT_IDKEY = "saihe_transport:idkey:";
    //客户ID与客户经理ID关联
    public static final String HASH_CUSTOMER_MANAGER_RELATE = "customer:manager:relate";
    //运输方式信息  map
    public static final String SHIPPING_METHOD="shipping:method";
    //运输模板与运输方式关联信息
    public static final String SHIPPING_TEMPLATED_METHODS="shipping:template:methods:";
    // 运输模板信息
    public static final String SHIPPING_TEMPLATE="shipping:template:vo:hash";
    //客户备库建议
    public static final String ZSET_CUSTOMER_STOCK_ADVICE = "customer:stock:advice:";
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
    public static final  String AREA = "area";

    public static final String HASH_COUNTRY_AREA_ID = "country:area:id:";

    /**
     * redis锁    提现申请通过
     */
    public static final  String WITHDRAWAL_APPLICATION_PASSED = "withdrawal:apply:passed";
}
