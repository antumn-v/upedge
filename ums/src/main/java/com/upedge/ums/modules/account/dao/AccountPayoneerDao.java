package com.upedge.ums.modules.account.dao;

import com.upedge.common.base.Page;
import com.upedge.ums.modules.account.entity.AccountPayoneer;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author xwCui
 */
public interface AccountPayoneerDao{

    AccountPayoneer selectByAccountIdAndPayoneerId(@Param("accountId") Long accountId,
                                                   @Param("payoneerId") Integer payoneerId);

    AccountPayoneer selectByPrimaryKey(AccountPayoneer record);

    int deleteByPrimaryKey(AccountPayoneer record);

    int updateByPrimaryKey(AccountPayoneer record);

    int updateByPrimaryKeySelective(AccountPayoneer record);

    int insert(AccountPayoneer record);

    int insertSelective(AccountPayoneer record);

    int insertByBatch(List<AccountPayoneer> list);

    int removeAccountPayoneer(@Param("accountId") Long accountId,
                              @Param("payoneerId") Integer payoneerId);

    List<AccountPayoneer> select(Page<AccountPayoneer> record);

    long count(Page<AccountPayoneer> record);

}
