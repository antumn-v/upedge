package com.upedge.pms.modules.product.vo;

import lombok.Data;

import java.util.Date;

@Data
public class CustomerProductVo {

    /**
     *
     */
    private Long id;
    /**
     * 店铺产品ID
     */
    private Long platProductId;
    /**
     * 商品店铺标题
     */
    private String title;
    /**
     * 商品店铺图片
     */
    private String image;
    /**
     * 店铺ID
     */
    private Long storeId;
    /**
     *
     */
    private Long adminProductId;
    /**
     * 0:SIB ,1:Ali, 2:其他
     */
    private Integer source;
    /**
     * 0:未关联 1 已关联
     */
    private Integer relateState;
    /**
     * 店铺名称
     */
    private String storeName;
    /**
     * 产品链接
     */
    private String handle;
    /**
     * 导入到系统的时间
     */
    private Date importTime;
    /**
     * 关联次数
     */
    private Integer relateNum;
    /**
     *0:未推送 1:等待中 2:已推送
     */
    private Integer pushState;
    /**
     * 商品价格
     */
    private String price;

    private Long customerId;

    private String username;

    /**
     * 近一天销量
     */
    private Integer one;

    /**
     * 近7天销量
     */
    private Integer seven;

    /**
     * 近15天销量
     */
    private Integer fifteen;

    /**
     * 日均销量
     */
    private double dailyAverage;

}
