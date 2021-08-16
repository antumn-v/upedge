package com.upedge.common.utils;


import org.apache.commons.lang3.StringUtils;

import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by cjq on 2018/12/8.
 */
public class UrlUtils {

    private static URL url;
    private static HttpURLConnection con;
    private static int state = -1;

    /**
     * 功能：检测当前URL是否可连接或是否有效,
     * 描述：最多连接网络 5 次, 如果 5 次都不成功，视为该地址不可用
     * @param urlStr 指定URL网络地址
     * @return URL
     */
    public synchronized boolean isConnect(String urlStr) {
        boolean res=false;
        int counts = 0;
        if (urlStr == null || urlStr.length() <= 0) {
            return false;
        }
        while (counts < 5) {
            try {
                url = new URL(urlStr);
                con = (HttpURLConnection) url.openConnection();
                state = con.getResponseCode();
                if (state == 200) {
                    res= true;
                }
                break;
            }catch (Exception ex) {
                counts++;
                urlStr = null;
                continue;
            }
        }
        return res;
    }

    public static String getNameByUrl(String url){
        if(StringUtils.isBlank(url)){
            return null;
        }
        try {
            url=url.split("[?]")[0];
            //获取链接前缀
            String prefixes = url.substring(0, url.lastIndexOf("/"));
            //获取链接名称
            String infix = url.substring(url.lastIndexOf("/"));
            infix = infix.substring(1, infix.indexOf("."));
            //获取链接后缀
            //String suffix = url.substring(url.lastIndexOf("."));
            return infix;
        }catch (Exception e){
            return null;
        }

    }


    public static void main(String[] args) {
        System.out.println(UrlUtils.getNameByUrl("https://detail.1688.com/offer/566804255439.html"));
    }
}
