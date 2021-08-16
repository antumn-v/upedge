package com.upedge.ums.modules.account.dao;

import com.upedge.ums.modules.account.entity.Account;
import com.upedge.ums.modules.account.entity.AccountUser;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import com.upedge.common.base.Page;

/**
 * @author gx
 */
public interface AccountUserDao{

    Account selectUserAccount(Long userId);

    AccountUser selectByPrimaryKey(AccountUser record);

    int deleteByPrimaryKey(AccountUser record);

    int updateByPrimaryKey(AccountUser record);

    int updateByPrimaryKeySelective(AccountUser record);

    int insert(AccountUser record);

    int insertSelective(AccountUser record);

    int insertByBatch(List<AccountUser> list);

    List<AccountUser> select(Page<AccountUser> record);

    long count(Page<AccountUser> record);

}
