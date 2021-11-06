package com.upedge.common.model.old.pms;

import lombok.Data;

/**
 * @author xwCui
 */
@Data
public class AppProductImage{

	/**
	 * 
	 */
    private Long appImageId;
	/**
	 * 图片地址
	 */
    private String imageUrl;
	/**
	 * 图片次序
	 */
    private Integer imageSeq;
	/**
	 * 
	 */
    private Long productId;
	/**
	 * 
	 */
    private Integer useFlag;
	/**
	 * 
	 */
    private String variantSku;
	/**
	 * 
	 */
    private String imageName;

}
