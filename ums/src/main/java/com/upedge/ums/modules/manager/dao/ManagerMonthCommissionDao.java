package com.upedge.ums.modules.manager.dao;

import com.upedge.common.base.Page;
import com.upedge.ums.modules.manager.entity.ManagerMonthCommission;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author gx
 */
public interface ManagerMonthCommissionDao {

    ManagerMonthCommission selectByManagerAndMonth(@Param("managerId") Long managerId, @Param("month") String month);

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
