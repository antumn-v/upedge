package com.upedge.thirdparty.fpx.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.upedge.thirdparty.fpx.constants.AmbientEnum;
import com.upedge.thirdparty.fpx.model.TokenResultEntity;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class TokenUtil extends HttpClientUtils {
    private static final String ACCESS_TOKEN_RUL = "/accessToken/get";

    public TokenUtil() {
    }

    public static TokenResultEntity getToken(String clientId, String redirectUri, String clientSecret, String code, String grantType, AmbientEnum ambient) {
        String urlProfiles = getAddress(ambient);
        String url = String.format(urlProfiles + "%s", "/accessToken/get");
        String result = null;
        Map<String, Object> map = new HashMap();
        map.put("client_id", clientId);
        map.put("redirect_uri", redirectUri);
        map.put("client_secret", clientSecret);
        map.put("grant_type", grantType);
        map.put("code", code);

        try {
            result = post(url, map);
        } catch (IOException var11) {
            var11.printStackTrace();
        }

        return pareJson(result);
    }

    public static TokenResultEntity getTokenByRefreshToken(String clientId, String clientSecret, String grantType, String refreshToken, String redirectUri, AmbientEnum ambient) {
        String urlProfiels = getAddress(ambient);
        String url = String.format(urlProfiels + "%s", "/accessToken/get");
        String result = null;
        Map<String, Object> requestMap = new HashMap();
        requestMap.put("client_id", clientId);
        requestMap.put("client_secret", clientSecret);
        requestMap.put("grant_type", grantType);
        requestMap.put("refresh_token", refreshToken);
        requestMap.put("redirect_uri", redirectUri);

        try {
            result = post(url, requestMap);
        } catch (IOException var11) {
            var11.printStackTrace();
        }

        return pareJson(result);
    }

    private static TokenResultEntity pareJson(String jsonStr) {
        if (StringUtils.isBlank(jsonStr)) {
            return null;
        } else {
            JSONObject jsonObject = JSON.parseObject(jsonStr);
            String accessToken = jsonObject.getString("access_token");
            String refreshToke = jsonObject.getString("refresh_token");
            String expiresIn = jsonObject.getString("expires_in");
            return new TokenResultEntity(accessToken, refreshToke, expiresIn);
        }
    }
}
