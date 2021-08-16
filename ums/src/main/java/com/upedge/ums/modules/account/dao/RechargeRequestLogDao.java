package com.upedge.ums.modules.account.dao;

import com.upedge.ums.modules.account.entity.RechargeRequestLog;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import com.upedge.common.base.Page;

/**
 * @author gx
 */
public interface RechargeRequestLogDao{

    RechargeRequestLog selectByPrimaryKey(RechargeRequestLog record);

    int deleteByPrimaryKey(RechargeRequestLog record);

    int updateByPrimaryKey(RechargeRequestLog record);

    int updateByPrimaryKeySelective(RechargeRequestLog record);

    int insert(RechargeRequestLog record);

    int insertSelective(RechargeRequestLog record);

    int insertByBatch(List<RechargeRequestLog> list);

    List<RechargeRequestLog> select(Page<RechargeRequestLog> record);

    long count(Page<RechargeRequestLog> record);

}
