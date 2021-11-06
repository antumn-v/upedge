package com.upedge.ums.modules.store.request;

import com.upedge.ums.modules.store.entity.StoreAttr;
import lombok.Data;

/**
 * @author author
 */
@Data
public class StoreAttrAddRequest{

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
            
    public StoreAttr toStoreAttr(){
        StoreAttr storeAttr=new StoreAttr();
        storeAttr.setStoreId(storeId);
        storeAttr.setAttrKey(attrKey);
        storeAttr.setAttrValue(attrValue);
        return storeAttr;
    }

}
