package com.upedge.common.enums;

/**
 * @author 海桐
 */
public enum  StoreSettingEnum {

    /**
     * 订单物流物流信息查询地址
     */
    tracking_url("https://t.17track.net/en#nums="),

    /**
     * 订单履行后是否发送邮件
     * 1=true
     * 0=false
     */
    email_prompt("1");


    String value;

    StoreSettingEnum(String value) {
        this.value = value;
    }


    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
