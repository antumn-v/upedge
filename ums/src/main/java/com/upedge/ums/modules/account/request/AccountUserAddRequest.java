package com.upedge.ums.modules.account.request;

import com.upedge.common.base.Page;
import com.upedge.ums.modules.account.entity.AccountUser;
import java.util.Date;
import lombok.Data;
/**
 * @author gx
 */
@Data
public class AccountUserAddRequest{

    /**
    * 
    */
    private Long accountId;

    public AccountUser toAccountUser(){
        AccountUser accountUser=new AccountUser();
        accountUser.setAccountId(accountId);
        return accountUser;
    }

}
