package com.upedge.pms.modules.product.entity;

import lombok.Data;

/**
 * @author gx
 */
@Data
public class CustomerPrivateProduct{

	/**
	 * 产品ID
	 */
    private Long productId;
	/**
	 * 用户ID
	 */
    private Long customerId;

}
