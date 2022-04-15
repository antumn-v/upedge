package com.upedge.sms.modules.overseaWarehouse.request;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class OverseaWarehouseServiceOrderItemUploadFpxRequest {

    @NotNull
    Long itemId;
    //是否是箱装
    boolean ifBox;
    //每箱个数
    Integer pcs;
    //包装属性
    @NotNull
    String wrapping;
    //商品外观，包装属性为H时此字段必填
    String appearance;
    //是否自带物流包装
    @NotNull
    String logisticsPackage;
    //包装材质
    @NotNull
    String packageMaterial;
    //重量
    @NotNull
    BigDecimal weight;
    //长
    @NotNull
    BigDecimal length;
    //宽
    @NotNull
    BigDecimal width;
    //高
    @NotNull
    BigDecimal height;

}
