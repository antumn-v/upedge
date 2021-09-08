package com.upedge.thirdparty.shopify.moudles.shop.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.panda.common.utils.OkHttpRequest;
import com.upedge.thirdparty.shopify.config.Shopify;
import com.upedge.thirdparty.shopify.moudles.shop.entity.ShopifyWebhook;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class ShopifyWebhookApi {


    public static JSONObject createShopifyWebHook(ShopifyWebhook webhook,String token,String shop){
        String url = Shopify.getShopifyApi(shop,"webhooks");

        Map<String,ShopifyWebhook> map = new HashMap<>();
        map.put("webhook",webhook);
        String s =  OkHttpRequest.shopifyRequest(url,JSON.toJSON(map).toString(),token,HttpMethod.POST);
        return JSONObject.parseObject(s);
    }

    public static JSONObject listStoreWebhook(String token,String shop){
        String url = Shopify.getShopifyApi(shop,"webhooks");


        String s =  OkHttpRequest.shopifyRequest(url,null,token,HttpMethod.GET);
        return JSONObject.parseObject(s);
    }

    public static JSONObject updateWebhook(ShopifyWebhook webhook,Long id,String token,String shop){
        String url = Shopify.getShopifyApi(shop,"webhooks/" + id);
        Map<String,ShopifyWebhook> map = new HashMap<>();
        map.put("webhook",webhook);

        String s =  OkHttpRequest.shopifyRequest(url,JSON.toJSON(map).toString(),token,HttpMethod.PUT);
        return JSONObject.parseObject(s);
    }

    public static JSONObject deleteWebhook(Long id,String token,String shop){
        String url = Shopify.getShopifyApi(shop,"webhooks/" + id);

        String s =  OkHttpRequest.shopifyRequest(url,null,token,HttpMethod.DELETE);
        return JSONObject.parseObject(s);
    }
}
