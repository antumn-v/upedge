package com.upedge.thirdparty.saihe.utils;

import com.upedge.thirdparty.saihe.entity.getProducts.ApiGetProductResponse;
import com.upedge.thirdparty.saihe.service.SaiheService;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.Base64;
import java.util.concurrent.TimeUnit;

/**
 * Created by cjq on 2018/12/4.
 */
public class PostUtils {

    public static String sendPostV2(String url,String xmlData,String soapAction) {
        PrintWriter out = null;
        InputStream in = null;
        String result = "";
        try {

            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            conn.setRequestProperty("content-type", "text/xml;charset=utf-8");
            conn.setRequestProperty("charset", "UTF-8");
            conn.setRequestProperty("SOAPAction",soapAction);// 发送POST请求必须设置如下两行
            conn.setReadTimeout(60000*20);
            conn.setDoOutput(true);
            conn.setDoInput(true);

            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(xmlData);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = conn.getInputStream();
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = in.read(buffer)) != -1) {
                outStream.write(buffer, 0, len);
            }
            byte[] data = outStream.toByteArray();
            result=new String(data);
        } catch (Exception e) {
            result = "error";
            System.out.println("[POST请求]向地址：" + url + " 发送数据：发生错误!"+e);
            e.printStackTrace();
        } finally {// 使用finally块来关闭输出流、输入流
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                System.out.println("关闭流异常");
            }
        }
        return result;
    }

    public static String sendPostV(String url,String xmlData) {
        String result = "";
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(5 ,TimeUnit.MINUTES)
                .build();
        okhttp3.MediaType mediaType = okhttp3.MediaType.parse("application/soap+xml");
        RequestBody body = RequestBody.create(mediaType,xmlData);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .addHeader("Content-Type", "application/xml; charset=utf-8")
                .addHeader("Connection", "keep-alive")
                .build();

        try {
            Response response = client.newCall(request).execute();
            // str为json字符串
            result=response.body().string();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String sendPostV(String url,String xmlData,String soapAction) {
        String result = "";
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(5 ,TimeUnit.MINUTES)
                .build();
        okhttp3.MediaType mediaType = okhttp3.MediaType.parse("application/soap+xml");
        RequestBody body = RequestBody.create(mediaType,xmlData);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .addHeader("SOAPAction",soapAction)
                .addHeader("Content-Type", "application/xml; charset=utf-8")
                .addHeader("Connection", "keep-alive")
                .build();

        try {
            Response response = client.newCall(request).execute();
            // str为json字符串
            result=response.body().string();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String sendGet(String url,String apiKey,String accessToken) {
        String result = "";
        try {
            OkHttpClient client = new OkHttpClient.Builder()
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .readTimeout(5 ,TimeUnit.MINUTES)
                    .build();
            String userPassword= apiKey+":" + accessToken;
            String endcoding = Base64.getEncoder().encodeToString(userPassword.getBytes());
            Request request = new Request.Builder()
                    .url(url)
                    .get()
                    .addHeader("Content-Type", "application/xml; charset=utf-8")
                    .addHeader("Connection", "keep-alive")
                    .addHeader("Authorization", "Basic " + endcoding)
                    .build();

            Response response = client.newCall(request).execute();
            // str为json字符串
            result=response.body().string();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


    public static String sendPost1(String url,String soapAction,String xmlData) {
        PrintWriter out = null;
        InputStream in = null;
        String result = "";
        try {

            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("content-type", "text/xml;charset=utf-8");
            conn.setRequestProperty("charset", "UTF-8");
            conn.setRequestProperty("SOAPAction",soapAction);
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);

            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(xmlData);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = conn.getInputStream();
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = in.read(buffer)) != -1) {
                outStream.write(buffer, 0, len);
            }
            byte[] data = outStream.toByteArray();
            result=new String(data);

        } catch (Exception e) {
            result = "error";
            System.out.println("[POST请求]向地址：" + url + " 发送数据：发生错误!"+e);
            e.printStackTrace();
        } finally {// 使用finally块来关闭输出流、输入流
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                System.out.println("关闭流异常");
            }
        }
        return result;
    }
    public static void main(String[] args) {
        ApiGetProductResponse apiGetProductResponse = SaiheService.getProductsByClientSKUs("555502872339",null);
        if (apiGetProductResponse.getGetProductsResult().getStatus().equals("OK")) {

        }
    }
}
