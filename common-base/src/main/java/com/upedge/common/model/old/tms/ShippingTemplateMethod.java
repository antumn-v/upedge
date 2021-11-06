package com.upedge.common.model.old.tms;

import lombok.Data;

/**
 * @author xwCui
 */
@Data
public class ShippingTemplateMethod{

	/**
	 * 
	 */
    private Long id;
	/**
	 * 运输模板id
	 */
    private Long shippingId;
	/**
	 * 运输方式id
	 */
    private Long methodId;
	/**
	 * 
	 */
    private Integer sort;

}
