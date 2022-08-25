package com.upedge.thirdparty.shipcompany.cne.api;

import com.alibaba.fastjson.JSONObject;
import com.upedge.thirdparty.shipcompany.cne.dto.CneRequestBase;
import com.upedge.thirdparty.shipcompany.cne.dto.CneShipMethodDto;
import okhttp3.*;
import org.springframework.http.HttpMethod;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class CneApi {


    public static List<CneShipMethodDto> getCneShipMethods(){
        String requestUrl = "https://api.cne.com/cgi-bin/EmsData.dll?DoApi";

        CneRequestBase cneRequestBase = new CneRequestBase();
        cneRequestBase.setRequestName("EmsKindList");

        String result = "";
        try {
            result = commonRequest(requestUrl,HttpMethod.POST, JSONObject.toJSONString(cneRequestBase));
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
        JSONObject jsonObject = JSONObject.parseObject(result);
        if (!jsonObject.containsKey("List")){
            System.out.println(jsonObject);
            return new ArrayList<>();
        }
        List<CneShipMethodDto> cneShipMethodDtos = jsonObject.getJSONArray("List").toJavaList(CneShipMethodDto.class);
        return cneShipMethodDtos;
    }



    static String commonRequest(String url, HttpMethod method, String data) throws Exception {
        System.out.println(data);

        OkHttpClient client = new OkHttpClient().newBuilder()
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .build();
        MediaType mediaType = MediaType.parse("application/json");

        RequestBody body = null;
        if (null != data) {
            body = RequestBody.create(mediaType,data);
        }

        Request request = new Request.Builder()
                .url(url)
                .addHeader("Content-Type","application/json;charset=UTF-8")
                .method(method.toString(), body)
                .build();
        Response response = null;

        response = client.newCall(request).execute();

        return response.body().string();

    }

    public static void main(String[] args) {
        System.out.println(getCneShipMethods());
    }
}
