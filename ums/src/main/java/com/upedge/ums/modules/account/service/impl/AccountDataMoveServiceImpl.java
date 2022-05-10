package com.upedge.ums.modules.account.service.impl;

import com.upedge.common.model.old.ums.AppUser;
import com.upedge.common.model.old.ums.UserCommission;
import com.upedge.ums.modules.account.dao.AccountMapper;
import com.upedge.ums.modules.account.entity.Account;
import com.upedge.ums.modules.account.service.AccountDataMoveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class AccountDataMoveServiceImpl implements AccountDataMoveService {


    @Autowired
    AccountMapper accountMapper;

    @Override
    public void accountUpdateByAppUser(AppUser appUser, UserCommission userCommission) {
        Account account = new Account();
        account.setBalance(appUser.getBalance());
        account.setAffiliateRebate(appUser.getBenefitsMoney());
        account.setCredit(appUser.getCredit());
        account.setCreditLimit(appUser.getCreditLimit());
        account.setCustomerId(appUser.getId());
        if (null != userCommission){
            account.setCommission(userCommission.getCommission());
        }else {
            account.setCommission(BigDecimal.ZERO);
        }
        accountMapper.updateBalanceByCustomerId(account);
    }
}
