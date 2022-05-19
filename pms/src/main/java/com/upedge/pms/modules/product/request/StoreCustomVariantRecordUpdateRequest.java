package com.upedge.pms.modules.product.request;

import com.upedge.pms.modules.product.entity.StoreCustomVariantRecord;
import lombok.Data;

import java.util.Date;
/**
 * @author gx
 */
@Data
public class StoreCustomVariantRecordUpdateRequest{

    /**
     * 
     */
    private String sku;
    /**
     * 
     */
    private String sellingLink;
    /**
     * 
     */
    private Long storeVariantId;
    /**
     * 
     */
    private Long creatorId;
    /**
     * 
     */
    private Date createTime;

    public StoreCustomVariantRecord toStoreCustomVariantRecord(Long id){
        StoreCustomVariantRecord storeCustomVariantRecord=new StoreCustomVariantRecord();
        storeCustomVariantRecord.setSku(sku);
        storeCustomVariantRecord.setSellingLink(sellingLink);
        storeCustomVariantRecord.setStoreVariantId(storeVariantId);
        storeCustomVariantRecord.setCreatorId(creatorId);
        storeCustomVariantRecord.setCreateTime(createTime);
        return storeCustomVariantRecord;
    }

}
