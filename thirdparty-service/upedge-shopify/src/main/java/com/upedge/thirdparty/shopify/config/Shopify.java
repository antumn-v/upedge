package com.upedge.thirdparty.shopify.config;


public class Shopify {

    public static String version = "2021-07";

    static String scope = "read_products,write_products,read_orders,write_orders,read_fulfillments,write_fulfillments,write_resource_feedbacks,read_inventory,write_inventory,read_locations," +
            "read_shipping,write_shipping,read_translations,write_translations,read_shopify_payments_payouts,read_shopify_payments_disputes,read_checkouts,write_checkouts," +
            "read_assigned_fulfillment_orders,write_assigned_fulfillment_orders,read_analytics,read_price_rules,write_price_rules," +
            "read_discounts,write_discounts,read_marketing_events,write_marketing_events,read_resource_feedbacks,write_resource_feedbacks," +
            "read_locales,write_locales,write_locales,read_product_listings";


    public static String getShopifyApi(String shop, String api){
        String url = "https://" + shop + ".myshopify.com/admin/api/"+version+"/"+ api + ".json";
        return url;
    }
}
