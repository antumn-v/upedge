package com.upedge.thirdparty.shoplazza.moudles.product.api;

import com.alibaba.fastjson.JSONObject;
import com.upedge.thirdparty.shoplazza.utils.ShoplazzaRequestUtils;
import org.springframework.http.HttpMethod;

public class ShoplazzaProductApi {


    public static JSONObject getShoplazzaProduct(String shop,String token,String param,String body){
        String url = "https://" + shop + "/openapi/2020-01/products";
        JSONObject jsonObject = ShoplazzaRequestUtils.sendRequest(url,token,param, HttpMethod.GET, body);
        return jsonObject;
    }


    public static JSONObject getShoplazzaProductCount(String shop,String token,String param,String body){
        String url = "https://" + shop + "/openapi/2020-01/products/count";
        JSONObject jsonObject = ShoplazzaRequestUtils.sendRequest(url,token,param, HttpMethod.GET, body);
        return jsonObject;
    }
}
