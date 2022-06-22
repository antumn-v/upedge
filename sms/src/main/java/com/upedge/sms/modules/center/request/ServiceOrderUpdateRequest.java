package com.upedge.sms.modules.center.request;

import com.upedge.sms.modules.center.entity.ServiceOrder;
import lombok.Data;
/**
 * @author gx
 */
@Data
public class ServiceOrderUpdateRequest{

    Long id;

    String serviceTitle;

    String remark;

    public ServiceOrder toServiceOrder(){
        ServiceOrder serviceOrder=new ServiceOrder();
        serviceOrder.setId(id);
        serviceOrder.setServiceTitle(serviceTitle);
        serviceOrder.setRemark(remark);
        return serviceOrder;
    }

}
