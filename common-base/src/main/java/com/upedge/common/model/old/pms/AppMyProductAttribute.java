package com.upedge.common.model.old.pms;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author xwCui
 */
@Data
public class AppMyProductAttribute{

	/**
	 * 
	 */
    private Long id;
	/**
	 * woocommerce产品ID
	 */
    private Long originalProductId;
	/**
	 * 商品店铺标题
	 */
    private String title;
	/**
	 * 商品店铺图片
	 */
    private String imageSrc;
	/**
	 * 商品店铺供应商
	 */
    private String vendor;
	/**
	 * 店铺ID
	 */
    private Long storeId;
	/**
	 * 
	 */
    private Long adminProductId;
	/**
	 * 0:SIB ,1:Ali, 2:其他
	 */
    private Integer source;
	/**
	 * 0:未关联 1 已关联
	 */
    private Integer relateState;
	/**
	 * 店铺名称
	 */
    private String storeName;
	/**
	 * 商品价格
	 */
    private BigDecimal price;
	/**
	 * 重名链接
	 */
    private String handle;
	/**
	 * 用户ID
	 */
    private Long userId;
	/**
	 * 0,从本地删除，1：shopift和本地都删除，2：在shopif中进行的删除操作
	 */
    private Integer productState;
	/**
	 * 
	 */
    private Date createAt;
	/**
	 * 
	 */
    private Date updateAt;
	/**
	 * 0:正常 1:等待中
	 */
    private Integer waitFlag;
	/**
	 * 0:shopify,1:woocommerce
	 */
    private Integer storeType;

}
