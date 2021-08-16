package com.upedge.ums.modules.store.request;

import com.upedge.common.base.Page;
import com.upedge.ums.modules.store.entity.StoreAttr;
import java.util.Date;
import lombok.Data;
/**
 * @author gx
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
