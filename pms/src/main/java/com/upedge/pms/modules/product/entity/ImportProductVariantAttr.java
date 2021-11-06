package com.upedge.pms.modules.product.entity;

import lombok.Data;

/**
 * @author author
 */
@Data
public class ImportProductVariantAttr {

    /**
     *
     */
    private Long id;
    /**
     *
     */
    private Long variantId;

    private Long productId;
    /**
     *
     */
    private String attrName;
    /**
     *
     */
    private String attrValue;
    /**
     *
     */
    private Integer seq;

}
