package com.upedge.ums.modules.user.vo;

import lombok.Data;

import java.util.Date;

@Data
public class CustomerStoreListVo {

    /**
     *
     */
    private Long id;
    /**
     * 店铺名称
     */
    private String storeName;
    /**
     * 店铺地址
     */
    private String storeUrl;
    /**
     * 店铺启用禁用
     */
    private Integer status = 1;
    /**
     * 0:shopify,1:woocommerce
     */
    private Integer storeType;
    /**
     * 店铺创建时间
     */
    private Date createTime;

    private Long orderCounts;

}
