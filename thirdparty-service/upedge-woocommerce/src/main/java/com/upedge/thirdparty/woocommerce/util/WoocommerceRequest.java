package com.upedge.thirdparty.woocommerce.util;

import com.alibaba.fastjson.JSONObject;

import com.upedge.common.utils.OkHttpRequest;
import okhttp3.*;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.util.Base64;
import java.util.concurrent.TimeUnit;

/**
 * @author 海桐
 */
public class WoocommerceRequest {

    public static ResponseEntity<String> sendRequest( String body,String base64UserMsg,String url,String paramStr,HttpMethod method) {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(10 * 1000);
        factory.setReadTimeout(2 * 60 * 1000);

//        String base64UserMsg = Base64.getEncoder().encodeToString(authmsg.getBytes());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(org.springframework.http.MediaType.APPLICATION_JSON);
        headers.add("Authorization", "Basic " + base64UserMsg);

        if(body != null){
            body = body.toString();
        }
        HttpEntity<Object> entity = new HttpEntity<>(body, headers);
        RestTemplate restTemplate = new RestTemplate(factory);
        //String url = "https://"+ storeUrl + "/orders.json";
        if (null != paramStr) {
            url = url + "?" + paramStr;
        }

        ResponseEntity<String> response = null;
        try {
            response = restTemplate.exchange(url, method, entity, String.class);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }



    public static ResponseBody putRequest(String object, String key, String secret, String url){
        String authmsg = key + ":" + secret;
        String base64UserMsg = Base64.getEncoder().encodeToString(authmsg.getBytes());
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(2 ,TimeUnit.MINUTES)
                .build();
        okhttp3.MediaType mediaType = okhttp3.MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType,object);
        Request request = new Request.Builder()
                .url(url)
                .put(body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Connection", "keep-alive")
                .addHeader("Authorization", "Basic " + base64UserMsg)
                .build();

        try {

            Response response = client.newCall(request).execute();

            // str为json字符串
            return response.body();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;

    }

}
