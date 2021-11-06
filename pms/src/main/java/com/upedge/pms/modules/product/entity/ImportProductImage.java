package com.upedge.pms.modules.product.entity;

import lombok.Data;

/**
 * @author author
 */
@Data
public class ImportProductImage {

    /**
     *
     */
    private Long id;
    /**
     * 图片地址
     */
    private String url;
    /**
     * 图片次序
     */
    private Integer seq;
    /**
     *
     */
    private Long productId;
    /**
     *
     */
    private Integer state;
    /**
     *
     */
    private String name;



    public ImportProductImage() {
    }
}
