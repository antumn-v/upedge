package com.upedge.thirdparty.fpx.constants;

public enum MethodEnum {
    /**
     * 费用试算
     */
    price_calculator("com.css.price_calculator"),
    /**
     * 库存查询
     */
    inventory_get("fu.wms.inventory.get"),
    /**
     * 查询库存流水
     */
    inventory_getlog("fu.wms.inventory.getlog")
    ;

    String method;

    MethodEnum(String method) {
        this.method = method;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }
}
