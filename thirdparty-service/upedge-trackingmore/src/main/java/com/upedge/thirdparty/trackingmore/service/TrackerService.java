package com.upedge.thirdparty.trackingmore.service;


import com.alibaba.fastjson.JSON;
import com.upedge.thirdparty.trackingmore.entity.*;
import com.upedge.thirdparty.trackingmore.entity.*;
import com.upedge.thirdparty.trackingmore.entity.webhook.TrackingData;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by cjq on 2019/6/6.
 */
public class TrackerService {

    static final String URL="http://118.24.207.189:8080";
//    static final String URL="http://127.0.0.1:8080";

    //更新单个运单号
    public static TrackingData createTrackingNumber(PostTracking tracking){

        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy/MM/dd HH:mm");
        //设置发货时间
//        if(tracking.getshipTime!=null) {
//            tracking.setOrder_create_time(simpleDateFormat.format(shipTime));
//        }
//        tracking.setLogistics_channel(logisticsChannel);
        String requestData= JSON.toJSONString(tracking);
        System.out.println("createTrackingNumber");
        try {

            String result= ApiRequest.sendPost(URL+"/trackingMore/updateTrackingNumber",requestData);
            System.out.println("updateTracking result=======" + result);
            PostTrackingsReponse trackingsReponse=JSON.parseObject(result, PostTrackingsReponse.class);
            if (trackingsReponse == null){
                return null;
            }
            return trackingsReponse.getData();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    //列出单个运单号物流信息
    public static GetTracking getTrackingResultsOfASingleTracking(String carrierCode, String trackingNumber){
        System.out.println("getTrackingResultsOfASingleTracking");
        try {
            String result=ApiRequest.sendGet(URL+"/trackingMore/queryCarriers?carrierCode="+carrierCode+"&trackingNumber="+trackingNumber);
            System.out.println("getTrackingResultsOfASingleTracking result======="+result);
            GetTrackingsReponse getTrackingsReponse= JSON.parseObject(result, GetTrackingsReponse.class);
            return getTrackingsReponse.getData();
        }catch(Exception e){
            e.printStackTrace();
            return  null;
        }
    }


    //获取支持的所有运输商
    public List<TrackingMoreCarrier> listAllCarriers(){
        List<TrackingMoreCarrier> carrierList=new ArrayList<>();
        try {
            String urlStr = null;
            String requestData = null;
            String result = new TrackerApi().orderOnlineByJson(requestData, urlStr, "carriers");
//            System.out.println("result=======" + result);
            CarriersReponse carriersData=JSON.parseObject(result, CarriersReponse.class);
//            System.out.println(carriersData.getMeta().getCode()+" "+carriersData.getMeta().getType()+" "+carriersData.getMeta().getMessage());
            for(Carrier carrier:carriersData.getData()){
                System.out.println(carrier.getName()+" "+carrier.getCode()+" "+carrier.getPhone()+" "+carrier.getName_cn());
                TrackingMoreCarrier trackingMoreCarrier=new TrackingMoreCarrier();
                trackingMoreCarrier.setCode(carrier.getCode());
                trackingMoreCarrier.setName(carrier.getName());
                trackingMoreCarrier.setPhone(carrier.getPhone());
                trackingMoreCarrier.setHomepage(carrier.getHomepage());
                trackingMoreCarrier.setType(carrier.getType());
                trackingMoreCarrier.setPicture(carrier.getPicture());
                trackingMoreCarrier.setNameCn(carrier.getName_cn());
                carrierList.add(trackingMoreCarrier);
            }
        }catch (Exception e){

        }
        return carrierList;
    }


    //通过运单号检测运输商
    public void detectCarriersByTrackingNumber(){
        try {
            String urlStr = null;
            String requestData="{\"tracking_number\":\"EA152563251CN\"}";
            String result = new TrackerApi().orderOnlineByJson(requestData,urlStr,"carriers/detect");
            System.out.println("result======="+result);
        }catch (Exception e){

        }
    }

    //创建单个运单号
    public static TrackingData createTrackingNumber(String carrierCode, String trackingNumber, String orderSourceName){
        PostTracking tracking=new PostTracking();
        tracking.setTracking_number(trackingNumber);
        tracking.setCarrier_code(carrierCode);
        tracking.setTitle("Panda");
        tracking.setCustomer_name(orderSourceName);
        String requestData=JSON.toJSONString(tracking);
        System.out.println("createTrackingNumber");

        try {
            String urlStr = null;
//            String requestData = "{\"tracking_number\": \"EA152563254CN\",\"carrier_code\":\"china-ems\",\"title\":\"chase chen\",\"customer_name\":\"chase\",\"customer_email\":\"abc@qq.com\",\"order_id\":\"#123\",\"order_create_time\":\"2018-05-20 12:00\",\"destination_code\":\"IL\",\"tracking_ship_date\":\"1521314361\",\"tracking_postal_code\":\"13ES20\",\"lang\":\"en\",\"logistics_channel\":\"4PX page\"}";
            String result = new TrackerApi().orderOnlineByJson(requestData, urlStr, "post");
            System.out.println("createTrackingNumber result=======" + result);
            PostTrackingsReponse trackingsReponse=JSON.parseObject(result, PostTrackingsReponse.class);
            if(trackingsReponse.getMeta().getCode()==4016){
                //更新信息
                return updateTracking(carrierCode,trackingNumber,orderSourceName);
            }
            return trackingsReponse.getData();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    //	获取多个运单号的物流信息
    public void listAllTrackings(){
        try {
            String urlStr = "?page=1&limit=100&created_at_min=1521314361&created_at_max=1541314361&update_time_min=1521314361&update_time_max=1541314361&order_created_time_min=1521314361&order_created_time_max=1541314361&numbers=1212121,UG586285221CN&orders=123&lang=cn";
            String requestData = null;
            String result = new TrackerApi().orderOnlineByJson(requestData, urlStr, "get");
            System.out.println("result=======" + result);
        }catch (Exception e){

        }
    }

    //创建多个运单号
    public void createMutiTrackingNumber(){
        try {
            String urlStr =null;
            String requestData="[{\"tracking_number\": \"EA152565241CN\",\"carrier_code\":\"china-ems\",\"title\":\"chase chen\",\"customer_name\":\"chase\",\"customer_email\":\"abc@qq.com\",\"order_id\":\"#123444\",\"order_create_time\":\"2018-05-20 12:00\",\"destination_code\":\"IL\",\"tracking_ship_date\":\"1525314361\",\"tracking_postal_code\":\"13ES20\",\"lang\":\"en\",\"logistics_channel\":\"4PX page\"},{\"tracking_number\": \"EA152563242CN\",\"carrier_code\":\"china-ems\",\"title\":\"chase chen\",\"customer_name\":\"chase\",\"customer_email\":\"abc@qq.com\",\"order_id\":\"#123444\",\"order_create_time\":\"2018-05-20 12:00\",\"destination_code\":\"IL\",\"tracking_ship_date\":\"1521314361\",\"tracking_postal_code\":\"13ES20\",\"lang\":\"en\",\"logistics_channel\":\"4PX page1\"}]";
            String result = new TrackerApi().orderOnlineByJson(requestData,urlStr,"batch");
            System.out.println("result======="+result);
        }catch(Exception e){

        }
    }

    public void modifyCourierCode(String carrierCode, String trackingNumber){
        try {
            String urlStr =null;
            String requestData="{\"tracking_number\": \"EA152563242CN\",\"carrier_code\":\"dhl\",\"update_carrier_code\":\"china-ems\"}";
            String result = new TrackerApi().orderOnlineByJson(requestData,urlStr,"update");
            System.out.println("result======="+result);
        }catch (Exception e){

        }
    }

    public void getuserinfo() {
        try {
            String urlStr =null;
            String requestData=null;
            String result = new TrackerApi().orderOnlineByJson(requestData,urlStr,"getuserinfo");
            System.out.println("result======="+result);
        }catch(Exception e){

        }
    }

    //更新信息
    public static TrackingData updateTracking(String carrierCode, String trackingNumber, String orderSourceName){
        PostTracking tracking=new PostTracking();
        tracking.setTracking_number(trackingNumber);
        tracking.setCarrier_code(carrierCode);
        tracking.setTitle("Panda");
        tracking.setCustomer_name(orderSourceName);
        String requestData=JSON.toJSONString(tracking);
        System.out.println("updateTracking");
        System.out.println(requestData);
        try {
            String urlStr = "/"+carrierCode+"/"+trackingNumber;
//            String requestData = "{\"title\": \"testtitle\",\"customer_name\":\"javatest\",\"customer_email\":\"942632688@qq.com\",\"order_id\":\"#1234567\",\"logistics_channel\":\"chase chen java\",\"customer_phone\":\"+86 13873399982\",\"destination_code\":\"US\",\"status\":\"7\"}";
            String result = new TrackerApi().orderOnlineByJson(requestData, urlStr, "codeNumberPut");
            System.out.println("updateTracking result=======" + result);
            PostTrackingsReponse trackingsReponse=JSON.parseObject(result, PostTrackingsReponse.class);
            return trackingsReponse.getData();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

}
