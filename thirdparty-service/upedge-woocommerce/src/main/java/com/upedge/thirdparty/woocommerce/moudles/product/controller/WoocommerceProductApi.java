package com.upedge.thirdparty.woocommerce.moudles.product.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import com.upedge.common.utils.OkHttpRequest;
import com.upedge.thirdparty.woocommerce.util.WoocommerceRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


public class WoocommerceProductApi {


    public static JSONArray woocommerceProducts(String token, String shopName,String param){
        String url = "https://"+shopName+"/wp-json/wc/v3/products" ;

        String data =
                OkHttpRequest.woocommerceRequest(null,token,url,param,HttpMethod.GET);

        if(StringUtils.isNotBlank(data)){
            return JSONArray.parseArray(data);
        }

        return null;
    }


    public static JSONArray getWoocommerceProductCount(String token, String shopName,String param){
        String url = "https://"+shopName+"/wp-json/wc/v3/reports/products/totals" ;

        String data =
                OkHttpRequest.woocommerceRequest(null,token,url,param,HttpMethod.GET);

        if(StringUtils.isNotBlank(data)){
            return JSONArray.parseArray(data);
        }

        return null;
    }

    public static JSONObject getProduct(String token,String shopName,String productId){
        String url = "https://"+shopName+"/wp-json/wc/v3/products/" + productId;

        String data =
                OkHttpRequest.woocommerceRequest(null,token,url,null, HttpMethod.GET);

        if(StringUtils.isNotBlank(data)){
            return JSONObject.parseObject(data);
        }
        return null;
    }

    public static JSONObject postProduct(String body,String token,String shopName){

        String url = "https://"+shopName+"/wp-json/wc/v3/products";

        String data =
                OkHttpRequest.woocommerceRequest(body,token,url,null, HttpMethod.POST);

        if(StringUtils.isNotBlank(data)){
            return JSONObject.parseObject(data);
        }
        return null;
    }

    public static JSONObject batchCreateVariants(JSONObject jsonObject,String token,String shopName,String productId){
        String url = "https://"+shopName+"/wp-json/wc/v3/products/" + productId + "/variations/batch";

        String data =
                OkHttpRequest.woocommerceRequest(jsonObject.toJSONString(),token,url,null, HttpMethod.POST);

        if(StringUtils.isNotBlank(data)){
            return JSONObject.parseObject(data);
        }
        return null;
    }

    public static JSONArray getProductAllVariants(String token,String shopName,String productId){
        String url = "https://"+shopName+"/wp-json/wc/v3/products/" + productId + "/variations?per_page=100";

        String data =
                OkHttpRequest.woocommerceRequest(null,token,url,null, HttpMethod.GET);

        if(data != null){
            return JSONArray.parseArray(data);
        }
        return null;
    }


    public static JSONObject deleteProduct(String token,String shopName,String productId){
        String url = "https://"+shopName+"/wp-json/wc/v3/products/" + productId;

        ResponseEntity<String> entity =
                WoocommerceRequest.sendRequest(null,token,url,null, HttpMethod.DELETE);

        if(entity.getStatusCode() == HttpStatus.OK){
            return JSONObject.parseObject(entity.getBody());
        }
        return null;
    }
}
