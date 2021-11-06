package com.upedge.ums.modules.manager.dao;

import com.upedge.common.base.Page;
import com.upedge.ums.modules.manager.entity.ManagerTarget;
import com.upedge.ums.modules.manager.vo.DashboardManagerTargetVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author author
 */
public interface ManagerTargetDao{

    ManagerTarget selectMonthTargetByManagerCode(String managerCode, String month);

    ManagerTarget selectByPrimaryKey(ManagerTarget record);

    int deleteByPrimaryKey(ManagerTarget record);

    int updateByPrimaryKey(ManagerTarget record);

    int updateByPrimaryKeySelective(ManagerTarget record);

    int insert(ManagerTarget record);

    int insertSelective(ManagerTarget record);

    int insertByBatch(List<ManagerTarget> list);

    List<ManagerTarget> select(Page<ManagerTarget> record);

    long count(Page<ManagerTarget> record);

    List<ManagerTarget> getList(@Param("t") ManagerTarget managerTarget);

    /**
     * 根据月份查询数据总和
     */
    DashboardManagerTargetVo queryDataAll(@Param("dateMonth") String dateMonth);

    /**
     * 根据月份查询客户经理数据
     */
    DashboardManagerTargetVo queryDataByUserManager(@Param("managerCode") String managerCode, @Param("dateMonth") String dateMonth);

}
