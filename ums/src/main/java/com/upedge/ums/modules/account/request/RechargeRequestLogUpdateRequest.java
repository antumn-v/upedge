package com.upedge.ums.modules.account.request;

import com.upedge.ums.modules.account.entity.RechargeRequestLog;
import java.util.Date;
import lombok.Data;
import java.math.BigDecimal;
/**
 * @author gx
 */
@Data
public class RechargeRequestLogUpdateRequest{

    /**
     * 
     */
    private Long accountId;
    /**
     * 
     */
    private Integer accountPaymethodId;
    /**
     * 提交充值申请的用户
     */
    private Long customerId;
    /**
     * 申请用户ID
     */
    private Long requestUserId;
    /**
     * 处理人
     */
    private Long handleUserId;
    /**
     * 还款金额
     */
    private BigDecimal repaymentAmount;
    /**
     * 到账金额
     */
    private BigDecimal amount;
    /**
     * 
     */
    private BigDecimal benefits;
    /**
     * 申请金额
     */
    private BigDecimal customerMoney;
    /**
     * 备注
     */
    private String remarks;
    /**
     * 流水号
     */
    private String transferFlow;
    /**
     * 0:电汇 1:paypal充值 2:payoneer充值,3:payoneer申请
     */
    private Integer rechargeType;
    /**
     * 0=审核中，1=成功，2=失败  3=payoneer待提交付款请求
     */
    private Integer status;
    /**
     * 
     */
    private Date createTime;
    /**
     * 
     */
    private Date updateTime;

    public RechargeRequestLog toRechargeRequestLog(Long id){
        RechargeRequestLog rechargeRequestLog=new RechargeRequestLog();
        rechargeRequestLog.setId(id);
        rechargeRequestLog.setAccountId(accountId);
        rechargeRequestLog.setAccountPaymethodId(accountPaymethodId);
        rechargeRequestLog.setCustomerId(customerId);
        rechargeRequestLog.setRequestUserId(requestUserId);
        rechargeRequestLog.setHandleUserId(handleUserId);
        rechargeRequestLog.setRepaymentAmount(repaymentAmount);
        rechargeRequestLog.setAmount(amount);
        rechargeRequestLog.setBenefits(benefits);
        rechargeRequestLog.setCustomerMoney(customerMoney);
        rechargeRequestLog.setRemarks(remarks);
        rechargeRequestLog.setTransferFlow(transferFlow);
        rechargeRequestLog.setRechargeType(rechargeType);
        rechargeRequestLog.setStatus(status);
        rechargeRequestLog.setCreateTime(createTime);
        rechargeRequestLog.setUpdateTime(updateTime);
        return rechargeRequestLog;
    }

}
