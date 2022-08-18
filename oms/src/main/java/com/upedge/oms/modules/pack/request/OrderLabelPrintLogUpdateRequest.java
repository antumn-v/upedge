package com.upedge.oms.modules.pack.request;

import com.upedge.oms.modules.pack.entity.OrderLabelPrintLog;
import lombok.Data;

import java.util.Date;
/**
 * @author gx
 */
@Data
public class OrderLabelPrintLogUpdateRequest{

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

    public OrderLabelPrintLog toOrderLabelPrintLog(Long id){
        OrderLabelPrintLog orderLabelPrintLog=new OrderLabelPrintLog();
        orderLabelPrintLog.setId(id);
        orderLabelPrintLog.setOrderId(orderId);
        orderLabelPrintLog.setPackNo(packNo);
        orderLabelPrintLog.setLabelUrl(labelUrl);
        orderLabelPrintLog.setCreateTime(createTime);
        orderLabelPrintLog.setOperatorId(operatorId);
        return orderLabelPrintLog;
    }

}
