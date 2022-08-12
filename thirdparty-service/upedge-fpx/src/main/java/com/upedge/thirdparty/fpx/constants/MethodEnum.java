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
    create_inbound("fu.wms.inbound.create"),
    /**
     * 查询入库委托
     */
    select_inbound("fu.wms.inbound.getlist"),
    /**
     * 查询计量单位
     */
    measure_unit_list("com.basis.measureunit.getlist"),
    /**
     * 创建直发委托单
     */
    order_create("ds.xms.order.create"),
    /**
     * 单个包裹打印面单
     */
    single_package_label("ds.xms.label.get"),
    /**
     * 多个包裹打印面单
     */
    multi_package_label("ds.xms.label.getlist")
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
