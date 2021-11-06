package com.upedge.pms.modules.product.request;

import com.upedge.pms.modules.product.entity.ImportProductDescription;
import lombok.Data;

/**
 * @author author
 */
@Data
public class ImportProductDescriptionUpdateRequest{

    /**
     * 产品描述
     */
    private String desc;

    public ImportProductDescription toImportProductDescription(Long id){
        ImportProductDescription importProductDescription=new ImportProductDescription();
        importProductDescription.setId(id);
        importProductDescription.setDescription(desc);
        return importProductDescription;
    }

}
