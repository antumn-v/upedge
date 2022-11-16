package com.upedge.thirdparty.shopify.utils;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.TimeUnit;

public class RequestUtils {

    private Logger log = LoggerFactory.getLogger(getClass());


    public static ResponseEntity<JSONObject> sendRequest(String url, String token, String paramStr, HttpMethod method, Object body)  {

        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(30 * 1000);
        factory.setReadTimeout(2 * 60 * 1000);
//        String authmsg = api_key + ":" + token;
//        String base64UserMsg = Base64.getEncoder().encodeToString(token.getBytes());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("X-Shopify-Access-Token", token);

        if(body != null) {
            body = JSON.toJSONString(body);
        }
        HttpEntity<Object> entity = new HttpEntity<>(body, headers);
        RestTemplate restTemplate = new RestTemplate(factory);
        //String url = "https://"+ storeUrl + "/orders.json";
        if (StringUtils.isNotBlank(paramStr)) {
            url = url + "?" + paramStr;
        }
        ResponseEntity<JSONObject> response = null;
        try {
            response = restTemplate.exchange(url, method, entity, JSONObject.class);
        }catch (Exception e) {
            if (e.getMessage().contains("429 Too Many Requests")){
                try {
                    Thread.sleep(1000L);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
        }
        return response;
    }


    public static JSONObject putRequest(String object, String token, String url) {

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
                .addHeader("X-Shopify-Access-Token",  token)
                .build();

        try {

            Response response = client.newCall(request).execute();
            String requestLimit = response.header("X-Shopify-Shop-Api-Call-Limit");
            if("40/40".equals(requestLimit)) {
                Thread.sleep(5000L);
            }
            // str为json字符串
            return JSONObject.parseObject(response.body().string());
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            e.getMessage();
        }
        return null;
    }




}
