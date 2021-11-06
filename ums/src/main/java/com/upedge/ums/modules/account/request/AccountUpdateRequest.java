package com.upedge.ums.modules.account.request;

import com.upedge.ums.modules.account.entity.AccountPayMethod;
import com.upedge.ums.modules.account.entity.AccountPayMethodAttr;

import java.util.List;

/**
 * @author 海桐
 */
public class AccountUpdateRequest {

    private AccountPayMethod payMethod;

    private List<AccountPayMethodAttr> payMethodAttrs;
}
