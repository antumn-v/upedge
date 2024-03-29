package com.upedge.thirdparty.trackingmore.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by cjq on 2019/4/27.
 */
public class TrackerApi {


    /** Apikey*/
    private String Apikey="a0b4a659-16c6-42be-bb07-2f8f83db6209";

    /**
     * Json
     */
    public String orderOnlineByJson(String requestData, String urlStr, String type) throws Exception {
        //---headerParams
        Map<String, String> headerparams = new HashMap<String, String>();
        headerparams.put("Trackingmore-Api-Key", Apikey);
        headerparams.put("Content-Type", "application/json");
        //---bodyParams
        List<String> bodyParams = new ArrayList<String>();
        String result =null;
        if(type.equals("post")){
            String ReqURL="http://api.trackingmore.com/v2/trackings/post";
            bodyParams.add(requestData);
            result=sendPost(ReqURL, headerparams , bodyParams,"POST");

        }else if(type.equals("get")){

            String ReqURL="http://api.trackingmore.com/v2/trackings/get";
            String RelUrl = ReqURL+urlStr;
            result=sendPost(RelUrl, headerparams , bodyParams,"GET");

        }else if(type.equals("batch")){

            String ReqURL="http://api.trackingmore.com/v2/trackings/batch";
            bodyParams.add(requestData);
            result=sendPost(ReqURL, headerparams , bodyParams,"POST");

        }else if(type.equals("codeNumberGet")){

            String ReqURL="http://api.trackingmore.com/v2/trackings";
            String RelUrl = ReqURL+urlStr;
            result=sendGet(RelUrl, headerparams,"GET");

        }else if(type.equals("codeNumberPut")){

            String ReqURL="http://api.trackingmore.com/v2/trackings";
            bodyParams.add(requestData);
            String RelUrl = ReqURL+urlStr;
            result=sendPost(RelUrl, headerparams , bodyParams,"PUT");

        }else if(type.equals("codeNumberDelete")){

            String ReqURL="http://api.trackingmore.com/v2/trackings";
            String RelUrl = ReqURL+urlStr;
            result=sendGet(RelUrl, headerparams ,"DELETE");

        }else if(type.equals("realtime")){

            String ReqURL="http://api.trackingmore.com/v2/trackings/realtime";
            bodyParams.add(requestData);
            result=sendPost(ReqURL, headerparams , bodyParams,"POST");

        }else if(type.equals("carriers")){

            String ReqURL="http://api.trackingmore.com/v2/carriers";
            result=sendGet(ReqURL, headerparams ,"GET");

        }else if(type.equals("carriers/detect")){

            String ReqURL="http://api.trackingmore.com/v2/carriers/detect";
            bodyParams.add(requestData);
            result=sendPost(ReqURL, headerparams , bodyParams,"POST");

        }else if(type.equals("update")){

            String ReqURL="http://api.trackingmore.com/v2/trackings/update";
            bodyParams.add(requestData);
            result=sendPost(ReqURL, headerparams , bodyParams,"POST");

        }else if(type.equals("getuserinfo")){

            String ReqURL="http://api.trackingmore.com/v2/trackings/getuserinfo";
            result=sendGet(ReqURL, headerparams ,"GET");

        }else if(type.equals("getstatusnumber")){

            String ReqURL="http://api.trackingmore.com/v2/trackings/getstatusnumber";
            result=sendGet(ReqURL, headerparams , "GET");

        }else if(type.equals("notupdate")){

            String ReqURL="http://api.trackingmore.com/v2/trackings/notupdate";
            bodyParams.add(requestData);
            result=sendPost(ReqURL, headerparams , bodyParams,"POST");

        }else if(type.equals("remote")){

            String ReqURL="http://api.trackingmore.com/v2/trackings/remote";
            bodyParams.add(requestData);
            result=sendPost(ReqURL, headerparams , bodyParams,"POST");

        }else if(type.equals("costtime")){

            String ReqURL="http://api.trackingmore.com/v2/trackings/costtime";
            bodyParams.add(requestData);
            result=sendPost(ReqURL, headerparams , bodyParams,"POST");

        }else if(type.equals("delete")){

            String ReqURL="http://api.trackingmore.com/v2/trackings/delete";
            bodyParams.add(requestData);
            result=sendPost(ReqURL, headerparams , bodyParams,"POST");

        }else if(type.equals("updatemore")){

            String ReqURL="http://api.trackingmore.com/v2/trackings/updatemore";
            bodyParams.add(requestData);
            result=sendPost(ReqURL, headerparams , bodyParams,"POST");

        }


        return result;


    }


    private String sendPost(String url, Map<String, String> headerParams , List<String> bodyParams, String mothod) {
        OutputStreamWriter out = null;
        InputStream in = null;
        String result = new String();
        try {
            URL realUrl = new URL(url);
            HttpURLConnection conn =(HttpURLConnection) realUrl.openConnection();

            conn.setDoOutput(true);
            conn.setDoInput(true);

            conn.setRequestMethod(mothod);

            for (Map.Entry<String, String> entry : headerParams.entrySet()) {
                conn.setRequestProperty(entry.getKey(), entry.getValue());
            }
            conn.connect();

            out = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");

            StringBuffer sbBody = new StringBuffer();
            for (String str : bodyParams) {
                sbBody.append(str);
            }
            out.write(sbBody.toString());

            out.flush();

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
            e.printStackTrace();
        }

        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        return result;
    }

    public static String sendGet(String url, Map<String, String> headerParams, String mothod ) {
        String result = "";
        InputStream in = null;
        try {
            String urlNameString = url;
            URL realUrl = new URL(urlNameString);

            HttpURLConnection connection =(HttpURLConnection) realUrl.openConnection();

            connection.setRequestMethod(mothod);

            for (Map.Entry<String, String> entry : headerParams.entrySet()) {
                connection.setRequestProperty(entry.getKey(), entry.getValue());
            }

            connection.connect();

            Map<String, List<String>> map = connection.getHeaderFields();

            for (String key : map.keySet()) {
                System.out.println(key + "--->" + map.get(key));
            }

            in = connection.getInputStream();
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = in.read(buffer)) != -1) {
                outStream.write(buffer, 0, len);
            }
            byte[] data = outStream.toByteArray();
            result=new String(data);

        } catch (Exception e) {
            System.out.println("Exception " + e);
            e.printStackTrace();
        }

        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }



}
