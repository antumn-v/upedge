package com.upedge.ums.modules.account.request;

import com.upedge.ums.modules.account.entity.RefundRecord;
import java.util.Date;
import lombok.Data;
import java.math.BigDecimal;
/**
 * @author gx
 */
@Data
public class RefundRecordUpdateRequest{

    /**
     *                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                 
     */
    private Long rechargeRecordId;
    /**
     * 
     */
    private Long customerId;
    /**
     * 
     */
    private Long orderId;
    /**
     * 
     */
    private Integer orderType;
    /**
     * 
     */
    private BigDecimal amount;
    /**
     * 0=balance 1=rebate
     */
    private Integer source;
    /**
     * 
     */
    private Date createTime;
    /**
     * 每个订单对应扣款记录的退款顺序
     */
    private Integer seq;

    public RefundRecord toRefundRecord(Integer id){
        RefundRecord refundRecord=new RefundRecord();
        refundRecord.setId(id);
        refundRecord.setRechargeRecordId(rechargeRecordId);
        refundRecord.setCustomerId(customerId);
        refundRecord.setOrderId(orderId);
        refundRecord.setOrderType(orderType);
        refundRecord.setAmount(amount);
        refundRecord.setSource(source);
        refundRecord.setCreateTime(createTime);
        refundRecord.setSeq(seq);
        return refundRecord;
    }

}
