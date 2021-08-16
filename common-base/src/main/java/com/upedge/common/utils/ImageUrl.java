package com.upedge.common.utils;


import org.apache.commons.lang3.StringUtils;

/**
 * Created by cjq on 2018/10/13.
 */
public class ImageUrl {


    /**
     *图片格式化去掉缩放比例
     */
    public static String exchangeImgUrl(String url){
        if(StringUtils.isBlank(url)){
            return null;
        }
        try {
            url=url.split("[?]")[0];
            //获取链接前缀
            String prefixes = url.substring(0, url.lastIndexOf("/"));
            //System.out.println(prefixes);
            //获取链接名称
            String infix = url.substring(url.lastIndexOf("/"));
            //获取去除缩放比例的名称
            infix = infix.substring(0, infix.indexOf("."));
            //System.out.println(infix);
            //获取链接后缀
            String suffix = url.substring(url.lastIndexOf("."));
            if(suffix.equals(".aw")){
                return null;
            }
            //System.out.println(suffix);
            //重组链接
            //System.out.println(prefixes + infix + suffix);
            url=prefixes + infix + suffix;
            return url;
        }catch (Exception e){
            // e.printStackTrace();
            return null;
        }

    }

    public static void main(String[] args) {
//        String s="https://cbu01.alicdn.com/img/ibank/2018/860/255/9499552068_1460459478.jpg?__r__=1539250644517";
//        String s1="https://cbu01.alicdn.com/img/ibank/2018/860/255/9499552068_1460459478.jpg";
//        String s2="https://amos.alicdn.com/online.aw?v=2&uid=%E4%BC%8A%E5%85%B0%E8%92%82%E6%80%9D%E9%85%8D%E9%A5%B0&site=cnalichn&s=10&charset=UTF-8";
//        System.out.println(exchangeImgUrl("https://cbu01.alicdn.com/img/ibank/2017/652/351/7791153256_1425413100.310x310.jpg_460x460.jpg"));
//        System.out.println(exchangeImgUrl(s));
//        System.out.println(exchangeImgUrl(s1));
//        System.out.println(exchangeImgUrl(s2));
//        System.out.println(exchangeImgUrl("https://img.alicdn.com/L1/249/14641382983631/1.0.0/img/14641418721506.png"));
//        System.out.println(exchangeImgUrl("https://ma.m.1688.com/touch/code/sCode?w=80&h=80&el=l&type=offer&id=562192773612"));
//        System.out.println(exchangeImgUrl("https://cbu01.alicdn.com/"));
//        System.out.println(exchangeImgUrl("https://cbu01.alicdn.com/img/ibank/2018/198/134/9559431891_176440000.jpg"));

        System.out.println(exchangeImgUrl("http://ae01.alicdn.com/kf/HTB1z2.Yah_rK1RkHFqDq6yJAFXa5.jpg"));
        System.out.println(exchangeImgUrl("https://cbu01.alicdn.com/img/ibank/2017/652/351/7791153256_1425413100.310x310.jpg_460x460.jpg"));
    }


    public static Integer strFormatInteger(String str){
        Integer res=1;
        try {
            res= Integer.valueOf(str);
            if(res<=0){
                res=1;
            }
        }catch (Exception e){
        }
        return res;
    }
}
