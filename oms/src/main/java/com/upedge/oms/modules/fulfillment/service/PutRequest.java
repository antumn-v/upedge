package com.upedge.oms.modules.fulfillment.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class PutRequest {


    public static String httpUrlConnectionPut(String httpUrl, String apiToken, Object object) {
        PrintWriter out = null;
        String result = "";
        URL url = null;
        try {
            url = new URL(httpUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        if (url != null) {
            try {
                HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
                urlConn.setRequestProperty("content-type", "application/json");
                urlConn.setDoInput(true);
                urlConn.setDoOutput(true);
                //设置请求方式为 PUT
                urlConn.setRequestMethod("PUT");

                urlConn.setRequestProperty("Content-Type", "application/json");
                urlConn.setRequestProperty("Accept", "application/json");

                urlConn.setRequestProperty("Charset", "UTF-8");
                //String userPassword= apiKey+":" + accessToken;
                //String endcoding = Base64.getEncoder().encodeToString(userPassword.getBytes());
                urlConn.setRequestProperty("Authorization", "Basic " + apiToken);

                //DataOutputStream dos = new DataOutputStream(urlConn.getOutputStream());
                //写入请求参数
                //这里要注意的是，在构造JSON字符串的时候，实践证明，最好不要使用单引号，而是用“\”进行转义，否则会报错
                // 关于这一点在上面给出的参考文章里面有说明
                out = new PrintWriter(urlConn.getOutputStream());
                // 发送请求参数
                out.print(object);
                // flush输出流的缓冲
                out.flush();
                /*dos.writeBytes(object);
                dos.flush();
                dos.close();*/
                //读取响应
                InputStreamReader isr = new InputStreamReader(urlConn.getInputStream());
                BufferedReader br = new BufferedReader(isr);
                String inputLine = null;
                while ((inputLine = br.readLine()) != null) {
                    result += inputLine;
                }
                isr.close();
                urlConn.disconnect();


            } catch (Exception e) {
                result = "error";
                System.out.println("[PUT请求]向地址：" + url + " 发送数据：" + object + " 发生错误!"+e);
                e.printStackTrace();
            }
        }

        return result;

    }
}
