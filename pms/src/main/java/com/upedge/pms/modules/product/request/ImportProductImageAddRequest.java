package com.upedge.pms.modules.product.request;

import com.upedge.pms.modules.product.entity.ImportProductImage;
import lombok.Data;

/**
 * @author author
 */
@Data
public class ImportProductImageAddRequest{

                                    /**
             * 图片地址
             */
            private String url;
                                /**
             * 图片次序
             */
            private Integer seq;
                                /**
             * 
             */
            private Long productId;
                                /**
             * 
             */
            private Integer state;
                                /**
             * 
             */
            private String name;
            
    public ImportProductImage toImportProductImage(){
        ImportProductImage importProductImage=new ImportProductImage();
        importProductImage.setUrl(url);
        importProductImage.setSeq(seq);
        importProductImage.setProductId(productId);
        importProductImage.setState(state);
        importProductImage.setName(name);
        return importProductImage;
    }

}
