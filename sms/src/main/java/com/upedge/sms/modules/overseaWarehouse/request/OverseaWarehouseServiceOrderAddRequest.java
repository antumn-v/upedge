package com.upedge.sms.modules.overseaWarehouse.request;

import com.upedge.sms.modules.overseaWarehouse.entity.OverseaWarehouseServiceOrder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
/**
 * @author gx
 */
@Data
public class OverseaWarehouseServiceOrderAddRequest{

    /**
    * 
    */
    private Long customerId;
    /**
    * 
    */
    private BigDecimal productAmount;
    /**
    * 
    */
    private BigDecimal shipPrice;
    /**
    * 
    */
    private Integer shipType;
    /**
    * 
    */
    private BigDecimal payAmount;
    /**
    * 
    */
    private Integer payState;
    /**
    * 
    */
    private Integer shipState;
    /**
    * 
    */
    private Integer refundState;
    /**
    * 
    */
    private Long paymentId;
    /**
    * 
    */
    private String warehouseCode;
    /**
    * 
    */
    private Long warehouseOrderId;
    /**
    * 
    */
    private Integer warehouseOrderState;
    /**
    * 
    */
    private String trackingCode;
    /**
    * 
    */
    private Date payTime;
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
    private Long managerId;

    public OverseaWarehouseServiceOrder toOverseaWarehouseServiceOrder(){
        OverseaWarehouseServiceOrder overseaWarehouseServiceOrder=new OverseaWarehouseServiceOrder();
        overseaWarehouseServiceOrder.setCustomerId(customerId);
        overseaWarehouseServiceOrder.setProductAmount(productAmount);
        overseaWarehouseServiceOrder.setShipPrice(shipPrice);
        overseaWarehouseServiceOrder.setShipType(shipType);
        overseaWarehouseServiceOrder.setPayAmount(payAmount);
        overseaWarehouseServiceOrder.setPayState(payState);
        overseaWarehouseServiceOrder.setShipState(shipState);
        overseaWarehouseServiceOrder.setRefundState(refundState);
        overseaWarehouseServiceOrder.setPaymentId(paymentId);
        overseaWarehouseServiceOrder.setWarehouseCode(warehouseCode);
        overseaWarehouseServiceOrder.setWarehouseOrderId(warehouseOrderId);
        overseaWarehouseServiceOrder.setWarehouseOrderState(warehouseOrderState);
        overseaWarehouseServiceOrder.setTrackingCode(trackingCode);
        overseaWarehouseServiceOrder.setPayTime(payTime);
        overseaWarehouseServiceOrder.setCreateTime(createTime);
        overseaWarehouseServiceOrder.setUpdateTime(updateTime);
        overseaWarehouseServiceOrder.setManagerId(managerId);
        return overseaWarehouseServiceOrder;
    }

}
