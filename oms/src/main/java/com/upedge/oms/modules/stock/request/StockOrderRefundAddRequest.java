package com.upedge.oms.modules.stock.request;

import com.upedge.oms.modules.stock.entity.StockOrderRefund;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author author
 */
@Data
public class StockOrderRefundAddRequest{

    /**
    * 备库订单ID
    */
    private Long stockOrderId;
    /**
    * 退款总金额
    */
    private BigDecimal amount;
    /**
    * 退款状态，申请中=0，通过=1，驳回=2
    */
    private Integer state;
    /**
    * 退款原因
    */
    private String reason;
    /**
    * 
    */
    private Date createTime;
    /**
    * 
    */
    private Date updateTime;
    /**
    * 0:app,1:admin
    */
    private Integer source;
    /**
    * 
    */
    private Long customerId;
    /**
    * 
    */
    private String managerCode;
    /**
    * 
    */
    private String warehouseCode;

    public StockOrderRefund toStockOrderRefund(){
        StockOrderRefund stockOrderRefund=new StockOrderRefund();
        stockOrderRefund.setStockOrderId(stockOrderId);
        stockOrderRefund.setAmount(amount);
        stockOrderRefund.setState(state);
        stockOrderRefund.setReason(reason);
        stockOrderRefund.setCreateTime(createTime);
        stockOrderRefund.setUpdateTime(updateTime);
        stockOrderRefund.setSource(source);
        stockOrderRefund.setCustomerId(customerId);
        stockOrderRefund.setManagerCode(managerCode);
        stockOrderRefund.setWarehouseCode(warehouseCode);
        return stockOrderRefund;
    }

}
