package com.upedge.common.model.cart.request;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class CartVo {
    private Long id;
    /**
     *
     */
    private Long customerId;
    /**
     * 产品ID
     */
    private Long productId;
    /**
     *
     */
    private String productTitle;
    /**
     * 变体ID
     */
    private Long variantId;
    /**
     *
     */
    private String variantName;
    /**
     *
     */
    private String variantSku;
    /**
     *
     */
    private String variantImage;

    private BigDecimal variantWeight;

    private BigDecimal variantVolume;
    /**
     * 数量
     */
    private Integer quantity;
    /**
     * 单价
     */
    private BigDecimal price;
    /**
     * 正常0，被删除2，创建订单1
     */
    private Integer state = 0;
    /**
     *
     */
    private Date createTime;
    /**
     *
     */
    private Date updateTime;
    /**
     * 备库=0，批发=1
     */
    private Integer cartType;

    private Long markId;

}
