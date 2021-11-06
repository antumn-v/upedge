package com.upedge.oms.modules.order.request;

import com.upedge.oms.modules.order.entity.StoreOrderRefund;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author author
 */
@Data
public class StoreOrderRefundUpdateRequest{

    /**
     * 
     */
    private String platOrderRefundId;
    /**
     * 
     */
    private String platOrderId;
    /**
     * 
     */
    private Long storeOrderId;
    /**
     * 
     */
    private BigDecimal refundAmount;
    /**
     * 
     */
    private BigDecimal usdAmount;
    /**
     * 
     */
    private BigDecimal otherFee;
    /**
     * 
     */
    private BigDecimal usdRate;
    /**
     * 
     */
    private BigDecimal currencyRate;
    /**
     * 
     */
    private String note;
    /**
     * 0=退产品费，1=只退运费不退产品费
     */
    private Integer refundType;
    /**
     * 
     */
    private Date createdAt;
    /**
     * 
     */
    private Date createTime;

    public StoreOrderRefund toStoreOrderRefund(Long id){
        StoreOrderRefund storeOrderRefund=new StoreOrderRefund();
        storeOrderRefund.setId(id);
        storeOrderRefund.setPlatOrderRefundId(platOrderRefundId);
        storeOrderRefund.setPlatOrderId(platOrderId);
        storeOrderRefund.setStoreOrderId(storeOrderId);
        storeOrderRefund.setRefundAmount(refundAmount);
        storeOrderRefund.setUsdAmount(usdAmount);
        storeOrderRefund.setOtherFee(otherFee);
        storeOrderRefund.setUsdRate(usdRate);
        storeOrderRefund.setCreateTime(createTime);
        return storeOrderRefund;
    }

}
