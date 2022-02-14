package com.upedge.thirdparty.fpx.model;

public class AffterentParam {
    private String method;
    private String appKey;
    private String version;
    private String format;
    private String accessToken;
    private String appSecret;
    private String language;

    public AffterentParam() {
    }

    public AffterentParam(String method, String appKey, String version, String format, String accessToken, String appSecret, String language) {
        this.method = method;
        this.appKey = appKey;
        this.version = version;
        this.format = format;
        this.accessToken = accessToken;
        this.appSecret = appSecret;
        this.language = language;
    }

    public String getMethod() {
        return this.method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getAppKey() {
        return this.appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getFormat() {
        return this.format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getAccessToken() {
        return this.accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getVersion() {
        return this.version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getLanguage() {
        return this.language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getAppSecret() {
        return this.appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    public String toString() {
        return "AffterentParam{method='" + this.method + '\'' + ", appKey='" + this.appKey + '\'' + ", version='" + this.version + '\'' + ", format='" + this.format + '\'' + ", accessToken='" + this.accessToken + '\'' + ", appSecret='" + this.appSecret + '\'' + ", language='" + this.language + '\'' + '}';
    }
}

