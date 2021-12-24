package com.upedge.common.utils;

import com.alibaba.fastjson.JSON;
import okhttp3.*;
import org.springframework.http.HttpMethod;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class OkHttpRequest {

    public static String commonRequest(String url,HttpMethod method,String data){
        OkHttpClient client = new OkHttpClient().newBuilder().connectTimeout(60, TimeUnit.SECONDS)
                .build();
        MediaType mediaType = MediaType.parse("application/json");

        RequestBody body = null;
        if(null != data){
            body =  RequestBody.create(mediaType, data);
        }

        Request request = new Request.Builder()
                .url(url)
                .method(method.toString(), body)
                .build();
        Response response = null;
        try {
            response = client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        try {
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    public static String shopifyRequest(String url, String data, String token, HttpMethod method){
        OkHttpClient client = new OkHttpClient().newBuilder().connectTimeout(60, TimeUnit.SECONDS)
                .build();
        MediaType mediaType = MediaType.parse("application/json");

        RequestBody body = null;
        if(null != data){
           body =  RequestBody.create(mediaType, data);
        }

        Request request = new Request.Builder()
                .url(url)
                .method(method.toString(), body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", "Basic " + token)
                .build();
        Response response = null;
        try {
            response = client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        try {
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    public static String woocommerceRequest(Object data, String token, String url, String paramStr, HttpMethod method){
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("application/json");

        RequestBody body = null;
        if(null != data){
            body =  RequestBody.create(mediaType, JSON.toJSONString(data));
        }

        Request request = new Request.Builder()
                .url(url)
                .method(method.toString(), body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", "Basic " + token)
                .build();
        Response response = null;
        try {
            response = client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        try {
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
