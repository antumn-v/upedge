package com.upedge.pms.modules.product.entity;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * @author gx
 */
@Data
public class ProductAttr{

	/**
	 * 
	 */
    private Long id;
	/**
	 * 
	 */
    private Long productId;
	/**
	 * 
	 */
    private String attrKey;
	/**
	 * 
	 */
    private String attrValue;

}
