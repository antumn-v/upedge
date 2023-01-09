package com.upedge.oms.modules.common.request;

import com.upedge.oms.modules.common.entity.OrderErrorMessage;
import lombok.Data;

import java.util.Date;
/**
 * @author gx
 */
@Data
public class OrderErrorMessageUpdateRequest{

    /**
     * 
     */
    private String errorMessage;
    /**
     * 
     */
    private String errorInfo;
    /**
     * 
     */
    private Date createTime;

    public OrderErrorMessage toOrderErrorMessage(Integer id){
        OrderErrorMessage orderErrorMessage=new OrderErrorMessage();
        orderErrorMessage.setId(id);
        orderErrorMessage.setErrorMessage(errorMessage);
        orderErrorMessage.setErrorInfo(errorInfo);
        orderErrorMessage.setCreateTime(createTime);
        return orderErrorMessage;
    }

}
