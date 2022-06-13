package com.upedge.ums.modules.account.request;

import com.upedge.common.base.Page;
import com.upedge.ums.modules.account.entity.AccountWithdrawLog;
import java.util.Date;
import lombok.Data;
import java.math.BigDecimal;
/**
 * @author gx
 */
@Data
public class AccountWithdrawLogAddRequest{

    /**
    * 
    */
    private Long accountId;
    /**
    * 
    */
    private Long customerId;
    /**
    * 
    */
    private BigDecimal balance;
    /**
    * 
    */
    private BigDecimal affiliateRebate;
    /**
    * 
    */
    private BigDecimal vipRebate;
    /**
    * 收款账号
    */
    private String collectAccount;
    /**
    * 收款方式:0=paypal
    */
    private Integer collectType;
    /**
    * 提现前记录
    */
    private String logBeforeWithdraw;
    /**
    * 
    */
    private Date createTime;
    /**
    * 
    */
    private Date updateTime;

    public AccountWithdrawLog toAccountWithdrawLog(){
        AccountWithdrawLog accountWithdrawLog=new AccountWithdrawLog();
        accountWithdrawLog.setAccountId(accountId);
        accountWithdrawLog.setCustomerId(customerId);
        accountWithdrawLog.setBalance(balance);
        accountWithdrawLog.setAffiliateRebate(affiliateRebate);
        accountWithdrawLog.setVipRebate(vipRebate);
        accountWithdrawLog.setCollectAccount(collectAccount);
        accountWithdrawLog.setCollectType(collectType);
        accountWithdrawLog.setLogBeforeWithdraw(logBeforeWithdraw);
        accountWithdrawLog.setCreateTime(createTime);
        accountWithdrawLog.setUpdateTime(updateTime);
        return accountWithdrawLog;
    }

}
