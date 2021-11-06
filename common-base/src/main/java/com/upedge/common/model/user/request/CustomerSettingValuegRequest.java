package com.upedge.common.model.user.request;

import lombok.Data;

/**
 * @author 海桐
 */
@Data
public class CustomerSettingValuegRequest {

    Long customerId;

    String settingName;

    public CustomerSettingValuegRequest() {
    }
}
