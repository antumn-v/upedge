package com.upedge.ums.modules.user.request;

import com.upedge.ums.modules.user.entity.CustomerVipRebateRecord;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
/**
 * @author gx
 */
@Data
public class CustomerVipRebateRecordUpdateRequest{

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

    public CustomerVipRebateRecord toCustomerVipRebateRecord(Long id){
        CustomerVipRebateRecord customerVipRebateRecord=new CustomerVipRebateRecord();
        customerVipRebateRecord.setId(id);
        customerVipRebateRecord.setCustomerId(customerId);
        customerVipRebateRecord.setAccountId(accountId);
        customerVipRebateRecord.setOrderId(orderId);
        customerVipRebateRecord.setRebate(rebate);
        customerVipRebateRecord.setRebateType(rebateType);
        customerVipRebateRecord.setCreateTime(createTime);
        return customerVipRebateRecord;
    }

}
