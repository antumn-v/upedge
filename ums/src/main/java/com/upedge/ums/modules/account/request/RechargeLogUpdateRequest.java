package com.upedge.ums.modules.account.request;

import com.upedge.ums.modules.account.entity.RechargeLog;
import java.util.Date;
import lombok.Data;
import java.math.BigDecimal;
/**
 * @author gx
 */
@Data
public class RechargeLogUpdateRequest{

    /**
     * 
     */
    private Long accountId;
    /**
     * 0 <=recharge_type < 3 表示充值申请ID，>3表示退款ID
     */
    private Long relateId;
    /**
     * 
     */
    private BigDecimal amount;
    /**
     * 
     */
    private BigDecimal rebate;
    /**
     * 
     */
    private BigDecimal payed;
    /**
     * 0 未使用 1 已使用 2已完成 3已撤销
     */
    private Integer rechargeStatus;
    /**
     * 0:电汇 1:paypal充值 2:payoneer充值，3=返点充值 4.退款充值，5,内部转账
     */
    private Integer rechargeType;
    /**
     * 
     */
    private Date createTime;
    /**
     * 
     */
    private Date updateTime;

    public RechargeLog toRechargeLog(Long id){
        RechargeLog rechargeLog=new RechargeLog();
        rechargeLog.setId(id);
        rechargeLog.setAccountId(accountId);
        rechargeLog.setRelateId(relateId);
        rechargeLog.setAmount(amount);
        rechargeLog.setRebate(rebate);
        rechargeLog.setPayed(payed);
        rechargeLog.setRechargeStatus(rechargeStatus);
        rechargeLog.setRechargeType(rechargeType);
        rechargeLog.setCreateTime(createTime);
        rechargeLog.setUpdateTime(updateTime);
        return rechargeLog;
    }

}
