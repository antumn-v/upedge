package com.upedge.ums.modules.manager.service;

import com.upedge.ums.modules.manager.entity.ManagerMonthCommission;
import com.upedge.common.base.Page;
import java.util.List;

/**
 * @author gx
 */
public interface ManagerMonthCommissionService{

    ManagerMonthCommission selectByManagerAndMonth(Long managerId,String month);

    ManagerMonthCommission selectByPrimaryKey(Long id);

    int deleteByPrimaryKey(Long id);

    int updateByPrimaryKey(ManagerMonthCommission record);

    int updateByPrimaryKeySelective(ManagerMonthCommission record);

    int insert(ManagerMonthCommission record);

    int insertSelective(ManagerMonthCommission record);

    List<ManagerMonthCommission> select(Page<ManagerMonthCommission> record);

    long count(Page<ManagerMonthCommission> record);
}

