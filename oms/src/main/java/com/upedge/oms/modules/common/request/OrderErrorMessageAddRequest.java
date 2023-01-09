package com.upedge.oms.modules.common.request;

import com.upedge.oms.modules.common.entity.OrderErrorMessage;
import lombok.Data;

import java.util.Date;
/**
 * @author gx
 */
@Data
public class OrderErrorMessageAddRequest{

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

    public OrderErrorMessage toOrderErrorMessage(){
        OrderErrorMessage orderErrorMessage=new OrderErrorMessage();
        orderErrorMessage.setErrorMessage(errorMessage);
        orderErrorMessage.setErrorInfo(errorInfo);
        orderErrorMessage.setCreateTime(new Date());
        return orderErrorMessage;
    }

}
