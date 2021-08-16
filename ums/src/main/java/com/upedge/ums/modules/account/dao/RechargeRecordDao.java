package com.upedge.ums.modules.account.dao;

import com.upedge.ums.modules.account.entity.RechargeRecord;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import com.upedge.common.base.Page;

/**
 * @author gx
 */
public interface RechargeRecordDao{

    RechargeRecord selectByPrimaryKey(RechargeRecord record);

    int deleteByPrimaryKey(RechargeRecord record);

    int updateByPrimaryKey(RechargeRecord record);

    int updateByPrimaryKeySelective(RechargeRecord record);

    int insert(RechargeRecord record);

    int insertSelective(RechargeRecord record);

    int insertByBatch(List<RechargeRecord> list);

    List<RechargeRecord> select(Page<RechargeRecord> record);

    long count(Page<RechargeRecord> record);

}
