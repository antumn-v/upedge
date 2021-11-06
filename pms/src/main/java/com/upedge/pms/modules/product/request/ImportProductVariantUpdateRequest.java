package com.upedge.pms.modules.product.request;

import com.upedge.pms.modules.product.entity.ImportProductVariant;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author author
 */
@Data
public class ImportProductVariantUpdateRequest{



    /**
     * 原价
     */
    private BigDecimal price;
    /**
     * 现价
     */
    private BigDecimal comparePrice;
    /**
     * 库存
     */
    private Long inventory;
    /**
     * 产品重量
     */
    private BigDecimal weight;

    private Integer state;

    public ImportProductVariant toImportProductVariant(Long id){
        ImportProductVariant importProductVariant=new ImportProductVariant();
        importProductVariant.setId(id);
        importProductVariant.setPrice(price);
        importProductVariant.setComparePrice(comparePrice);
        importProductVariant.setInventory(inventory);
        importProductVariant.setWeight(weight);
        importProductVariant.setState(state);
        return importProductVariant;
    }

}
