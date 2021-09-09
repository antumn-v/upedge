package com.upedge.pms.modules.product.entity;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * @author gx
 */
@Data
public class ProductVariantAttr{

	/**
	 * 
	 */
    private Long id;
	/**
	 * 中文名称
	 */
    private String variantAttrCname;
	/**
	 * 
	 */
    private String variantAttrEname;
	/**
	 * 
	 */
    private String originalAttrCvalue;
	/**
	 * 中文值
	 */
    private String variantAttrCvalue;
	/**
	 * 
	 */
    private String variantAttrEvalue;
	/**
	 * 
	 */
    private Long variantId;
	/**
	 * 
	 */
    private Long productId;
	/**
	 * 
	 */
    private Integer seq;

}
