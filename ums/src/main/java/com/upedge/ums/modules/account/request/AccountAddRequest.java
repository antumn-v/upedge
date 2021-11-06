package com.upedge.ums.modules.account.request;

import com.upedge.common.utils.IdGenerate;
import com.upedge.ums.modules.account.entity.Account;
import com.upedge.ums.modules.account.entity.AccountPayMethod;
import com.upedge.ums.modules.account.entity.PayMethod;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author 海桐
 */
@Data
public class AccountAddRequest {

    @NotNull
    private int payMethodId;

    private String name;

    private String routeName;

    private Integer routeType;

    private String bankNum;

    private int isDefault;

    private int autoPay = 0;

    private int status = 0;


    public Account toAccount(Long customerId){
        Account account = new Account();
        account.setName(name);
        account.setCustomerId(customerId);
        account.setId(IdGenerate.nextId());
        account.setStatus(1);
        return account;
    }

    public PayMethod toPayMethod(AccountPayMethod payMethod){
        PayMethod method = new PayMethod();
        method.setId(payMethod.getId());
        method.setRouteName(routeName);
        method.setRouteType(routeType);
        return method;
    }


    public AccountPayMethod toAccountPayMethod(Long accountId){
        AccountPayMethod payMethod = new AccountPayMethod();
        payMethod.setAccountId(accountId);
        payMethod.setPaymethodId(payMethodId);
        payMethod.setAutopay(autoPay);
        payMethod.setBankNum(bankNum);
        payMethod.setIsdefault(isDefault);
        payMethod.setStatus(status);
        payMethod.setCreateTime(new Date());
        payMethod.setUpdateTime(new Date());
        return payMethod;
    }

}
