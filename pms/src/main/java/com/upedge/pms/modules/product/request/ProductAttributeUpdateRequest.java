package com.upedge.pms.modules.product.request;

import com.upedge.pms.modules.product.entity.ProductAttribute;
import java.util.Date;
import lombok.Data;
/**
 * @author gx
 */
@Data
public class ProductAttributeUpdateRequest{

    /**
     * 商品id
     */
    private Long productId;
    /**
     * 1688目录id
     */
    private Long aliCnCategoryId;
    /**
     * 1688目录名称
     */
    private String aliCnCategoryName;
    /**
     * 产品货号
     */
    private String itemNo;
    /**
     * 1688上30天成交量
     */
    private Integer turnover;
    /**
     * 1688上的评分
     */
    private Integer score;
    /**
     * 报关英文名
     */
    private String entryEname;
    /**
     * 报关中文名
     */
    private String entryCname;
    /**
     * 赛盒仓库id
     */
    private Integer warehouseId;

    public ProductAttribute toProductAttribute(Long id){
        ProductAttribute productAttribute=new ProductAttribute();
        productAttribute.setId(id);
        productAttribute.setProductId(productId);
        productAttribute.setAliCnCategoryId(aliCnCategoryId);
        productAttribute.setAliCnCategoryName(aliCnCategoryName);
        productAttribute.setItemNo(itemNo);
        productAttribute.setTurnover(turnover);
        productAttribute.setScore(score);
        productAttribute.setEntryEname(entryEname);
        productAttribute.setEntryCname(entryCname);
        productAttribute.setWarehouseId(warehouseId);
        return productAttribute;
    }

}
