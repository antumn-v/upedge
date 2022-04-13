package com.upedge.thirdparty.fpx.constants;

public enum MethodEnum {
    /**
     * 费用试算
     */
    price_calculator("com.css.price_calculator"),
    /**
     * 仓库查询
     */
    get_warehouse("com.basis.warehouse.getlist"),
    /**
     * 运输方式查询
     */
    get_methods("com.basis.logistics_product.getlist"),
    /**
     * 库存查询
     */
    inventory_get("fu.wms.inventory.get"),
    /**
     * 查询库存流水
     */
    inventory_getlog("fu.wms.inventory.getlog"),
    /**
     * 创建sku
     */
    create_sku("fu.wms.sku.create"),
    /**
     * 创建入库委托
     */
    create_inbound("fu.wms.inbound.create")
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
