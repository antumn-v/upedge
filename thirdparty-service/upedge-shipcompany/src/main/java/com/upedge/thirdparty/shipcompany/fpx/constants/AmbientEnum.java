package com.upedge.thirdparty.shipcompany.fpx.constants;

public enum AmbientEnum {
    SANDBOX_ADDRESS("sandbox"),
    FORMAT_ADDRESS("format");

    private String evncValue;

    private AmbientEnum(String evncValue) {
        this.evncValue = evncValue;
    }
}
