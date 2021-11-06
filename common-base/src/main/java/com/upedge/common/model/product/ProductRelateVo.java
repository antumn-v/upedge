package com.upedge.common.model.product;

import lombok.Data;

import java.util.Date;

/**
 * @author gx
 */
@Data
public class ProductRelateVo {

	/**
	 * 
	 */
    private Long storeVariantId;
	/**
	 * 
	 */
    private Long storeProductId;
	/**
	 * 
	 */
    private Long adminProductId;
	/**
	 * 
	 */
    private Long adminVariantId;
	/**
	 * 
	 */
    private Integer scale;
	/**
	 * 0=普通产品，1=捆绑产品
	 */
    private Integer variantType;
	/**
	 * 
	 */
    private Date createTime;
	/**
	 * 
	 */
    private Date updateTime;

}
