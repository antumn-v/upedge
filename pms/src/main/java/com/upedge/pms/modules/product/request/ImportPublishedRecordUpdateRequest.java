package com.upedge.pms.modules.product.request;

import com.upedge.pms.modules.product.entity.ImportPublishedRecord;
import java.util.Date;
import lombok.Data;
/**
 * @author gx
 */
@Data
public class ImportPublishedRecordUpdateRequest{

    /**
     * 
     */
    private Long stroreId;
    /**
     * 
     */
    private String platProductId;
    /**
     * 
     */
    private Date publishTime;

    public ImportPublishedRecord toImportPublishedRecord(Long id){
        ImportPublishedRecord importPublishedRecord=new ImportPublishedRecord();
        importPublishedRecord.setStroreId(stroreId);
        importPublishedRecord.setPlatProductId(platProductId);
        importPublishedRecord.setPublishTime(publishTime);
        return importPublishedRecord;
    }

}
