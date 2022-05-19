package com.upedge.pms.modules.product.request;

import com.upedge.common.base.Page;
import com.upedge.pms.modules.product.entity.StoreCustomVariantRecord;
import java.util.Date;
import lombok.Data;
/**
 * @author gx
 */
@Data
public class StoreCustomVariantRecordAddRequest{

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

    public StoreCustomVariantRecord toStoreCustomVariantRecord(){
        StoreCustomVariantRecord storeCustomVariantRecord=new StoreCustomVariantRecord();
        storeCustomVariantRecord.setSku(sku);
        storeCustomVariantRecord.setSellingLink(sellingLink);
        storeCustomVariantRecord.setStoreVariantId(storeVariantId);
        storeCustomVariantRecord.setCreatorId(creatorId);
        storeCustomVariantRecord.setCreateTime(createTime);
        return storeCustomVariantRecord;
    }

}
