package com.upedge.common.model.old.pms;

import lombok.Data;

import java.util.Date;

/**
 * @author xwCui
 */
@Data
public class AdminProductData{

	/**
	 * 
	 */
    private Long id;
	/**
	 * 商品id
	 */
    private Long productId;
	/**
	 * 点击数
	 */
    private Integer clickNum;
	/**
	 * 导入数
	 */
    private Integer importNum;
	/**
	 * 
	 */
    private Integer pageViews;
	/**
	 * 下单数
	 */
    private Integer orderNum;
	/**
	 * 
	 */
    private Date updateTime;

}
