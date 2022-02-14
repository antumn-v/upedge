package com.upedge.ums.enums;

public enum CustomerSettingEnum {

    DEFAULT_WAREHOUSE("default_warehouse","CNHZ"),
    ;

    String settingName;

    String defaultValue;

    CustomerSettingEnum(String settingName, String defaultValue) {
        this.settingName = settingName;
        this.defaultValue = defaultValue;
    }
}
