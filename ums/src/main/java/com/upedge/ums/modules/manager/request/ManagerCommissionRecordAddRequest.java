package com.upedge.ums.modules.manager.request;

import com.upedge.common.base.Page;
import com.upedge.ums.modules.manager.entity.ManagerCommissionRecord;
import java.util.Date;
import lombok.Data;
import java.math.BigDecimal;
/**
 * @author gx
 */
@Data
public class ManagerCommissionRecordAddRequest{

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

    public ManagerCommissionRecord toManagerCommissionRecord(){
        ManagerCommissionRecord ManagerCommissionRecord=new ManagerCommissionRecord();
        ManagerCommissionRecord.setManagerId(managerId);
        ManagerCommissionRecord.setOrderId(orderId);
        ManagerCommissionRecord.setCommission(commission);
        ManagerCommissionRecord.setCreateTime(createTime);
        return ManagerCommissionRecord;
    }

}
