package com.upedge.thirdparty.shipcompany.yunexpress.api;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.upedge.common.utils.ListUtils;
import com.upedge.thirdparty.shipcompany.yunexpress.dto.WayBillCreateDto;
import com.upedge.thirdparty.shipcompany.yunexpress.request.WayBillCreateRequest;
import com.upedge.thirdparty.shipcompany.yunexpress.response.WayBillCreateResponse;
import com.upedge.thirdparty.shipcompany.yunexpress.vo.WayBillItemVo;
import com.upedge.thirdparty.shipcompany.yunexpress.vo.YunExpressLabelVo;
import com.upedge.thirdparty.shipcompany.yunexpress.vo.YunExpressShipMethodVo;
import okhttp3.*;
import org.springframework.http.HttpMethod;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class YunexpressApi {

    private static final String TOKEN = "Basic QzA2NjE2JjB4VlVRV0Rtb2VvPQ==";


    public static String REQUEST_URL = "http://omsapi.uat.yunexpress.com/api";


    public static List<YunExpressShipMethodVo> getShipMethods(String countryCode) {
        String apiUrl = REQUEST_URL + "/Common/GetShippingMethods?CountryCode=CN";

        String result = null;
        try {
            result = commonRequest(apiUrl, HttpMethod.GET, null);
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }

        JSONObject jsonObject = JSONObject.parseObject(result);
        if (jsonObject.containsKey("Items")) {
            List<YunExpressShipMethodVo> shipMethodVos = jsonObject.getJSONArray("Items").toJavaList(YunExpressShipMethodVo.class);
            return shipMethodVos;
        }
        return new ArrayList<>();

    }

    public static WayBillCreateResponse createWayBill(WayBillCreateRequest request) throws Exception {
        List<WayBillCreateDto> wayBillCreateDtos = request.getWayBillCreateDtos();
        if (ListUtils.isEmpty(wayBillCreateDtos)) {
            return null;
        }
        String apiUrl = REQUEST_URL + request.getUrl();
        String result = commonRequest(apiUrl, HttpMethod.POST, JSONObject.toJSONString(wayBillCreateDtos));
        JSONObject jsonObject = JSONObject.parseObject(result);
        List<WayBillItemVo> wayBillItemVos = jsonObject.getJSONArray("Item").toJavaList(WayBillItemVo.class);
        WayBillCreateResponse wayBillCreateResponse = jsonObject.toJavaObject(WayBillCreateResponse.class);
        wayBillCreateResponse.setItemVos(wayBillItemVos);
        return wayBillCreateResponse;
        /**
         * {"Item":[{"CustomerOrderNumber":"1534002172771790848","Success":0,"TrackType":null,"Remark":"请求参数错误，Value cannot be null. (Parameter 'key')","AgentNumber":null,"WayBillNumber":null,"RequireSenderAddress":0,"TrackingNumber":null,"ShipperBoxs":null}],"Code":"1001","Message":"提交失败!","RequestId":"0HMJRC73RR0UF:00000002","TimeStamp":"2022-08-19T02:20:30.141512+00:00"}
         */

    }

    public static String getSinglePackageLabel(String orderNo){
        String apiUrl = REQUEST_URL + "/Label/Print";
        JSONArray jsonArray = new JSONArray();
        jsonArray.add(orderNo);
        try {
            String result = commonRequest(apiUrl, HttpMethod.POST, jsonArray.toString());
            JSONObject jsonObject = JSONObject.parseObject(result);
            String code = jsonObject.getString("code");
            if (code.equals("0000")){
                List<YunExpressLabelVo> yunExpressLabelVos = jsonObject.getJSONArray("Item").toJavaList(YunExpressLabelVo.class);
                return yunExpressLabelVos.get(0).getUrl();
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
        return "";
    }


    static String commonRequest(String url, HttpMethod method, String data) throws Exception {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .build();
        MediaType mediaType = MediaType.parse("application/json");

        RequestBody body = null;
        if (null != data) {
            body = RequestBody.create(mediaType, data);
        }

        Request request = new Request.Builder()
                .url(url)
                .method(method.toString(), body)
                .addHeader("Authorization", TOKEN)
                .build();
        Response response = null;

        response = client.newCall(request).execute();

        return response.body().string();

    }

    public static void main(String[] args) {
        String result = "{\n" +
                "    \"Item\": [\n" +
                "        {\n" +
                "            \"CustomerOrderNumber\": \"1534005174605803520\",\n" +
                "            \"Success\": 1,\n" +
                "            \"TrackType\": \"2\",\n" +
                "            \"Remark\": \"\",\n" +
                "            \"AgentNumber\": null,\n" +
                "            \"WayBillNumber\": \"YT2223131246000002\",\n" +
                "            \"RequireSenderAddress\": 0,\n" +
                "            \"TrackingNumber\": null,\n" +
                "            \"ShipperBoxs\": [\n" +
                "                {\n" +
                "                    \"BoxNumber\": \"1534005174605803520\",\n" +
                "                    \"ShipperHawbcode\": \"YT2223131246000002U001\"\n" +
                "                }\n" +
                "            ]\n" +
                "        }\n" +
                "    ],\n" +
                "    \"Code\": \"0000\",\n" +
                "    \"Message\": \"提交成功!\",\n" +
                "    \"RequestId\": \"0HMJRC726F2RQ:00000002\",\n" +
                "    \"TimeStamp\": \"2022-08-19T05:24:50.4832254+00:00\"\n" +
                "}";

        JSONObject jsonObject = JSONObject.parseObject(result);
        List<WayBillItemVo> wayBillItemVos = jsonObject.getJSONArray("Item").toJavaList(WayBillItemVo.class);
        System.out.println(wayBillItemVos);
        WayBillCreateResponse wayBillCreateResponse = JSONObject.parseObject(result, WayBillCreateResponse.class);
        System.out.println(wayBillCreateResponse);
    }
}
