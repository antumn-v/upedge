package com.upedge.pms.modules.product.entity;

import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * @author gx
 */
@Data
public class Product{

	/**
	 * 
	 */
    private Long id;
	/**
	 * 
	 */
    private String originalId;
	/**
	 * 商品sku
	 */
    private String productSku;
	/**
	 * 原始标题
	 */
    private String originalTitle;
	/**
	 * 商品标题
	 */
    private String productTitle;
	/**
	 * 商品主图
	 */
    private String productImage;
	/**
	 * 供应商id
	 */
    private Long supplierId;
	/**
	 * 运输模板id
	 */
    private Long shippingId;
	/**
	 * 商品类目id
	 */
    private Long categoryId;
	/**
	 * 商品状态（1:编辑中2:下架3: 上架 4:机器上架 ）
	 */
    private Integer state;
	/**
	 * 速卖通替换状态 0未替换 1替换
	 */
    private Integer replaceState;
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
	/**
	 * 导入/创建 人
	 */
    private String userId;
	/**
	 * 创建时间
	 */
    private Date createTime;
	/**
	 * 更新时间
	 */
    private Date updateTime;
	/**
	 * 价格区间
	 */
    private String priceRange;
	/**
	 * 最高价
	 */
    private BigDecimal maxPrice;
	/**
	 * 最低价
	 */
    private BigDecimal minPrice;

}
