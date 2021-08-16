package com.upedge.ums.modules.account.dao;

import com.upedge.ums.modules.account.entity.Account;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import com.upedge.common.base.Page;

/**
 * @author gx
 */
public interface AccountDao{

    Account selectByPrimaryKey(Account record);

    int deleteByPrimaryKey(Account record);

    int updateByPrimaryKey(Account record);

    int updateByPrimaryKeySelective(Account record);

    int insert(Account record);

    int insertSelective(Account record);

    int insertByBatch(List<Account> list);

    List<Account> select(Page<Account> record);

    long count(Page<Account> record);

}
