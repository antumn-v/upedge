package com.upedge.ums.modules.account.dao;

import com.upedge.ums.modules.account.entity.RechargeLog;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import com.upedge.common.base.Page;

/**
 * @author gx
 */
public interface RechargeLogDao{

    RechargeLog selectByPrimaryKey(RechargeLog record);

    int deleteByPrimaryKey(RechargeLog record);

    int updateByPrimaryKey(RechargeLog record);

    int updateByPrimaryKeySelective(RechargeLog record);

    int insert(RechargeLog record);

    int insertSelective(RechargeLog record);

    int insertByBatch(List<RechargeLog> list);

    List<RechargeLog> select(Page<RechargeLog> record);

    long count(Page<RechargeLog> record);

}
