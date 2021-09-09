package com.upedge.pms.modules.product.entity;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * @author gx
 */
@Data
public class CustomerPrivateProduct{

	/**
	 * 
	 */
    private Long id;
	/**
	 * 产品ID
	 */
    private Long productId;
	/**
	 * 用户ID
	 */
    private Long customerId;

}
