package com.upedge.oms.modules.pack.service;

import com.upedge.common.base.Page;
import com.upedge.common.model.statistics.request.ManagerPackageStatisticsRequest;
import com.upedge.common.model.statistics.vo.ManagerPackageStatisticsVo;
import com.upedge.oms.modules.pack.entity.PackageDailyCount;
import com.upedge.oms.modules.pack.request.PackageDailyCountRequest;
import com.upedge.oms.modules.pack.vo.ManagerPackageCountVo;
import com.upedge.oms.modules.pack.vo.PackageDailyCountVo;

import java.util.List;

/**
 * @author gx
 */
public interface PackageDailyCountService{

    List<ManagerPackageCountVo> selectManagerPackageCount(PackageDailyCountRequest request);

    List<PackageDailyCountVo> selectPackageDailyCount(PackageDailyCountRequest request);

    List<ManagerPackageStatisticsVo> selectManagerPackageStatistics(ManagerPackageStatisticsRequest request);

    PackageDailyCount selectByPrimaryKey(Integer id);

    int deleteByPrimaryKey(Integer id);

    int updateByPrimaryKey(PackageDailyCount record);

    int updateByPrimaryKeySelective(PackageDailyCount record);

    int insert(PackageDailyCount record);

    int insertSelective(PackageDailyCount record);

    List<PackageDailyCount> select(Page<PackageDailyCount> record);

    long count(Page<PackageDailyCount> record);
}

