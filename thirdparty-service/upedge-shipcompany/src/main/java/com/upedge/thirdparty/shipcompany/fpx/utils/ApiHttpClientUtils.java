package com.upedge.thirdparty.shipcompany.fpx.utils;

import com.alibaba.fastjson.JSONObject;
import com.upedge.thirdparty.shipcompany.fpx.config.FpxConfig;
import com.upedge.thirdparty.shipcompany.fpx.constants.AmbientEnum;
import com.upedge.thirdparty.shipcompany.fpx.constants.MethodEnum;
import com.upedge.thirdparty.shipcompany.fpx.model.AffterentParam;
import com.upedge.thirdparty.shipcompany.fpx.model.ResponseMsg;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ApiHttpClientUtils extends HttpClientUtils {
    public ApiHttpClientUtils() {
    }

    public static String apiGet(AffterentParam param, Map<String, Object> paramMap, AmbientEnum ambient) {
        if (!checkParam(param)) {
            return ResponseMsg.fial("参数缺失").toString();
        } else {
            String jsonStr = getBodyJson(paramMap);
            String urlProfiles = getAddress(ambient);
            StringBuilder urlStr = new StringBuilder(urlProfiles);
            urlStr.append("/router/api/service");
            Long timestamp = (new Date()).getTime();
            String sign = SignUtil.getSingByParam(param, jsonStr, timestamp);
            StringBuilder url = getRequestUrl(param, urlStr, timestamp, sign);
            if (MapUtils.isNotEmpty(paramMap)) {
                Iterator var9 = paramMap.entrySet().iterator();

                while(var9.hasNext()) {
                    Map.Entry<String, Object> entry = (Map.Entry)var9.next();
                    url.append("&" + (String)entry.getKey() + "=" + entry.getValue());
                }
            }

            String response = null;

            try {
                response = get(url.toString());
            } catch (IOException var11) {
                var11.printStackTrace();
            }

            return response;
        }
    }

    public static String apiJsongPost(AffterentParam param, JSONObject jsonObject, AmbientEnum ambient) {
        if (!checkParam(param)) {
            return ResponseMsg.fial("参数缺失").toString();
        } else {
            String bodyJsonStr = jsonObject.toJSONString();
            String urlProfiles = getAddress(ambient);
            StringBuilder urlStr = new StringBuilder(urlProfiles);
            urlStr.append("/router/api/service");
            Long timestamp = (new Date()).getTime();
            String sign = SignUtil.getSingByParam(param, bodyJsonStr, timestamp);
            StringBuilder url = getRequestUrl(param, urlStr, timestamp, sign);
            String response = null;

            try {
                response = post(url.toString(), bodyJsonStr);
            } catch (IOException var11) {
                var11.printStackTrace();
            }

            return response;
        }
    }

    private static StringBuilder getRequestUrl(AffterentParam param, StringBuilder urlStr, Long timestamp, String sign) {
        urlStr.append("?method=" + param.getMethod());
        urlStr.append("&app_key=" + param.getAppKey());
        urlStr.append("&v=" + param.getVersion());
        urlStr.append("&timestamp=" + timestamp);
        urlStr.append("&format=" + param.getFormat());
        urlStr.append("&access_token=" + param.getAccessToken());
        urlStr.append("&sign=" + sign);
        urlStr.append("&language=" + param.getLanguage());
        return urlStr;
    }

    private static String getBodyJson(Map<String, Object> paramMap) {
        JSONObject jsonObject = new JSONObject(paramMap);
        return jsonObject.toString();
    }

    private static boolean checkParam(AffterentParam param) {
        return !StringUtils.isBlank(param.getAppKey()) && !StringUtils.isBlank(param.getVersion()) && !StringUtils.isBlank(param.getMethod()) && !StringUtils.isBlank(param.getVersion()) && !StringUtils.isBlank(param.getFormat()) && !StringUtils.isBlank(param.getLanguage()) && !StringUtils.isBlank(param.getAppSecret());
    }

    public static void main(String[] args) {
        AffterentParam param = new AffterentParam();
        param.setAppKey(FpxConfig.API_KEY);
        param.setAppSecret(FpxConfig.API_SECRET);
        param.setVersion(FpxConfig.VERSION);
        param.setMethod(MethodEnum.inventory_get.getMethod());
        param.setFormat("json");

        Map<String, Object> paramMap = new HashMap<>();

    }
}