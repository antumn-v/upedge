package com.upedge.common.model.old.pms;

import lombok.Data;

import java.util.Date;

/**
 * @author xwCui
 */
@Data
public class ProductSourcing{

	/**
	 * 
	 */
    private Long id;
	/**
	 * 
	 */
    private Long userId;
	/**
	 * 
	 */
    private Long sourcingProductId;
	/**
	 * 
	 */
    private String sourcingUrl;
	/**
	 * 
	 */
    private String title;
	/**
	 * 
	 */
    private String image;
	/**
	 * 0=processing,1=success,2=failed
	 */
    private Integer state;
	/**
	 * 0=aliProduct,1=storeProduct,2=others
	 */
    private Integer sourcingType;
	/**
	 * 
	 */
    private Date createTime;
	/**
	 * 
	 */
    private Date updateTime;
	/**
	 * 处理人
	 */
    private String adminUserCode;
	/**
	 * 1=正常，0=已删除
	 */
    private Integer sourcingStatus;
	/**
	 * 1-10 等级越高越靠前
	 */
    private Integer rank;

}
