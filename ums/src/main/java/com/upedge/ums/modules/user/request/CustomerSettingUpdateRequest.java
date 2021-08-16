package com.upedge.ums.modules.user.request;

import com.upedge.ums.modules.user.entity.CustomerSetting;
import java.util.Date;
import lombok.Data;
/**
 * @author gx
 */
@Data
public class CustomerSettingUpdateRequest{

    /**
     * 
     */
    private Long customerId;
    /**
     * 
     */
    private String settingName;
    /**
     * 
     */
    private String settingValue;

    public CustomerSetting toCustomerSetting(Integer id){
        CustomerSetting customerSetting=new CustomerSetting();
        customerSetting.setId(id);
        customerSetting.setCustomerId(customerId);
        customerSetting.setSettingName(settingName);
        customerSetting.setSettingValue(settingValue);
        return customerSetting;
    }

}
