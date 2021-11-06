package com.upedge.ums.modules.account.service;

import com.upedge.common.model.old.ums.AppUser;
import com.upedge.common.model.old.ums.UserCommission;

public interface AccountDataMoveService {


    void accountUpdateByAppUser(AppUser appUser, UserCommission userCommission);
}
