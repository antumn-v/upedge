package com.upedge.oms.modules.fulfillment.service;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;

public class PostRequest {

    public static String sendPost(String url, String apiToken, Object object) {
        HttpServletResponse response = null;
        PrintWriter out = null;
        InputStream in = null;
        String result = "";
        try {

            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("content-type", "application/json");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
//            String userPassword= apiKey+":" + accessToken;
//            String endcoding = Base64.getEncoder().encodeToString(userPassword.getBytes());
            conn.setRequestProperty("Authorization", "Basic " + apiToken);
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);

            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(object);
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
            System.out.println("[POST请求]向地址：" + url + " 发送数据：" + object + " 发生错误!"+e);
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

}
