package com.upedge.thirdparty.shipcompany.yunexpress.api;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.upedge.common.utils.ListUtils;
import com.upedge.thirdparty.shipcompany.yunexpress.dto.WayBillCreateDto;
import com.upedge.thirdparty.shipcompany.yunexpress.dto.WayBillDeleteDto;
import com.upedge.thirdparty.shipcompany.yunexpress.dto.YunExpressGetTrackNumDto;
import com.upedge.thirdparty.shipcompany.yunexpress.request.WayBillCreateRequest;
import com.upedge.thirdparty.shipcompany.yunexpress.response.WayBillCreateResponse;
import com.upedge.thirdparty.shipcompany.yunexpress.response.YunExpressGetTrackNumResponse;
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

    public static YunExpressGetTrackNumDto getTrackNum(Long orderId){
        String apiUrl = REQUEST_URL + "/Waybill/GetTrackingNumber?CustomerOrderNumber=" + orderId;

        String result = "";
        try {
            result = commonRequest(apiUrl, HttpMethod.GET, null);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        YunExpressGetTrackNumResponse yunExpressGetTrackNumResponse = JSONObject.parseObject(result,YunExpressGetTrackNumResponse.class);
        if (yunExpressGetTrackNumResponse.getCode().equals("0000")){
            List<YunExpressGetTrackNumDto> yunExpressGetTrackNumDtos = yunExpressGetTrackNumResponse.getYunExpressGetTrackNumDtos();
            return yunExpressGetTrackNumDtos.get(0);
        }
        return null;
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
            String code = jsonObject.getString("Code");
            if (code.equals("0000")){
                List<YunExpressLabelVo> yunExpressLabelVos = jsonObject.getJSONArray("Item").toJavaList(YunExpressLabelVo.class);
                return yunExpressLabelVos.get(0).getUrl();
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
        return "";
    }

    public static String cancelYunExpressPack(Long orderId){
        String apiUrl = REQUEST_URL + "/WayBill/Delete";

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("OrderType",2);
        jsonObject.put("OrderNumber",orderId);

        try {
            String result = commonRequest(apiUrl, HttpMethod.POST, jsonObject.toString());
            jsonObject = JSONObject.parseObject(result);
            WayBillDeleteDto wayBillDeleteDto = jsonObject.toJavaObject(WayBillDeleteDto.class);
            if (wayBillDeleteDto.getCode().equals("0000")){
                return "success";
            }
            WayBillDeleteDto.WayBillDeleteItemDTO item = wayBillDeleteDto.getItem();;
            return item.getRemark();
        } catch (Exception e) {
            e.printStackTrace();

        }
        return null;
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

}
