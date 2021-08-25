package com.upedge.ums.modules.account.dao;

import com.upedge.ums.modules.account.entity.Account;
import org.apache.ibatis.annotations.Mapper;

import java.math.BigDecimal;
import java.util.List;
import com.upedge.common.base.Page;
import org.apache.ibatis.annotations.Param;

/**
 * @author gx
 */
public interface AccountDao{

    boolean addAccountBalance(@Param("id") Long id,
                              @Param("amount") BigDecimal amount);

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
