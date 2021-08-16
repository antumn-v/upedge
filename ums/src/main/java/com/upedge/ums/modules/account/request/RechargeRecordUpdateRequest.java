package com.upedge.ums.modules.account.request;

import com.upedge.ums.modules.account.entity.RechargeRecord;
import java.util.Date;
import lombok.Data;
import java.math.BigDecimal;
/**
 * @author gx
 */
@Data
public class RechargeRecordUpdateRequest{

    /**
     *                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                 
     */
    private Long rechargeId;
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
     * 0=balance 1=rebate 2=credit
     */
    private Integer source;
    /**
     * 
     */
    private Date createTime;
    /**
     * 每个订单对应充值记录的扣款顺序
     */
    private Integer seq;

    public RechargeRecord toRechargeRecord(Integer id){
        RechargeRecord rechargeRecord=new RechargeRecord();
        rechargeRecord.setId(id);
        rechargeRecord.setRechargeId(rechargeId);
        rechargeRecord.setCustomerId(customerId);
        rechargeRecord.setOrderId(orderId);
        rechargeRecord.setOrderType(orderType);
        rechargeRecord.setAmount(amount);
        rechargeRecord.setSource(source);
        rechargeRecord.setCreateTime(createTime);
        rechargeRecord.setSeq(seq);
        return rechargeRecord;
    }

}
