package com.upedge.ums.modules.account.dao;

import com.upedge.common.base.Page;
import com.upedge.ums.modules.account.entity.Payoneer;

import java.util.List;

/**
 * @author xwCui
 */
public interface PayoneerDao{

    Payoneer selectByPayoneerAccountId(String payoneerAccountId);

    List<Payoneer> selectAccountPayoneers(Long accountId);

    Payoneer selectByPrimaryKey(Integer id);

    int deleteByPrimaryKey(Payoneer record);

    int updateByPrimaryKey(Payoneer record);

    int updateByPrimaryKeySelective(Payoneer record);

    int insert(Payoneer record);

    int insertSelective(Payoneer record);

    int insertByBatch(List<Payoneer> list);

    List<Payoneer> select(Page<Payoneer> record);

    long count(Page<Payoneer> record);

}
