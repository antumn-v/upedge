package com.upedge.ums.modules.manager.request;

import com.upedge.ums.modules.manager.entity.ManagerCommissionRecord;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
/**
 * @author gx
 */
@Data
public class ManagerCommissionRecordUpdateRequest{

    /**
     * 
     */
    private Long managerId;
    /**
     * 
     */
    private Long orderId;
    /**
     * 
     */
    private BigDecimal commission;
    /**
     * 
     */
    private Date createTime;

    public ManagerCommissionRecord toManagerCommissionRecord(Long id){
        ManagerCommissionRecord ManagerCommissionRecord=new ManagerCommissionRecord();
        ManagerCommissionRecord.setId(id);
        ManagerCommissionRecord.setManagerId(managerId);
        ManagerCommissionRecord.setOrderId(orderId);
        ManagerCommissionRecord.setCommission(commission);
        ManagerCommissionRecord.setCreateTime(createTime);
        return ManagerCommissionRecord;
    }

}
