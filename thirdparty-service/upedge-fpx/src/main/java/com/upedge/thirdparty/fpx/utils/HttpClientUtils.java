package com.upedge.thirdparty.fpx.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.upedge.thirdparty.fpx.constants.AmbientEnum;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class HttpClientUtils {
    public HttpClientUtils() {
    }

    public static String get(String url) throws IOException {
        CloseableHttpResponse response = null;

        String var3;
        try {
            HttpGet httpGet = new HttpGet(url);
            response = HttpClients.createDefault().execute(httpGet);
            if (response.getStatusLine().getStatusCode() != 200) {
                JSONObject errorObject = JSONObject.parseObject(EntityUtils.toString(response.getEntity()));
                String errorMsg = errorObject.getString("message");
                throw new IOException("HTTP ERROR: " + response.getStatusLine().getStatusCode() + "," + errorMsg);
            }

            var3 = EntityUtils.toString(response.getEntity());
        } finally {
            if (response != null) {
                response.close();
            }

        }

        return var3;
    }

    public static String postJson(String url, Object object) throws IOException {
        String jsonData = JSON.toJSONString(object);
        return post(url, jsonData);
    }

    public static String post(String url, String jsonData) throws IOException {
        CloseableHttpResponse response = null;
        HttpPost post = new HttpPost(url);
        post.addHeader("Content-Type", "application/json");
        if (StringUtils.isNotEmpty(jsonData)) {
            post.setEntity(new StringEntity(jsonData, Charset.forName("UTF-8")));
        }

        try {
            response = HttpClients.createDefault().execute(post);
            if (response.getStatusLine().getStatusCode() != 200) {
                JSONObject errorObject = JSONObject.parseObject(EntityUtils.toString(response.getEntity()));
                String errorMsg = errorObject.getString("message");
                throw new IOException("HTTP ERROR: " + response.getStatusLine().getStatusCode() + "," + errorMsg);
            }
        } catch (IOException var6) {
            throw new IOException(var6.getMessage(), var6);
        }

        return EntityUtils.toString(response.getEntity());
    }

    public static String post(String url, Map<String, Object> params) throws IOException {
        CloseableHttpResponse response = null;

        String var10;
        try {
            HttpPost post = new HttpPost(url);
            post.addHeader("Content-Type", "application/x-www-form-urlencoded");
            if (MapUtils.isNotEmpty(params)) {
                List<NameValuePair> nvps = new ArrayList();
                Iterator var5 = params.entrySet().iterator();

                while(var5.hasNext()) {
                    Map.Entry<String, Object> entry = (Map.Entry)var5.next();
                    nvps.add(new BasicNameValuePair((String)entry.getKey(), entry.getValue().toString()));
                }

                post.setEntity(new UrlEncodedFormEntity(nvps, StandardCharsets.UTF_8));
            }

            response = HttpClients.createDefault().execute(post);
            if (response.getStatusLine().getStatusCode() != 200) {
                throw new IOException("HTTP Error:" + response.getStatusLine().getStatusCode());
            }

            var10 = EntityUtils.toString(response.getEntity());
        } finally {
            if (response != null) {
                response.close();
            }

        }

        return var10;
    }

    public static String getAddress(AmbientEnum ambient) {
        String urlProfiels = AmbientEnum.FORMAT_ADDRESS.equals(ambient) ? "http://open.4px.com" : "http://open.4px.com";
        return urlProfiels;
    }
}
