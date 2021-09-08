package com.upedge.thirdparty.shopify.moudles.shop.controller;

import com.alibaba.fastjson.JSONObject;

import com.upedge.thirdparty.shopify.config.Shopify;
import com.upedge.thirdparty.shopify.entity.Response;
import com.upedge.thirdparty.shopify.entity.shop.Shop;
import com.upedge.thirdparty.shopify.moudles.shop.entity.ShopifyGetTokenParam;
import com.upedge.thirdparty.shopify.moudles.shop.entity.ShopifyLocation;
import com.upedge.thirdparty.shopify.utils.RequestUtils;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;


public class ShopifyShopApi {


    public static JSONObject getToken(ShopifyGetTokenParam param){
        Response response = new Response();
        String accessToken = null;
        String url = "client_id=" + param.getApiKey() + "&client_secret=" + param.getApiSecret() + "&code=" + param.getCode();
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL("https://" + param.getShopName() + "/admin/oauth/access_token");
            URLConnection conn = realUrl.openConnection();
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            out = new PrintWriter(conn.getOutputStream());
            out.print(url);
            out.flush();
            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
            accessToken = result.substring(result.indexOf(":") + 1, result.indexOf(",")).replaceAll("\"", "");
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！" + e);

            return response.failed("shopify request error");
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        response.addData("token",accessToken);
        return response.success();
    }


    public static Shop getShopDetail(String shop,String token){
        String url = Shopify.getShopifyApi(shop,"shop");
        ResponseEntity<JSONObject> entity =  RequestUtils.sendRequest(url,token,null, HttpMethod.GET,null);
        if(entity == null){
            return null;
        }

        return entity.getBody().getJSONObject("shop").toJavaObject(Shop.class);
    }

    public static List<ShopifyLocation> getShopifyLocations(String shop,String token){
        String url = Shopify.getShopifyApi(shop,"locations");
        ResponseEntity<JSONObject> entity =  RequestUtils.sendRequest(url,token,null, HttpMethod.GET,null);
        if(entity == null){
            return null;
        }
        JSONObject jsonObject = entity.getBody();
        if(jsonObject != null && jsonObject.containsKey("locations")){
            List<ShopifyLocation> locations = jsonObject.getJSONArray("locations").toJavaList(ShopifyLocation.class);
            return locations;
        }
        return null;
    }

    public static void main(String[] args) {

        Shop shop = getShopDetail("pandagx.myshopify.com","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");
        System.out.println(shop);
    }



}
