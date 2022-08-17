package com.upedge.thirdparty.shipcompany.yunexpress.api;

import com.alibaba.fastjson.JSONObject;
import com.upedge.common.utils.ListUtils;
import com.upedge.thirdparty.shipcompany.yunexpress.dto.WayBillCreateDto;
import com.upedge.thirdparty.shipcompany.yunexpress.request.WayBillCreateRequest;
import com.upedge.thirdparty.shipcompany.yunexpress.response.WayBillCreateResponse;
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
            result = commonRequest(apiUrl, HttpMethod.POST, null);
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }

        JSONObject jsonObject = JSONObject.parseObject(result);
        if (jsonObject.containsKey("items")) {
            List<YunExpressShipMethodVo> shipMethodVos = jsonObject.getJSONArray("items").toJavaList(YunExpressShipMethodVo.class);
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
        String result = commonRequest(apiUrl, HttpMethod.POST, JSONObject.parseArray(wayBillCreateDtos.toString()).toString());
        WayBillCreateResponse wayBillCreateResponse = JSONObject.parseObject(result, WayBillCreateResponse.class);
        return wayBillCreateResponse;

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
