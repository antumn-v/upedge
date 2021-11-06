package com.upedge.pms.modules.product.request;

import com.upedge.pms.modules.product.entity.ImportProductVariantAttr;
import lombok.Data;

/**
 * @author author
 */
@Data
public class ImportProductVariantAttrUpdateRequest{

    /**
     * 
     */
    private Long variantId;
    /**
     * 
     */
    private String arrtName;
    /**
     * 
     */
    private String attrValue;
    /**
     * 
     */
    private Integer seq;

    public ImportProductVariantAttr toImportProductVariantAttr(Long id){
        ImportProductVariantAttr importProductVariantAttr=new ImportProductVariantAttr();
        importProductVariantAttr.setId(id);
        importProductVariantAttr.setVariantId(variantId);
        importProductVariantAttr.setAttrName(arrtName);
        importProductVariantAttr.setAttrValue(attrValue);
        importProductVariantAttr.setSeq(seq);
        return importProductVariantAttr;
    }

}
