package com.upedge.ums.modules.manager.request;

import com.upedge.common.base.Page;
import com.upedge.ums.modules.manager.entity.ManagerComissionRecord;
import java.util.Date;
import lombok.Data;
import java.math.BigDecimal;
/**
 * @author gx
 */
@Data
public class ManagerComissionRecordAddRequest{

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

    public ManagerComissionRecord toManagerComissionRecord(){
        ManagerComissionRecord managerComissionRecord=new ManagerComissionRecord();
        managerComissionRecord.setManagerId(managerId);
        managerComissionRecord.setOrderId(orderId);
        managerComissionRecord.setCommission(commission);
        managerComissionRecord.setCreateTime(createTime);
        return managerComissionRecord;
    }

}