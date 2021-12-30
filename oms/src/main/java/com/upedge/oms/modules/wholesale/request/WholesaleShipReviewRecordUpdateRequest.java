package com.upedge.oms.modules.wholesale.request;

import com.upedge.oms.modules.wholesale.entity.WholesaleShipReviewRecord;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
/**
 * @author gx
 */
@Data
public class WholesaleShipReviewRecordUpdateRequest{

    /**
     * 
     */
    private Long customerId;
    /**
     * 
     */
    private Long wholesaleOrderId;
    /**
     * 
     */
    private String shipMethodName;
    /**
     * 
     */
    private BigDecimal shipPrice;
    /**
     * 0=支付前审核，1=支付后审核
     */
    private Integer reviewType;
    /**
     * 0=未通过 1=通过
     */
    private Integer reviewStatus;
    /**
     * 
     */
    private Long reviewUserId;
    /**
     * 
     */
    private Date createTime;

    public WholesaleShipReviewRecord toWholesaleShipReviewRecord(Long id){
        WholesaleShipReviewRecord wholesaleShipReviewRecord=new WholesaleShipReviewRecord();
        wholesaleShipReviewRecord.setId(id);
        wholesaleShipReviewRecord.setCustomerId(customerId);
        wholesaleShipReviewRecord.setWholesaleOrderId(wholesaleOrderId);
        wholesaleShipReviewRecord.setShipMethodName(shipMethodName);
        wholesaleShipReviewRecord.setShipPrice(shipPrice);
        wholesaleShipReviewRecord.setReviewType(reviewType);
        wholesaleShipReviewRecord.setReviewStatus(reviewStatus);
        wholesaleShipReviewRecord.setReviewUserId(reviewUserId);
        wholesaleShipReviewRecord.setCreateTime(createTime);
        return wholesaleShipReviewRecord;
    }

}
