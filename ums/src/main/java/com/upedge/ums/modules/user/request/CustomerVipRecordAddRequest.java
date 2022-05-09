package com.upedge.ums.modules.user.request;

import com.upedge.common.base.Page;
import com.upedge.ums.modules.user.entity.CustomerVipRecord;
import java.util.Date;
import lombok.Data;
/**
 * @author gx
 */
@Data
public class CustomerVipRecordAddRequest{

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

    public CustomerVipRecord toCustomerVipRecord(){
        CustomerVipRecord customerVipRecord=new CustomerVipRecord();
        customerVipRecord.setCustomerId(customerId);
        customerVipRecord.setVipType(vipType);
        customerVipRecord.setCreateTime(createTime);
        customerVipRecord.setManagerId(managerId);
        return customerVipRecord;
    }

}
