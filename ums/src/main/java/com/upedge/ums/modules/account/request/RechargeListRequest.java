package com.upedge.ums.modules.account.request;

import com.upedge.common.base.Page;
import com.upedge.ums.modules.account.entity.RechargeLog;
import lombok.Data;


/**
 * @author 海桐
 */
@Data
public class RechargeListRequest extends Page<RechargeLog> {

    private Long customerId;

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }
}
