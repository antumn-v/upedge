package com.upedge.thirdparty.shipcompany.fpx.config;

import com.upedge.thirdparty.shipcompany.fpx.model.AffterentParam;

public class FpxConfig {

    public static AffterentParam param = new AffterentParam();

    static {
        param.setAppKey(FpxConfig.API_KEY);
        param.setAppSecret(FpxConfig.API_SECRET);
        param.setVersion(FpxConfig.VERSION);
        param.setFormat("json");
        param.setLanguage("cn");
        param.setAccessToken("");
    }

//    public static final String API_KEY = "4a4dcb47-31af-447a-90be-3800f678ff92";
    public static final String API_KEY = "df03a067-0870-480e-95a8-7cbb29a6a9eb";

//    public static final String API_SECRET = "ef819173-cdd6-436f-8455-f3eddac3e51c";
    public static final String API_SECRET = "d2604a79-2740-4868-8151-904dc445cebe";

    public static final String VERSION = "1.0.0";

    public static final String CUSTOMER_CODE = "941824";
}
