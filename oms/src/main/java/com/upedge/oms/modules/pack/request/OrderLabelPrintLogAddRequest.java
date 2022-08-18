package com.upedge.oms.modules.pack.request;

import com.upedge.common.base.Page;
import com.upedge.oms.modules.pack.entity.OrderLabelPrintLog;
import java.util.Date;
import lombok.Data;
/**
 * @author gx
 */
@Data
public class OrderLabelPrintLogAddRequest{

    /**
    * 
    */
    private Long orderId;
    /**
    * 
    */
    private Long packNo;
    /**
    * 
    */
    private String labelUrl;
    /**
    * 
    */
    private Date createTime;
    /**
    * 
    */
    private Long operatorId;

    public OrderLabelPrintLog toOrderLabelPrintLog(){
        OrderLabelPrintLog orderLabelPrintLog=new OrderLabelPrintLog();
        orderLabelPrintLog.setOrderId(orderId);
        orderLabelPrintLog.setPackNo(packNo);
        orderLabelPrintLog.setLabelUrl(labelUrl);
        orderLabelPrintLog.setCreateTime(createTime);
        orderLabelPrintLog.setOperatorId(operatorId);
        return orderLabelPrintLog;
    }

}
