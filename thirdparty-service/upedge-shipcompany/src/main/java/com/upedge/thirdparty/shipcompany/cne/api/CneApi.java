package com.upedge.thirdparty.shipcompany.cne.api;

import com.alibaba.fastjson.JSONObject;
import com.upedge.common.exception.CustomerException;
import com.upedge.thirdparty.shipcompany.cne.dto.CneCreateOrderRequest;
import com.upedge.thirdparty.shipcompany.cne.dto.CneOrderDto;
import com.upedge.thirdparty.shipcompany.cne.dto.CneRequestBase;
import com.upedge.thirdparty.shipcompany.cne.dto.CneShipMethodDto;
import okhttp3.*;
import org.apache.commons.codec.digest.DigestUtils;
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
            return new ArrayList<>();
        }
        List<CneShipMethodDto> cneShipMethodDtos = jsonObject.getJSONArray("List").toJavaList(CneShipMethodDto.class);
        return cneShipMethodDtos;
    }

    public static CneOrderDto createCneOrder(CneCreateOrderRequest request) throws Exception {
        String requestUrl = "https://api.cne.com/cgi-bin/EmsData.dll?DoApi";

        request.setRequestName("PreInputSet");

        String result = commonRequest(requestUrl,HttpMethod.POST, JSONObject.toJSONString(request));

        JSONObject jsonObject = JSONObject.parseObject(result);
        List<CneOrderDto> cneOrderDtos = jsonObject.getJSONArray("ErrList").toJavaList(CneOrderDto.class);
        if (1 != jsonObject.getInteger("OK")){
            String message = "";
            for (CneOrderDto cneOrderDto : cneOrderDtos) {
                message = cneOrderDto.getCMess() + " ";
            }
            throw new CustomerException(message);
        }
        return cneOrderDtos.get(0);

    }

    public static String getLabel(String trackingCode) throws Exception {
        CneRequestBase cneRequestBase = new CneRequestBase();
        String md5 = DigestUtils.md5Hex(cneRequestBase.getIcID() + trackingCode + cneRequestBase.getApiToken()).toLowerCase();
        String url = "https://label.cne.com/CnePrint?icID=8604585&cNos="+trackingCode+"&ptemp=label10x15_0&signature=" + md5;
        return url;
    }


    static String commonRequest(String url, HttpMethod method, String data) throws Exception {

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
        try {
            System.out.println(getLabel("00340434496772907676"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
