package com.upedge.pms.modules.product.request;

import com.upedge.common.utils.IdGenerate;
import com.upedge.pms.modules.product.entity.Product;
import com.upedge.pms.modules.product.entity.ImportProductAttribute;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author 海桐
 */
@Data
public class ImportAddAppProductRequest {

    @NotNull
    Long productId;


    public ImportProductAttribute toImportAttr(Product product){
        ImportProductAttribute attribute = new ImportProductAttribute();
        attribute.setSourceProductId(productId);
        attribute.setId(IdGenerate.nextId());
        attribute.setSource(0);
        attribute.setState(0);
        attribute.setSupplierName("SourcinBox");
        attribute.setImage(product.getProductImage());
        attribute.setTitle(product.getProductTitle());
        attribute.setCreateTime(new Date());
        return attribute;
    }


}
