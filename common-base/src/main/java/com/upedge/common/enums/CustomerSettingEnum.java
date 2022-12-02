package com.upedge.common.enums;

/**
 * @author 海桐
 */

public enum  CustomerSettingEnum {

    /**
     * 上传店铺物流号类型
     * 0=物流商单号
     * 1=真实追踪号
     */
    upload_store_track_code_type("0");

    String value;

    CustomerSettingEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
