package com.upedge.ums.modules.account.request;

import com.upedge.ums.modules.account.entity.AccountPayMethod;
import com.upedge.ums.modules.account.entity.AccountPayMethodAttr;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author 海桐
 */
@Data
public class AccountPayMethodUpdateRequest {

    private Integer isdefault;

    private Integer autopay;

    private List<AccountPayMethodAttr> attrs;



    public AccountPayMethod toAccountPayMethod(AccountPayMethod payMethod) {
        payMethod.setAutopay(autopay);
        payMethod.setIsdefault(isdefault);
        payMethod.setAutopay(autopay);
        payMethod.setUpdateTime(new Date());
        return payMethod;
    }
}
