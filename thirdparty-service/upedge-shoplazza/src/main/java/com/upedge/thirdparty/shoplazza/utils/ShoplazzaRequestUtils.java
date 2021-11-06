package com.upedge.thirdparty.shoplazza.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.*;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.TimeUnit;

public class ShoplazzaRequestUtils {

    public static JSONObject sendRequest(String url, String token, String paramStr, HttpMethod method, Object body) {

        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(30 * 1000);
        factory.setReadTimeout(2 * 60 * 1000);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Access-Token",  token);

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
            return response.getBody();
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
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
                .addHeader("Access-Token",  token)
                .build();

        try {

            Response response = client.newCall(request).execute();
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
