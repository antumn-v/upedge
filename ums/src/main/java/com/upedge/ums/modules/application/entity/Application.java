package com.upedge.ums.modules.application.entity;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * @author gx
 */
@Data
public class Application{

	/**
	 * 
	 */
    private Long id;
	/**
	 * 应用名称
	 */
    private String name;
	/**
	 * 
	 */
    private String code;
	/**
	 * 
	 */
    private Date createTime;
	/**
	 * 
	 */
    private Date updateTime;
	/**
	 * 
	 */
    private String url;
	/**
	 * 菜单分组
	 */
    private String menuGroup;

}
