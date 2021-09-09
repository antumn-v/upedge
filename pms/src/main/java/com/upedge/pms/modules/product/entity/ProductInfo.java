package com.upedge.pms.modules.product.entity;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * @author gx
 */
@Data
public class ProductInfo{

	/**
	 * 
	 */
    private Long id;
	/**
	 * 商品描述
	 */
    private String productDesc;
	/**
	 * 商品id
	 */
    private Long productId;
	/**
	 * 
	 */
    private String cnDesc;

}
