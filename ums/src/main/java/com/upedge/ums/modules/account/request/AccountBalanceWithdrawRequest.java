package com.upedge.ums.modules.account.request;

import com.upedge.common.model.user.vo.Session;
import com.upedge.common.utils.IdGenerate;
import com.upedge.ums.modules.account.entity.AccountWithdrawLog;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class AccountBalanceWithdrawRequest {

    @NotNull
    BigDecimal withdrawAmount;

    @NotNull
    private String collectAccount;

    private Integer collectType;

    public AccountWithdrawLog toWithdrawLog(Session session){
        AccountWithdrawLog withdrawLog = new AccountWithdrawLog();
        withdrawLog.setId(IdGenerate.nextId());
        withdrawLog.setWithdrawAmount(withdrawAmount);
        withdrawLog.setBalance(BigDecimal.ZERO);
        withdrawLog.setAccountId(session.getAccountId());
        withdrawLog.setCustomerId(session.getCustomerId());
        withdrawLog.setAffiliateRebate(BigDecimal.ZERO);
        withdrawLog.setVipRebate(BigDecimal.ZERO);
        withdrawLog.setCollectAccount(collectAccount);
        if (collectType == null){
            collectType = 0;
        }
        withdrawLog.setCollectType(collectType);
        withdrawLog.setCreateTime(new Date());
        withdrawLog.setUpdateTime(new Date());
        withdrawLog.setStatus(0);
        return withdrawLog;
    }


}
