package com.upedge.ums.modules.account.dao;

import com.upedge.ums.modules.account.entity.AccountLog;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import com.upedge.common.base.Page;

/**
 * @author gx
 */
public interface AccountLogDao{

    AccountLog selectByPrimaryKey(AccountLog record);

    int deleteByPrimaryKey(AccountLog record);

    int updateByPrimaryKey(AccountLog record);

    int updateByPrimaryKeySelective(AccountLog record);

    int insert(AccountLog record);

    int insertSelective(AccountLog record);

    int insertByBatch(List<AccountLog> list);

    List<AccountLog> select(Page<AccountLog> record);

    long count(Page<AccountLog> record);

}
