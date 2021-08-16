package com.upedge.ums.modules.user.request;

import com.upedge.common.base.Page;
import com.upedge.ums.modules.user.entity.CustomerSetting;
import java.util.Date;
import lombok.Data;
/**
 * @author gx
 */
@Data
public class CustomerSettingAddRequest{

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

    public CustomerSetting toCustomerSetting(){
        CustomerSetting customerSetting=new CustomerSetting();
        customerSetting.setCustomerId(customerId);
        customerSetting.setSettingName(settingName);
        customerSetting.setSettingValue(settingValue);
        return customerSetting;
    }

}
