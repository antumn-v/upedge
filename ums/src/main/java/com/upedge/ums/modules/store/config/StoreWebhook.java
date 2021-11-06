package com.upedge.ums.modules.store.config;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class StoreWebhook {

    public static Map<String, String> woocTopics = new HashMap();

    public static Set<String> shopifyTopics=new HashSet();

    public static Set<String> shoplazzaTopics=new HashSet();

    public static String W_PRODUCT_URL = "https://app.sourcinbox.com/webHook/woocommerce";

    public static String W_ORDER_URL = "https://app.sourcinbox.com/webHook/woocommerce";

    public static String S_PRODUCT_URL = "https://app.sourcinbox.com/webHook/webhook"
            ;
    public static String S_ORDER_URL = "https://app.sourcinbox.com/webHook/webhook";

    public static String S_SHOP_URL = "https://app.sourcinbox.com/webHook/webhook";

    public static String SHOPLAZZA_WEBHOOK_URL = "https://app.sourcinbox.com/webHook/shoplazza";
    static {
        woocTopics.put("order.created","SourcinBox Order Create");
        woocTopics.put("order.updated","SourcinBox Order Updated");
        woocTopics.put("order.deleted","SourcinBox Order Deleted");
        woocTopics.put("product.updated","SourcinBox Product Updated");
        woocTopics.put("product.created","SourcinBox Product Created");
        woocTopics.put("product.deleted","SourcinBox Product Deleted");

        shopifyTopics.add("app/uninstalled");
        shopifyTopics.add("orders/cancelled");
        shopifyTopics.add("orders/create");
        shopifyTopics.add("orders/delete");
        shopifyTopics.add("orders/fulfilled");
        shopifyTopics.add("orders/paid");
        shopifyTopics.add("orders/partially_fulfilled");
        shopifyTopics.add("orders/updated");
        shopifyTopics.add("products/create");
        shopifyTopics.add("products/update");
        shopifyTopics.add("products/delete");
        shopifyTopics.add("refunds/create");
        shopifyTopics.add("order_transactions/create");


        shoplazzaTopics.add("app/uninstalled");
        shoplazzaTopics.add("app/expired");
        shoplazzaTopics.add("orders/cancelled");
        shoplazzaTopics.add("orders/create");
        shoplazzaTopics.add("orders/delete");
        shoplazzaTopics.add("orders/fulfilled");
        shoplazzaTopics.add("orders/paid");
        shoplazzaTopics.add("orders/partially_fulfilled");
        shoplazzaTopics.add("orders/updated");
        shoplazzaTopics.add("orders/refunded");
        shoplazzaTopics.add("products/create");
        shoplazzaTopics.add("products/update");
        shoplazzaTopics.add("products/delete");
    }

}
