package com.upedge.ums.async;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.upedge.common.feign.OmsFeignClient;
import com.upedge.common.feign.PmsFeignClient;
import com.upedge.common.model.store.StoreVo;
import com.upedge.common.model.store.request.StoreApiRequest;
import com.upedge.common.utils.ListUtils;
import com.upedge.thirdparty.shopify.moudles.order.controller.ShopifyOrderApi;
import com.upedge.thirdparty.shopify.moudles.product.controller.ShopifyProductApi;
import com.upedge.thirdparty.shopify.moudles.shop.controller.ShopifyWebhookApi;
import com.upedge.thirdparty.shopify.moudles.shop.entity.ShopifyWebhook;
import com.upedge.thirdparty.shoplazza.moudles.order.api.ShoplazzaOrderApi;
import com.upedge.thirdparty.shoplazza.moudles.product.api.ShoplazzaProductApi;
import com.upedge.thirdparty.shoplazza.moudles.shop.api.ShoplazzaShopApi;
import com.upedge.thirdparty.shoplazza.moudles.shop.entity.ShoplazzaWebhook;
import com.upedge.thirdparty.woocommerce.moudles.order.api.WoocommerceOrderApi;
import com.upedge.thirdparty.woocommerce.moudles.product.controller.WoocommerceProductApi;
import com.upedge.thirdparty.woocommerce.moudles.shop.entity.WoocWebhook;
import com.upedge.thirdparty.woocommerce.util.WoocommerceRequest;
import com.upedge.ums.modules.store.config.StoreWebhook;
import com.upedge.ums.modules.store.entity.Store;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.function.BiConsumer;

@Slf4j
@Component
public class StoreAsync {

    @Autowired
    OmsFeignClient omsFeignClient;

    @Autowired
    PmsFeignClient pmsFeignClient;

    @Autowired
    RedisTemplate redisTemplate;

    //获取店铺数据
    @Async
    public void getStoreData(Store store) {
//        createStoreWebhook(store);
        switch (store.getStoreType()) {
            case 0:
                getShopifyStoreData(store);
                break;
            case 1:
                getWoocommerceStoreData(store);
                break;
            case 2:
                getShoplazzaStoreData(store);
            default:
                break;
        }
    }

    public void getStoreProductAndOrder(Store store) {
        switch (store.getStoreType()) {
            case 0:
                getShopifyStoreOrders(store);
                break;
            case 1:
                getWoocommerceStoreOrders(store);
                break;
            case 2:
                getWoocommerceStoreOrders(store);
            default:
                break;
        }
    }
    //店铺创建webhook
    public void createStoreWebhook(Store store) {
        String shop = store.getStoreName();
        String token = store.getApiToken();
        switch (store.getStoreType()) {
            case 0:
                createShopifyWebhook(token, shop);
                break;
            case 1:
                createWoocWebhook(token, shop);
                break;
            case 2:
                createShoplazzaWebhook(token,shop);
            default:
                break;
        }
    }

    public void createWoocWebhook(String token, String shop) {
        StoreWebhook.woocTopics.forEach(new BiConsumer<String, String>() {
            @Override
            public void accept(String topic, String name) {
                WoocWebhook webhook = new WoocWebhook();
                webhook.setName(name);
                webhook.setTopic(topic);
                if (topic.contains("order")) {
                    webhook.setDelivery_url(StoreWebhook.W_ORDER_URL);
                } else if (topic.contains("product")) {
                    webhook.setDelivery_url(StoreWebhook.W_PRODUCT_URL);
                }

                String url = "https://" + shop + "/wp-json/wc/v3/webhooks";

                String s = WoocommerceRequest.sendRequest(JSON.toJSON(webhook).toString(), token, url, null, HttpMethod.POST).getBody();
                if (null != s) {
                    webhook = JSONObject.parseObject(s).toJavaObject(WoocWebhook.class);
                } else {
                    s = WoocommerceRequest.sendRequest(JSON.toJSON(webhook).toString(), token, url, null, HttpMethod.POST).getBody();
                    if (null != s) {
                        webhook = JSONObject.parseObject(s).toJavaObject(WoocWebhook.class);
                    }
                }
            }
        });
    }

    public void createShopifyWebhook(String token, String shop) {
        StoreWebhook.shopifyTopics.forEach(topic -> {
            ShopifyWebhook webhook = new ShopifyWebhook();
            webhook.setTopic(topic);
            if (topic.contains("order") || topic.contains("refund")) {
                webhook.setAddress(StoreWebhook.S_ORDER_URL);
            } else if (topic.contains("product")) {
                webhook.setAddress(StoreWebhook.S_PRODUCT_URL);
            } else {
                webhook.setAddress(StoreWebhook.S_SHOP_URL);
            }

            JSONObject jsonObject = ShopifyWebhookApi.createShopifyWebHook(webhook, token, shop);
            if (null == jsonObject) {
                ShopifyWebhookApi.createShopifyWebHook(webhook, token, shop);
            }
        });
    }

    public void createShoplazzaWebhook(String token, String shop) {
        StoreWebhook.shoplazzaTopics.forEach(topic -> {
            ShoplazzaWebhook webhook = new ShoplazzaWebhook();
            webhook.setTopic(topic);
            webhook.setAddress(StoreWebhook.SHOPLAZZA_WEBHOOK_URL);

            JSONObject jsonObject = ShoplazzaShopApi.shoplazzaCreateWebhook(shop, token, webhook);
            if (null == jsonObject) {
                ShoplazzaShopApi.shoplazzaCreateWebhook(shop, token, webhook);
            }
        });
    }

    //获取shopify店铺订单产品
    public void getShopifyStoreData(Store store) {
        getShopifyStoreProducts(store);
        getShopifyStoreOrders(store);
    }
    //店匠店铺
    public void getShoplazzaStoreData(Store store) {
        getShoplazzaStoreProducts(store);
        getShoplazzaStoreOrders(store);
    }
    //woocommerce店铺数据
    public void getWoocommerceStoreData(Store store){
        getWoocommerceStoreProducts(store);
        getWoocommerceStoreOrders(store);
    }

    public void getWoocommerceStoreProducts(Store store){
        String shop = store.getStoreName();
        String token = store.getApiToken();
        JSONArray array = WoocommerceProductApi.getWoocommerceProductCount(token,shop,null);
        StoreVo storeVo = new StoreVo();
        BeanUtils.copyProperties(store,storeVo);
        StoreApiRequest storeApiRequest = new StoreApiRequest();
        storeApiRequest.setStoreVo(storeVo);
        for (int i = 0; i < array.size(); i++) {
            JSONObject report = array.getJSONObject(i);
            Integer counts = report.getInteger("total");
            if (counts == 0){
                continue;
            }
            Integer pageNum = 1;
            if(counts % 10 == 0){
                pageNum = counts / 10;
            }else if(counts / 10 == 0){
                pageNum = 1;
            }else if(counts % 10 > 0) {
                pageNum = counts / 10 + 1;
            }
            for (int j = 1; j <= pageNum; j++) {
                String param = "page="+j + "&type=" + report.getString("slug");
                JSONArray productArray = WoocommerceProductApi.woocommerceProducts(token,shop,param);

                for (int k = 0; k < productArray.size(); k++) {
                    storeApiRequest.setJsonObject(productArray.getJSONObject(k));
                    pmsFeignClient.updateWoocommerceProduct(storeApiRequest);
                }
            }
        }
    }

    public void getWoocommerceStoreOrders(Store store){
        String shop = store.getStoreName();
        String token = store.getApiToken();
        JSONArray array = WoocommerceOrderApi.getWoocommerceOrderCount(token,shop,null);
        StoreVo storeVo = new StoreVo();
        BeanUtils.copyProperties(store,storeVo);
        StoreApiRequest storeApiRequest = new StoreApiRequest();
        storeApiRequest.setStoreVo(storeVo);
        Integer counts = 0;
        for (int i = 0; i < array.size(); i++) {
            JSONObject report = array.getJSONObject(i);
            if(report.getString("slug").equals("processing")){
                counts = report.getInteger("total");
                break;
            }
        }
        Integer pageNum = 1;
        if(counts % 10 == 0){
            pageNum = counts / 10;
        }else if(counts / 10 == 0){
            pageNum = 1;
        }else if(counts % 10 > 0) {
            pageNum = counts / 10 + 1;
        }
        for (int j = 1; j <= pageNum; j++) {
            String param = "page="+j + "&status=processing";
            JSONArray orderArray = WoocommerceOrderApi.getWoocommerceOrders(token,shop,param);
            for (int k = 0; k < orderArray.size(); k++) {
                storeApiRequest.setJsonObject(orderArray.getJSONObject(k));
                omsFeignClient.woocommerceOrderUpdate(storeApiRequest);
            }
        }
    }

    public void getShopifyStoreOrders(Store store) {
        String shop = store.getStoreName();

        String token = store.getApiToken();

        StoreVo storeVo = new StoreVo();
        BeanUtils.copyProperties(store, storeVo);
        JSONObject jsonObject = ShopifyOrderApi.getStoreOrder(shop, token, null, null, "financial_status=paid&limit=250");
        while (jsonObject != null && jsonObject.containsKey("orders")) {
            JSONArray array = jsonObject.getJSONArray("orders");
            log.error("店铺获取订单：店铺:{},订单长度：{}",store.getStoreName(),array.size());
            for (int i = 0; i < array.size(); i++) {
                JSONObject order = array.getJSONObject(i);
                StoreApiRequest storeApiRequest = new StoreApiRequest();
                storeApiRequest.setId(order.getString("id"));
                storeApiRequest.setStoreVo(storeVo);
                storeApiRequest.setJsonObject(order);
//                redisTemplate.opsForList().leftPush(RedisKey.LIST_SHOPIFY_ORDER_WEBHOOK, storeApiRequest);
                omsFeignClient.updateShopifyOrder(storeApiRequest);
            }
            String nextUrl = null;
            if (jsonObject.containsKey("nextUrl")) {
                nextUrl = jsonObject.getString("nextUrl");
            }
            if (StringUtils.isNotBlank(nextUrl)) {
                jsonObject = ShopifyOrderApi.getStoreOrder(shop, token, null, nextUrl, null);
            } else {
                jsonObject = null;
            }

        }
    }

    public void getShopifyStoreProducts(Store store) {
        String shop = store.getStoreName();

        String token = store.getApiToken();

        StoreVo storeVo = new StoreVo();
        BeanUtils.copyProperties(store, storeVo);
        JSONObject jsonObject = ShopifyProductApi.getStoreProducts(shop, token, null, null, null);
        while (jsonObject != null && jsonObject.containsKey("products")) {
            JSONArray array = jsonObject.getJSONArray("products");
            for (int i = 0; i < array.size(); i++) {
                JSONObject product = array.getJSONObject(i);
                StoreApiRequest storeApiRequest = new StoreApiRequest();
                storeApiRequest.setId(product.getString("id"));
                storeApiRequest.setStoreVo(storeVo);
                storeApiRequest.setJsonObject(product);
                pmsFeignClient.updateShopifyProduct(storeApiRequest);
            }
            String nextUrl = null;
            if (jsonObject.containsKey("nextUrl")) {
                nextUrl = jsonObject.getString("nextUrl");
            }
            if (StringUtils.isNotBlank(nextUrl)) {
                jsonObject = ShopifyProductApi.getStoreProducts(shop, token, null, nextUrl, null);
            } else {
                jsonObject = null;
            }
        }
    }

    public void getShoplazzaStoreProducts(Store store) {
        String shop = store.getStoreName();
        String token = store.getApiToken();
        JSONObject jsonObject = ShoplazzaProductApi.getShoplazzaProductCount(shop, token, null, null);
        if (jsonObject == null) {
            return;
        }
        Integer count = jsonObject.getInteger("count");
        if (count == null || count == 0){
            return;
        }
        Integer size = 0;
        if (count % 250 == 0) {
            size = count % 250;
        } else {
            size = (count / 250) + 1;
        }
        StoreVo storeVo = new StoreVo();
        BeanUtils.copyProperties(store, storeVo);
        StoreApiRequest storeApiRequest = new StoreApiRequest();
        storeApiRequest.setStoreVo(storeVo);
        for (int i = 1; i <= size; i++) {
            jsonObject = ShoplazzaProductApi.getShoplazzaProduct(shop, token, "page=" + i + "&limit=250", null);
            if (jsonObject != null && jsonObject.containsKey("products")) {
                JSONArray array = jsonObject.getJSONArray("products");
                if (ListUtils.isNotEmpty(array)) {
                    for (int j = 0; j < array.size(); j++) {
                        storeApiRequest.setJsonObject(array.getJSONObject(j));
                        pmsFeignClient.updateShoplazzaProduct(storeApiRequest);
                    }
                }
            }
        }

    }

    public void getShoplazzaStoreOrders(Store store) {
        String shop = store.getStoreName();
        String token = store.getApiToken();
        String param = "financial_status=paid";
        JSONObject jsonObject = ShoplazzaOrderApi.getShoplazzaOrdersCount(shop, token, param, null);
        if (jsonObject == null) {
            return;
        }
        Integer count = jsonObject.getInteger("count");
        if (count == null || count == 0){
            return;
        }
        Integer size = 0;
        if (count % 250 == 0) {
            size = count % 250;
        } else {
            size = (count / 250) + 1;
        }
        StoreVo storeVo = new StoreVo();
        BeanUtils.copyProperties(store, storeVo);
        StoreApiRequest storeApiRequest = new StoreApiRequest();
        storeApiRequest.setStoreVo(storeVo);
        for (int i = 1; i <= size; i++) {
            jsonObject = ShoplazzaOrderApi.getShoplazzaOrders(shop, token, param + "&page=" + i + "&limit=250", null);
            if (jsonObject != null && jsonObject.containsKey("orders")) {
                JSONArray array = jsonObject.getJSONArray("orders");
                if (ListUtils.isNotEmpty(array)) {
                    for (int j = 0; j < array.size(); j++) {
                        storeApiRequest.setJsonObject(array.getJSONObject(j));
                        omsFeignClient.updateShoplazzaOrder(storeApiRequest);
                    }
                }
            }
        }

    }
}
