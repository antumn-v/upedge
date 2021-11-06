package com.upedge.common.model.old.pms;

import lombok.Data;

/**
 * @author xwCui
 */
@Data
public class AdminProductTag{

	/**
	 * 1:启用/0:禁用
	 */
    private Long id;
	/**
	 * 
	 */
    private Long productId;
	/**
	 * 标签
	 */
    private String tag;
	/**
	 * 1:启用/0:禁用
	 */
    private Integer state;

}
