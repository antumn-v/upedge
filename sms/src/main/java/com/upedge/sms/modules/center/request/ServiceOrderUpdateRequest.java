package com.upedge.sms.modules.center.request;

import com.upedge.sms.modules.center.entity.ServiceOrder;
import java.util.Date;
import lombok.Data;
import java.math.BigDecimal;
/**
 * @author gx
 */
@Data
public class ServiceOrderUpdateRequest{

    /**
     * 
     */
    private Integer serviceType;
    /**
     * 
     */
    private Long customerId;
    /**
     * 
     */
    private Integer serviceState;
    /**
     * 
     */
    private Integer payState;
    /**
     * 
     */
    private BigDecimal payAmount;
    /**
     * 
     */
    private Date createTime;
    /**
     * 
     */
    private Date updateTime;
    /**
     * 
     */
    private Date finishTime;
    /**
     * 
     */
    private Long managerId;

    public ServiceOrder toServiceOrder(Long id){
        ServiceOrder serviceOrder=new ServiceOrder();
        serviceOrder.setId(id);
        serviceOrder.setServiceType(serviceType);
        serviceOrder.setCustomerId(customerId);
        serviceOrder.setServiceState(serviceState);
        serviceOrder.setPayState(payState);
        serviceOrder.setPayAmount(payAmount);
        serviceOrder.setCreateTime(createTime);
        serviceOrder.setUpdateTime(updateTime);
        serviceOrder.setFinishTime(finishTime);
        serviceOrder.setManagerId(managerId);
        return serviceOrder;
    }

}
