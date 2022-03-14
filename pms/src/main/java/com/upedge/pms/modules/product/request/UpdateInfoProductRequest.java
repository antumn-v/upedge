package com.upedge.pms.modules.product.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class UpdateInfoProductRequest {


    private String productSku;

    @NotNull(message = "产品图片不能为空")
    private String productImage;
    /**
     * 商品标题
     */
    @NotBlank(message = "产品标题不能为空")
    private String productTitle;
    /**
     * 商品类目id
     */
    private String categoryCode;
    /**
     * 0:普通商品 1:定制包装
     */
    @NotNull(message = "商品类别不能为空")
    private Integer cateType;
    /**
     * 报关英文名
     */
    @NotBlank(message = "报关英文名不能为空")
    private String entryEname;
    /**
     * 报关中文名
     */
    @NotBlank(message = "报关中文名不能为空")
    private String entryCname;
    /**
     * 赛盒仓库id
     */
    private Integer warehouseCode;
    /**
     * 运输模板id
     */
    @NotNull(message = "运输模板不能为空")
    private Long shippingId;

}
