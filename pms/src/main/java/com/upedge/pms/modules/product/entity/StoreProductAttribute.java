package com.upedge.pms.modules.product.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author author
 */
@Data
public class StoreProductAttribute {

    /**
     *
     */
    private Long id;
    /**
     * woocommerce产品ID
     */
    private String platProductId;
    /**
     * 商品店铺标题
     */
    private String title;
    /**
     * 商品店铺图片
     */
    private String image;
    /**
     * 商品店铺供应商
     */
    private String vendor;
    /**
     * 店铺ID
     */
    private Long storeId;

    private Long orgId;

    private String orgPath;
    /**
     *
     */
    private Long adminProductId;
    /**
     * 0:SIB ,1:Ali, 2:其他
     */
    private Integer source;
    /**
     * 0:未关联 1 已关联  2部分关联
     */
    private Integer relateState;
    /**
     * 店铺名称
     */
    private String storeName;
    /**
     * 商品价格
     */
    private String price;
    /**
     * 重名链接
     */
    private String handle;


    private Integer state;
    /**
     *
     */
    private Date createAt;
    /**
     *
     */
    private Date updateAt;
    /**
     * 导入到系统的时间
     */
    private Date importTime;
    /**
     *0:未推送 1:等待中 2:已推送
     */
    private Integer pushState;

    private Long customerId;

    private String managerCode;

    public StoreProductAttribute() {


    }
}
