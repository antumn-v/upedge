package com.upedge.cms.modules.notice.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author author
 */
@Data
public class AppNotice{

	/**
	 * 
	 */
    private Long id;
	/**
	 * 类型
	 */
    private String type;
	/**
	 * 标题
	 */
    private String title;
	/**
	 * 内容
	 */
    private String content;
	/**
	 * 
	 */
    private Date updateTime;
	/**
	 * 创建时间
	 */
    private Date createTime;
	/**
	 * 查看次数
	 */
    private Integer viewCount;
	/**
	 * 状态 1:启用 0:禁用
	 */
    private Integer state;
	/**
	 * 操作人
	 */
    private String adminUserId;

}
