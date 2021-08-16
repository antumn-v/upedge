package com.upedge.common.enums;

/**
 * @author 海桐
 */

public enum  CustomerSettingEnum {

    /**
     * 是否可以修改订单产品数量
     * 1=true
     * 0=false
     */
    order_product_quantity_modify("0"),

    /**
     * 运输方式排序设置
     * 1=按默认方式排序
     * 0=按运费升序排序
     */
    ship_method_sort_type("1");

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
