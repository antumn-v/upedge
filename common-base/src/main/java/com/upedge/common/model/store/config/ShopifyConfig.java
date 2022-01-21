package com.upedge.common.model.store.config;

public class ShopifyConfig {



    public static final String api_key = "d05274a065a8b4ebd2342d17eb28d768";//退休
    public static final String api_select_key = "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";//退休
    public static final String redirect_url = "https://app.upedge.cn/storeblank";
//    public static final String redirect_url = "http://guoxing.gz2vip.idcfengye.com/storeblank";


    public static String scope = "read_products,write_products,read_orders,write_orders,read_fulfillments,write_fulfillments,write_resource_feedbacks,read_inventory,write_inventory,read_locations," +
            "read_shipping,write_shipping,read_translations,write_translations,read_shopify_payments_payouts,read_shopify_payments_disputes,read_checkouts,write_checkouts," +
            "read_assigned_fulfillment_orders,write_assigned_fulfillment_orders,read_analytics,read_price_rules,write_price_rules," +
            "read_discounts,write_discounts,read_marketing_events,write_marketing_events,read_resource_feedbacks,write_resource_feedbacks," +
            "read_locales,write_locales,write_locales,read_product_listings,read_all_orders";
}
