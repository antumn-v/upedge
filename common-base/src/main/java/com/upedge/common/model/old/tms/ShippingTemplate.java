package com.upedge.common.model.old.tms;

import lombok.Data;

import java.util.Date;

/**
 * @author xwCui
 */
@Data
public class ShippingTemplate{

	/**
	 * 
	 */
    private Long id;
	/**
	 * 模板名称
	 */
    private String name;
	/**
	 * 描述
	 */
    private String desc;
	/**
	 * 1:启用 2:删除
	 */
    private Integer status;
	/**
	 * 创建时间
	 */
    private Date createTime;

}
