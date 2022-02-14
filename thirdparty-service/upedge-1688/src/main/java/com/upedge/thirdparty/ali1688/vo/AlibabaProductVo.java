package com.upedge.thirdparty.ali1688.vo;

import lombok.Data;

import java.util.List;

/**
 * @author author
 */
@Data
public class AlibabaProductVo{

	/**
	*
	*/
	private Long id;
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
	 * 属性信息
	 */
	private com.upedge.thirdparty.ali1688.vo.ProductAttributeVo productAttributeVo;

	/**
	 * 产品描述
	 */
	private com.upedge.thirdparty.ali1688.vo.ProductInfoVo productInfoVo;

	/**
	 * 产品供应商
	 */
	private com.upedge.thirdparty.ali1688.vo.SupplierVo supplierVo;

	/**
	 * 产品图片列表
	 */
	private List<com.upedge.thirdparty.ali1688.vo.ProductImgVo> productImgVoList;

	/**
	 * 产品变体列表
	 */
	private List<com.upedge.thirdparty.ali1688.vo.ProductVariantVo> productVariantVoList;



}
