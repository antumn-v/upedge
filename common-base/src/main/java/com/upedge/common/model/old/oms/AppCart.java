package com.upedge.common.model.old.oms;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author xwCui
 */
@Data
public class AppCart{

	/**
	 * 
	 */
    private Long id;
	/**
	 * 
	 */
    private Long userId;
	/**
	 * 产品ID
	 */
    private Long productId;
	/**
	 * 变体ID
	 */
    private Long variantId;
	/**
	 * 数量
	 */
    private Integer quantity;
	/**
	 * 单价
	 */
    private BigDecimal price;
	/**
	 * 正常0，被删除-1，创建订单1
	 */
    private Integer state;
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

}
