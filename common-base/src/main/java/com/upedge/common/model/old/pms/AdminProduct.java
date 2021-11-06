package com.upedge.common.model.old.pms;

import lombok.Data;

import java.util.Date;

/**
 * @author xwCui
 */
@Data
public class AdminProduct{

	/**
	 * 
	 */
    private Long id;
	/**
	 * 原始商品id
	 */
    private String originalId;
	/**
	 * 供应商id
	 */
    private Long supplierId;
	/**
	 * 商品状态（1:编辑中2:下架3: 上架 4:机器上架  -1:机器下架）
	 */
    private Integer state;
	/**
	 * 创建时间
	 */
    private Date createTime;
	/**
	 * 处理人
	 */
    private String userId;
	/**
	 * 更新时间
	 */
    private Date updateTime;
	/**
	 * 速卖通替换状态 0未替换 1替换
	 */
    private Integer replaceState;
	/**
	 * 速卖通替换链接
	 */
    private String replaceUrl;
	/**
	 * 赛盒上传状态 0:未上传 1:已上传
	 */
    private Integer saiheState;
	/**
	 * 0:1688 1:个人添加 2:复制产品3:捆绑产品
	 */
    private Integer productSource;
	/**
	 * 0:公有产品 1:私有产品
	 */
    private Integer productType;
	/**
	 * 0:普通商品 1:定制包装
	 */
    private Integer cateType;

}
