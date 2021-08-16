package com.upedge.ums.modules.account.request;

import com.upedge.ums.modules.account.entity.AccountUser;
import java.util.Date;
import lombok.Data;
/**
 * @author gx
 */
@Data
public class AccountUserUpdateRequest{

    Long userId;
    /**
     * 
     */
    private Long accountId;

    public AccountUser toAccountUser(){
        AccountUser accountUser=new AccountUser();
        accountUser.setUserId(userId);
        accountUser.setAccountId(accountId);
        return accountUser;
    }

}
