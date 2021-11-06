package com.upedge.ums.modules.account.service;

import com.upedge.common.base.Page;
import com.upedge.common.model.user.vo.Session;
import com.upedge.ums.modules.account.entity.AdminCredit;
import com.upedge.ums.modules.account.request.AdminCreditApplyRequest;
import com.upedge.ums.modules.account.response.AdminCreditApplyResponse;
import com.upedge.ums.modules.account.response.AdminCreditUpdateResponse;

import java.util.List;

/**
 * @author author
 */
public interface AdminCreditService{

    AdminCredit selectByPrimaryKey(Long id);

    int deleteByPrimaryKey(Long id);

    int updateByPrimaryKey(AdminCredit record);

    int updateByPrimaryKeySelective(AdminCredit record);

    int insert(AdminCredit record);

    int insertSelective(AdminCredit record);

    List<AdminCredit> select(Page<AdminCredit> record);

    long count(Page<AdminCredit> record);

    AdminCreditApplyResponse applyCreditLimit(AdminCreditApplyRequest request, Session session);

    AdminCreditUpdateResponse confirmApply(Long id, Session session);

    AdminCreditUpdateResponse rejectApply(Long id, Session session);
}

