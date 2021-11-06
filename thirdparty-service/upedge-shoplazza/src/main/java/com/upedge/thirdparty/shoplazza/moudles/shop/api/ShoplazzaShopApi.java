package com.upedge.thirdparty.shoplazza.moudles.shop.api;

import com.alibaba.fastjson.JSONObject;
import com.upedge.thirdparty.shoplazza.config.ShoplazzaConfig;
import com.upedge.thirdparty.shoplazza.moudles.shop.entity.ShoplazzaWebhook;
import com.upedge.thirdparty.shoplazza.utils.ShoplazzaRequestUtils;
import org.springframework.http.HttpMethod;

import java.util.HashMap;
import java.util.Map;

public class ShoplazzaShopApi {


    public static JSONObject shoplazzaShopDetail(String shopName,String token){
        String url = "https://" + shopName + "/openapi/"+ ShoplazzaConfig.version +"/shop";

        JSONObject jsonObject = ShoplazzaRequestUtils.sendRequest(url,token,null, HttpMethod.GET,null);

        return jsonObject;
    }

    public static JSONObject shoplazzaCreateWebhook(String shop, String token, ShoplazzaWebhook webhook){
        String url = "https://" + shop + "/openapi/"+ ShoplazzaConfig.version +"/webhooks";
        Map<String,ShoplazzaWebhook> map = new HashMap<>();
        map.put("webhook",webhook);
        JSONObject jsonObject = ShoplazzaRequestUtils.sendRequest(url,token,null, HttpMethod.POST,map);
        return jsonObject;
    }
}
