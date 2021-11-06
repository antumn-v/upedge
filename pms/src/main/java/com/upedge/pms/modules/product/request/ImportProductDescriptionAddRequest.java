package com.upedge.pms.modules.product.request;

import com.upedge.pms.modules.product.entity.ImportProductDescription;
import lombok.Data;

/**
 * @author author
 */
@Data
public class ImportProductDescriptionAddRequest{

                                    /**
             * 
             */
            private Long productId;
                                /**
             * 产品描述
             */
            private String desc;
            
    public ImportProductDescription toImportProductDescription(){
        ImportProductDescription importProductDescription=new ImportProductDescription();
        importProductDescription.setProductId(productId);
        importProductDescription.setDescription(desc);
        return importProductDescription;
    }

}
