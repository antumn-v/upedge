package com.upedge.pms.modules.product.vo;

import com.upedge.pms.modules.product.entity.ImportProductVariantAttr;
import lombok.Data;

/**
 * @author 海桐
 */
@Data
public class AppProductVariantAttrVo {

    private Long id;

    private String variantAttrEname;

    private String variantAttrEvalue;

    private Long variantId;

    public ImportProductVariantAttr toImportVariantAttr(Long id){
        ImportProductVariantAttr attr = new ImportProductVariantAttr();
        attr.setAttrName(variantAttrEname);
        attr.setAttrValue(variantAttrEvalue);
        attr.setVariantId(id);
        return attr;
    }

}
