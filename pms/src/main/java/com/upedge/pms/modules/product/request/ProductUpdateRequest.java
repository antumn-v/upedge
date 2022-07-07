package com.upedge.pms.modules.product.request;

import com.upedge.pms.modules.product.entity.Product;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
/**
 * @author gx
 */
@Data
public class ProductUpdateRequest{

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
    private String supplierName;
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
    private Long userId;
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

    private String remark;

    public Product toProduct(Long id){
        Product product=new Product();
        product.setId(id);
        product.setOriginalId(originalId);
        product.setProductSku(productSku);
        product.setOriginalTitle(originalTitle);
        product.setProductTitle(productTitle);
        product.setProductImage(productImage);
        product.setSupplierName(supplierName);
        product.setShippingId(shippingId);
        product.setCategoryId(categoryId);
        product.setState(state);
        product.setReplaceState(replaceState);
        product.setSaiheState(saiheState);
        product.setProductSource(productSource);
        product.setProductType(productType);
        product.setCateType(cateType);
        product.setUserId(userId);
        product.setCreateTime(createTime);
        product.setUpdateTime(updateTime);
        product.setPriceRange(priceRange);
        product.setRemark(remark);
        return product;
    }

}
