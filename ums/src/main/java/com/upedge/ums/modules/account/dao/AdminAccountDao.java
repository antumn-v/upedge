package com.upedge.ums.modules.account.dao;

import com.upedge.common.base.Page;
import com.upedge.ums.modules.account.entity.AdminAccount;

import java.util.List;

/**
 * @author author
 */
public interface AdminAccountDao{

    AdminAccount selectByPrimaryKey(Long id);

    int deleteByPrimaryKey(AdminAccount record);

    int updateByPrimaryKey(AdminAccount record);

    int updateByPrimaryKeySelective(AdminAccount record);

    int insert(AdminAccount record);

    int insertSelective(AdminAccount record);

    int insertByBatch(List<AdminAccount> list);

    List<AdminAccount> select(Page<AdminAccount> record);

    long count(Page<AdminAccount> record);

    List<AdminAccount> allAdminAccount();
}
