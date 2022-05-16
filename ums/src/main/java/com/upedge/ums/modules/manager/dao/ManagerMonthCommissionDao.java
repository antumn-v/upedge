package com.upedge.ums.modules.manager.dao;

import com.upedge.ums.modules.manager.entity.ManagerMonthCommission;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import com.upedge.common.base.Page;

/**
 * @author gx
 */
public interface ManagerMonthCommissionDao{

    ManagerMonthCommission selectByPrimaryKey(ManagerMonthCommission record);

    int deleteByPrimaryKey(ManagerMonthCommission record);

    int updateByPrimaryKey(ManagerMonthCommission record);

    int updateByPrimaryKeySelective(ManagerMonthCommission record);

    int insert(ManagerMonthCommission record);

    int insertSelective(ManagerMonthCommission record);

    int insertByBatch(List<ManagerMonthCommission> list);

    List<ManagerMonthCommission> select(Page<ManagerMonthCommission> record);

    long count(Page<ManagerMonthCommission> record);

}
