package com.upedge.ums.modules.store.request;

import com.upedge.ums.modules.store.entity.StoreAttr;
import lombok.Data;

/**
 * @author author
 */
@Data
public class StoreAttrUpdateRequest{

    /**
     * 
     */
    private Long storeId;
    /**
     * 
     */
    private String attrKey;
    /**
     * 
     */
    private String attrValue;

    public StoreAttr toStoreAttr(Integer id){
        StoreAttr storeAttr=new StoreAttr();
        storeAttr.setId(id);
        storeAttr.setStoreId(storeId);
        storeAttr.setAttrKey(attrKey);
        storeAttr.setAttrValue(attrValue);
        return storeAttr;
    }

}
