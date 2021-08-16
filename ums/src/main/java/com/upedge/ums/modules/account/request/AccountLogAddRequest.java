package com.upedge.ums.modules.account.request;

import com.upedge.common.base.Page;
import com.upedge.ums.modules.account.entity.AccountLog;
import java.util.Date;
import lombok.Data;
import java.math.BigDecimal;
/**
 * @author gx
 */
@Data
public class AccountLogAddRequest{

    /**
    * 
    */
    private Long accountId;
    /**
    * 
    */
    private Long customerId;
    /**
    * 支付/扣款 = 0，退款/收款 = 1，还款 = 2
    */
    private Integer transactionType;
    /**
    * 充值 = 0,备库 = 1，普通 = 2，批发 = 3，转账 = 4，提现 = 5
    */
    private Integer orderType;
    /**
    * 账户 = 0，paypal = 1，payoneer = 2，佣金 = 3
    */
    private Integer payMethod;
    /**
    * 
    */
    private Long transactionId;
    /**
    * 
    */
    private BigDecimal balance;
    /**
    * 
    */
    private BigDecimal rebate;
    /**
    * 
    */
    private BigDecimal credit;
    /**
    * 手续费
    */
    private BigDecimal fixFee;
    /**
    * 
    */
    private Date createTime;

    public AccountLog toAccountLog(){
        AccountLog accountLog=new AccountLog();
        accountLog.setAccountId(accountId);
        accountLog.setCustomerId(customerId);
        accountLog.setTransactionType(transactionType);
        accountLog.setOrderType(orderType);
        accountLog.setPayMethod(payMethod);
        accountLog.setTransactionId(transactionId);
        accountLog.setBalance(balance);
        accountLog.setRebate(rebate);
        accountLog.setCredit(credit);
        accountLog.setFixFee(fixFee);
        accountLog.setCreateTime(createTime);
        return accountLog;
    }

}
