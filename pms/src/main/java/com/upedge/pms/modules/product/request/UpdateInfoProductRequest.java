package com.upedge.pms.modules.product.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class UpdateInfoProductRequest {
    /**
     * 商品标题
     */
    @NotBlank
    private String productTitle;
    /**
     * 商品类目id
     */
    private String categoryCode;
    /**
     * 0:普通商品 1:定制包装
     */
    @NotNull
    private Integer cateType;
    /**
     * 报关英文名
     */
    @NotBlank
    private String entryEname;
    /**
     * 报关中文名
     */
    @NotBlank
    private String entryCname;
    /**
     * 赛盒仓库id
     */
    @NotNull
    private Integer warehouseCode;
    /**
     * 运输模板id
     */
    @NotNull
    private Long shippingId;

}
