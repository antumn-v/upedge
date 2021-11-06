package com.upedge.pms.modules.product.vo;

import lombok.Data;

import java.util.Date;

@Data
public class CustomerProductVariantRelateVo {

    /**
     * 变体ID
     */
    private Long id;
    /**
     * 店铺平台变体ID
     */
    private Long platVariantId;
    /**
     * 店铺平台产品ID
     */
    private Long platProductId;
    /**
     *
     */
    private String title;
    /**
     *
     */
    private String sku;
    /**
     * 图片
     */
    private String image;

    private Integer state;

    private Date importTime;

    private Long adminProductId;

    private String adminVariantSku;

    private String adminVariantId;

    private Integer variantType;

    private Integer scale;



}
