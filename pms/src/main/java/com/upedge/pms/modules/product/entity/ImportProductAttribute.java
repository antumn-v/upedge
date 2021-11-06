package com.upedge.pms.modules.product.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author author
 */
@Data
public class ImportProductAttribute {

    /**
     *
     */
    private Long id;
    /**
     * 原商品ID
     */
    private Long sourceProductId;
    /**
     * 商品名称
     */
    private String title;
    /**
     * 产品类别
     */
    private String type;
    /**
     * 产品在product选项卡展示的图片
     */
    private String image;
    /**
     * 产品状态（已上架，未上架）
     */
    private Integer state;
    /**
     * 产品标签
     */
    private String tags;
    /**
     * 商品上架店铺后的ID
     */
    private String platProductId;
    /**
     *
     */
    private Long collectId;
    /**
     * 产品来源,0=app,1=ali
     */
    private Integer source;
    /**
     * 原标题
     */
    private String originalTitle;
    /**
     *
     */
    private Date createTime;
    /**
     *
     */
    private Date updateTime;
    /**
     *
     */
    private Long userId;
    /**
     *
     */
    private Long storeId;

    private String supplierName;

    private Long customerId;



    public ImportProductAttribute() {
    }
}
