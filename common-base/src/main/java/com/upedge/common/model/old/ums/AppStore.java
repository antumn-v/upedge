package com.upedge.common.model.old.ums;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author xwCui
 */
@Data
public class AppStore{

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
	 * 店铺标签
	 */
    private String storeTags;
	/**
	 * 店铺创建时间
	 */
    private Date createTime;
	/**
	 * 访问令牌
	 */
    private String accessToken;
	/**
	 * 店铺地址ID
	 */
    private Long locationId;
	/**
	 * 店铺对应的用户ID
	 */
    private Long userId;
	/**
	 * 
	 */
    private String countryCode;
	/**
	 * 
	 */
    private String currency;
	/**
	 * 店铺启用禁用
	 */
    private Integer storeState;
	/**
	 * 
	 */
    private Integer webhookState;
	/**
	 * 
	 */
    private Date updateTime;
	/**
	 * 
	 */
    private String woocKey;
	/**
	 * 
	 */
    private String woocSecret;
	/**
	 * 0:shopify,1:woocommerce
	 */
    private Integer source;
	/**
	 * 
	 */
    private String timezone;
	/**
	 * 0=app关联，1=shopify关联,2手动关联
	 */
    private Integer relateType;

    BigDecimal currencyRate;

    Boolean isRateCustomized;

}
