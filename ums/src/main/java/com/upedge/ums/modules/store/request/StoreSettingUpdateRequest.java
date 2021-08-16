package com.upedge.ums.modules.store.request;

import com.upedge.ums.modules.store.entity.StoreSetting;
import java.util.Date;
import lombok.Data;
/**
 * @author gx
 */
@Data
public class StoreSettingUpdateRequest{

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

    public StoreSetting toStoreSetting(Integer id){
        StoreSetting storeSetting=new StoreSetting();
        storeSetting.setId(id);
        storeSetting.setStoreId(storeId);
        storeSetting.setSettingName(settingName);
        storeSetting.setSettingValue(settingValue);
        return storeSetting;
    }

}
