package com.upedge.ums.modules.application.entity;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * @author gx
 */
@Data
public class Menu{

	/**
	 * 
	 */
    private Long id;
	/**
	 * 应用id
	 */
    private Long applicationId;
	/**
	 * 
	 */
    private String title;
	/**
	 * 
	 */
    private String name;
	/**
	 * 
	 */
    private String url;
	/**
	 * 
	 */
    private Long parentId;
	/**
	 * 
	 */
    private String menuPath;
	/**
	 * 
	 */
    private Integer seq;
	/**
	 * 
	 */
    private Date updateTime;
	/**
	 * 
	 */
    private Date createTime;
	/**
	 * 1:普通用户 2:管理员 3:超级管理员
	 */
    private Integer menuType;
	/**
	 * 菜单分组
	 */
    private String menuGroup;
	/**
	 * 权限名
	 */
    private String permissionName;
	/**
	 * 0=页面权限，1=按钮权限
	 */
    private Integer permissionType;
	/**
	 * 0=禁用 1=启用
	 */
    private Integer state;
	/**
	 * 
	 */
    private Boolean createPerm;

}
