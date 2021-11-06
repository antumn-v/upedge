package com.upedge.common.model.old.pms;

import lombok.Data;

import java.util.Date;

/**
 * @author xwCui
 */
@Data
public class AdminGroupVariant{

	/**
	 * admin自定义产品子体id
	 */
    private Long groupVariantId;
	/**
	 * admin真实产品子体id
	 */
    private Long adminVariantId;
	/**
	 * 捆绑数量
	 */
    private Integer quantity;
	/**
	 * 
	 */
    private Date createTime;
	/**
	 * 
	 */
    private Date updateTime;

}
