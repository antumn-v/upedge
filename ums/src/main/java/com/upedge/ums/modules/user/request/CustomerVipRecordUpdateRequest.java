package com.upedge.ums.modules.user.request;

import com.upedge.ums.modules.user.entity.CustomerVipRecord;
import lombok.Data;

import java.util.Date;
/**
 * @author gx
 */
@Data
public class CustomerVipRecordUpdateRequest{

    /**
     * 
     */
    private Long customerId;
    /**
     * 1=授权  0=撤销
     */
    private Integer vipType;
    /**
     * 
     */
    private Date createTime;
    /**
     * 
     */
    private Long managerId;

    public CustomerVipRecord toCustomerVipRecord(Integer id){
        CustomerVipRecord customerVipRecord=new CustomerVipRecord();
        customerVipRecord.setId(id);
        customerVipRecord.setCustomerId(customerId);
        customerVipRecord.setVipType(vipType);
        customerVipRecord.setCreateTime(createTime);
        customerVipRecord.setManagerId(managerId);
        return customerVipRecord;
    }

}
