package com.upedge.oms.modules.order.request;

import com.upedge.oms.modules.order.entity.StoreOrder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author author
 */
@Data
public class StoreOrderUpdateRequest{

    /**
     * 
     */
    private String platOrderId;
    /**
     * 
     */
    private String platOrderName;
    /**
     * 
     */
    private Long storeId;
    /**
     * 
     */
    private String customerName;
    /**
     * 
     */
    private Long storeAddressId;
    /**
     * 
     */
    private Long customerId;
    /**
     * 
     */
    private String currencyCode;
    /**
     * 
     */
    private BigDecimal currencuRate;
    /**
     * 
     */
    private BigDecimal totalPrice;
    /**
     * 
     */
    private BigDecimal totalLineItemsPrice;
    /**
     * 
     */
    private BigDecimal freight;
    /**
     * 
     */
    private BigDecimal totalWeight;
    /**
     * 0=已支付 1=部分退款 2=全部退款
     */
    private Integer financialStatus;
    /**
     * 0=未发货 1=部分发货 2=全部发货
     */
    private Integer fulfillmentStatus;
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
    private Date importTime;

    public StoreOrder toStoreOrder(Long id){
        StoreOrder storeOrder=new StoreOrder();
        storeOrder.setId(id);
        storeOrder.setPlatOrderId(platOrderId);
        storeOrder.setPlatOrderName(platOrderName);
        storeOrder.setStoreId(storeId);
        storeOrder.setStoreAddressId(storeAddressId);
        storeOrder.setCustomerId(customerId);
        storeOrder.setCurrencyCode(currencyCode);
        storeOrder.setCurrencyRate(currencuRate);
        storeOrder.setTotalPrice(totalPrice);
        storeOrder.setTotalLineItemsPrice(totalLineItemsPrice);
        storeOrder.setFreight(freight);
        storeOrder.setTotalWeight(totalWeight);
        storeOrder.setFinancialStatus(financialStatus);
        storeOrder.setFulfillmentStatus(fulfillmentStatus);
        storeOrder.setCreateTime(createTime);
        storeOrder.setUpdateTime(updateTime);
        storeOrder.setImportTime(importTime);
        return storeOrder;
    }

}
