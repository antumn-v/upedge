package com.upedge.ums.modules.user.request;

import com.upedge.common.base.Page;
import com.upedge.ums.modules.user.entity.CustomerVipRebateRecord;
import java.util.Date;
import lombok.Data;
import java.math.BigDecimal;
/**
 * @author gx
 */
@Data
public class CustomerVipRebateRecordAddRequest{

    /**
    * 
    */
    private Long customerId;
    /**
    * 
    */
    private Long accountId;
    /**
    * 
    */
    private Long orderId;
    /**
    * 
    */
    private BigDecimal rebate;
    /**
    * 1=增加  0=减少
    */
    private Integer rebateType;
    /**
    * 
    */
    private Date createTime;

    public CustomerVipRebateRecord toCustomerVipRebateRecord(){
        CustomerVipRebateRecord customerVipRebateRecord=new CustomerVipRebateRecord();
        customerVipRebateRecord.setCustomerId(customerId);
        customerVipRebateRecord.setAccountId(accountId);
        customerVipRebateRecord.setOrderId(orderId);
        customerVipRebateRecord.setRebate(rebate);
        customerVipRebateRecord.setRebateType(rebateType);
        customerVipRebateRecord.setCreateTime(createTime);
        return customerVipRebateRecord;
    }

}
