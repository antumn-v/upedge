package com.upedge.ums.modules.account.service;

import com.upedge.common.base.Page;
import com.upedge.ums.modules.account.entity.AdminAccount;
import com.upedge.ums.modules.account.request.AdminAccountAddRequest;
import com.upedge.ums.modules.account.request.AdminAccountListRequest;
import com.upedge.ums.modules.account.request.AdminAccountUpdateRequest;
import com.upedge.ums.modules.account.response.AdminAccountAddResponse;
import com.upedge.ums.modules.account.response.AdminAccountInfoResponse;
import com.upedge.ums.modules.account.response.AdminAccountListResponse;
import com.upedge.ums.modules.account.response.AdminAccountUpdateResponse;

import java.util.List;

/**
 * @author author
 */
public interface AdminAccountService{

    AdminAccount selectByPrimaryKey(Long id);

    int deleteByPrimaryKey(Long id);

    int updateByPrimaryKey(AdminAccount record);

    int updateByPrimaryKeySelective(AdminAccount record);

    int insert(AdminAccount record);

    int insertSelective(AdminAccount record);

    List<AdminAccount> select(Page<AdminAccount> record);

    long count(Page<AdminAccount> record);

    AdminAccountListResponse all();

    AdminAccountListResponse listAdminAccount(AdminAccountListRequest request);

    AdminAccountAddResponse addAdminAccount(AdminAccountAddRequest request);

    AdminAccountUpdateResponse updateAdminAccount(Long id, AdminAccountUpdateRequest request);

    AdminAccountInfoResponse info(Long id);
}

