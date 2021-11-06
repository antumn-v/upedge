package com.upedge.pms.modules.product.request;

import com.upedge.pms.modules.product.entity.ImportProductImage;
import lombok.Data;

/**
 * @author author
 */
@Data
public class ImportProductImageUpdateRequest{

    /**
     * 图片次序
     */
    private Integer seq;
    /**
     * 
     */
    private Integer state;
    /**
     * 
     */
    private String name;

    public ImportProductImage toImportProductImage(Long id){
        ImportProductImage importProductImage=new ImportProductImage();
        importProductImage.setId(id);
        importProductImage.setSeq(seq);
        importProductImage.setState(state);
        importProductImage.setName(name);
        return importProductImage;
    }

}
