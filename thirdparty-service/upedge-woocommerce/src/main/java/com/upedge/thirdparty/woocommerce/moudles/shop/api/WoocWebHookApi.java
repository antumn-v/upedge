package com.upedge.thirdparty.woocommerce.moudles.shop.api;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.upedge.thirdparty.woocommerce.moudles.shop.entity.WoocWebhook;
import com.upedge.thirdparty.woocommerce.util.WoocommerceRequest;
import org.springframework.http.HttpMethod;

public class WoocWebHookApi {

//    private static Map<String,String> topics = new HashMap();
//    static {
//        topics.put("order.created","UPEDGE Order Create");
//        topics.put("order.updated","UPEDGE Order Updated");
//        topics.put("order.deleted","UPEDGE Order Deleted");
//        topics.put("product.updated","UPEDGE Product Updated");
//        topics.put("product.created","UPEDGE Product Created");
//        topics.put("product.deleted","UPEDGE Product Deleted");
//    }

//    public JSONObject batchCreateWebhook(String token,String delivery_url,String shop){
//        JSONObject jsonObject = new JSONObject();
//
//        topics.forEach(new BiConsumer<String, String>() {
//            @Override
//            public void accept(String topic, String name) {
//                WoocWebhook webhook = new WoocWebhook();
//                webhook.setName(name);
//                webhook.setTopic(topic);
//                webhook.setDelivery_url(delivery_url);
//
//                String url = "https://"+shop +"/wp-json/wc/v3/webhooks";
//
//                JSONObject json =  WoocommerceRequest.sendRequest(JSON.toJSON(webhook).toString(),token,url,null, HttpMethod.POST).getBody();
//                if(null != json){
//                    jsonObject.put(topic,json.getInteger("id"));
//                }else {
//                    json =  WoocommerceRequest.sendRequest(JSON.toJSON(webhook).toString(),token,url,null, HttpMethod.POST).getBody();
//                    if(null != json){
//                        jsonObject.put(topic,json.getInteger("id"));
//                    }
//                }
//            }
//        });
//        return null;
//    }

    public JSONObject createWebhook(WoocWebhook webhook,String token,String shop){
        String url = "https://"+shop +"/wp-json/wc/v3/webhooks";

        String s =  WoocommerceRequest.sendRequest(JSON.toJSON(webhook).toString(),token,url,null, HttpMethod.POST).getBody();
        if(null == s){
            s = WoocommerceRequest.sendRequest(JSON.toJSON(webhook).toString(),token,url,null, HttpMethod.POST).getBody();
        }
        return JSONObject.parseObject(s);
    }

}
