package com.upedge.ums.modules.account.request;

import com.upedge.ums.modules.account.entity.AccountPayMethod;
import com.upedge.ums.modules.account.entity.AccountPayMethodAttr;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * @author 海桐
 */
@Data
public class AccountPayMethodAddRequest {

    @NotNull
    private Long accountId;

    @NotNull
    private Integer paymethodId;

    @NotNull
    private String bankNum;

    private Integer isDefault = 0;

    private List<AccountPayMethodAttr> attrs;


    public List<AccountPayMethodAttr> toAccountPayMethodAttrs(Integer accountPayMethodId) {

        for (AccountPayMethodAttr attr: attrs) {
            attr.setAccountPaymethodId(accountPayMethodId);
        }
        return attrs;

    }



    public AccountPayMethod toAccountPayMethod() {
        AccountPayMethod payMethod = new AccountPayMethod();
        payMethod.setAccountId(accountId);
        payMethod.setPaymethodId(paymethodId);
        payMethod.setAutopay(0);
        payMethod.setBankNum(bankNum);
        payMethod.setIsdefault(isDefault);
        payMethod.setStatus(1);
        payMethod.setCreateTime(new Date());
        payMethod.setUpdateTime(new Date());
        return payMethod;
    }


}
