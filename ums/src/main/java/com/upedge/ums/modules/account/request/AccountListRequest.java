package com.upedge.ums.modules.account.request;

import com.upedge.common.base.Page;
import com.upedge.ums.modules.account.entity.Account;
import lombok.Data;

@Data
public class AccountListRequest extends Page<Account> {


    String beginDate;

    String endDate;
}
