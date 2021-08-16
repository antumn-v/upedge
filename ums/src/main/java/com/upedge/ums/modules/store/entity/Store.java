package com.upedge.ums.modules.store.entity;

import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * @author gx
 */
@Data
public class Store{

	/**
	 * 
	 */
    private Long id;
	/**
	 * 店铺名称
	 */
    private String storeName;
	/**
	 * 店铺地址
	 */
    private String storeUrl;
	/**
	 * 添加次店铺的用户
	 */
    private Long userId;
	/**
	 * 
	 */
    private Long customerId;
	/**
	 * 店铺对应的组织ID
	 */
    private Long orgId;
	/**
	 * 
	 */
    private String orgPath;
	/**
	 * 店铺货币
	 */
    private String currency;
	/**
	 * 店铺货币美元汇率
	 */
    private BigDecimal usdRate;
	/**
	 * 自定义美元汇率
	 */
    private BigDecimal customerUsdRate;
	/**
	 * 
	 */
    private String apiToken;
	/**
	 * 1 = 启用 0 = 禁用
	 */
    private Integer status;
	/**
	 * 0:shopify,1:woocommerce,2:shoplazza
	 */
    private Integer storeType;
	/**
	 * 
	 */
    private String timezone;
	/**
	 * 店铺创建时间
	 */
    private Date createTime;
	/**
	 * 
	 */
    private Date updateTime;

}
