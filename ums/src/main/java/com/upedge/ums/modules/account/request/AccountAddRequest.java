package com.upedge.ums.modules.account.request;

import com.upedge.common.base.Page;
import com.upedge.ums.modules.account.entity.Account;
import java.util.Date;
import lombok.Data;
import java.math.BigDecimal;
/**
 * @author gx
 */
@Data
public class AccountAddRequest{

    /**
    * 名称
    */
    private String name;
    /**
    * 
    */
    private Long customerId;
    /**
    * 余额
    */
    private BigDecimal balance;
    /**
    * 返利
    */
    private BigDecimal rebate;
    /**
    * 已使用的信用额度
    */
    private BigDecimal credit;
    /**
    * 可使用的信用额度
    */
    private BigDecimal creditLimit;
    /**
    * 佣金
    */
    private BigDecimal commission;
    /**
    * 0=禁用，1=正常
    */
    private Integer status;
    /**
    * 是否为默认账户
    */
    private Boolean isDefault;

    public Account toAccount(){
        Account account=new Account();
        account.setName(name);
        account.setCustomerId(customerId);
        account.setBalance(balance);
        account.setRebate(rebate);
        account.setCredit(credit);
        account.setCreditLimit(creditLimit);
        account.setCommission(commission);
        account.setStatus(status);
        account.setIsDefault(isDefault);
        return account;
    }

}
