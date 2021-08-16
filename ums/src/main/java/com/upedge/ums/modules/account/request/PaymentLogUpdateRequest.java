package com.upedge.ums.modules.account.request;

import com.upedge.ums.modules.account.entity.PaymentLog;
import java.util.Date;
import lombok.Data;
import java.math.BigDecimal;
/**
 * @author gx
 */
@Data
public class PaymentLogUpdateRequest{

    /**
     * 
     */
    private Long accountId;
    /**
     * 
     */
    private Long customerId;
    /**
     * 1=备库订单 2=普通订单 3=批发订单
     */
    private Integer orderType;
    /**
     * 
     */
    private BigDecimal amount;
    /**
     * 
     */
    private BigDecimal credit;
    /**
     * 
     */
    private BigDecimal rebate;
    /**
     * 手续费
     */
    private BigDecimal fee;
    /**
     * 0 待确认 1已支付 2已撤销
     */
    private Integer payStatus;
    /**
     * 支付方式：0=recharge 1=paypal
     */
    private Integer payType;
    /**
     * 
     */
    private Date createTime;
    /**
     * 
     */
    private Date updateTime;

    public PaymentLog toPaymentLog(Long id){
        PaymentLog paymentLog=new PaymentLog();
        paymentLog.setId(id);
        paymentLog.setAccountId(accountId);
        paymentLog.setCustomerId(customerId);
        paymentLog.setOrderType(orderType);
        paymentLog.setAmount(amount);
        paymentLog.setCredit(credit);
        paymentLog.setRebate(rebate);
        paymentLog.setFee(fee);
        paymentLog.setPayStatus(payStatus);
        paymentLog.setPayType(payType);
        paymentLog.setCreateTime(createTime);
        paymentLog.setUpdateTime(updateTime);
        return paymentLog;
    }

}
