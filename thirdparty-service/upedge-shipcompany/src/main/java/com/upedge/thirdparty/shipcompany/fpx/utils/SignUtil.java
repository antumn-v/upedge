package com.upedge.thirdparty.shipcompany.fpx.utils;

import com.upedge.thirdparty.shipcompany.fpx.model.AffterentParam;

public class SignUtil {
    private static final String signFormat = "app_key%sformat%smethod%stimestamp%sv%s%s%s";

    public SignUtil() {
    }

    public static String getSign(String appkey, String format, String method, String timestamp, String version, String bodyJson, String appSecret) {
        String signCause = String.format("app_key%sformat%smethod%stimestamp%sv%s%s%s", appkey, format, method, timestamp, version, bodyJson, appSecret);
        String sign = MD5Util.doMd5(signCause).toLowerCase();
        return sign;
    }

    public static String getSingByParam(AffterentParam param, String bodyJson, Long timestamp) {
        String sign = getSign(param.getAppKey(), param.getFormat(), param.getMethod(), timestamp.toString(), param.getVersion(), bodyJson, param.getAppSecret());
        return sign;
    }
}