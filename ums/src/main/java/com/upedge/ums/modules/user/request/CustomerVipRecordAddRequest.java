package com.upedge.ums.modules.user.request;

import com.upedge.common.model.user.vo.Session;
import com.upedge.ums.modules.user.entity.CustomerVipRecord;
import lombok.Data;

import java.util.Date;
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


    public CustomerVipRecord toCustomerVipRecord(Session session){
        CustomerVipRecord customerVipRecord=new CustomerVipRecord();
        customerVipRecord.setCustomerId(customerId);
        customerVipRecord.setVipType(vipType);
        customerVipRecord.setCreateTime(new Date());
        customerVipRecord.setManagerId(session.getId());
        return customerVipRecord;
    }

}
