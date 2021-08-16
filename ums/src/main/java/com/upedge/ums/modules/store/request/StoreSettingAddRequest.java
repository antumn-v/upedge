package com.upedge.ums.modules.store.request;

import com.upedge.common.base.Page;
import com.upedge.ums.modules.store.entity.StoreSetting;
import java.util.Date;
import lombok.Data;
/**
 * @author gx
 */
@Data
public class StoreSettingAddRequest{

    /**
    * 
    */
    private Long storeId;
    /**
    * 
    */
    private String settingName;
    /**
    * 
    */
    private String settingValue;

    public StoreSetting toStoreSetting(){
        StoreSetting storeSetting=new StoreSetting();
        storeSetting.setStoreId(storeId);
        storeSetting.setSettingName(settingName);
        storeSetting.setSettingValue(settingValue);
        return storeSetting;
    }

}
