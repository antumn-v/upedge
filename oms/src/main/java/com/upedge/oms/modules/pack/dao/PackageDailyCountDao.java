package com.upedge.oms.modules.pack.dao;

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
public interface PackageDailyCountDao{
    /**
     * 客户经理包裹数
     * @param request
     * @return
     */
    List<ManagerPackageCountVo> selectManagerPackageCount(PackageDailyCountRequest request);
    /**
     * 每日包裹数
     * @param request
     * @return
     */
    List<PackageDailyCountVo> selectPackageDailyCount(PackageDailyCountRequest request);

    List<ManagerPackageStatisticsVo> selectManagerPackageStatistics(ManagerPackageStatisticsRequest request);

    PackageDailyCount selectByPrimaryKey(PackageDailyCount record);

    int deleteByPrimaryKey(PackageDailyCount record);

    int updateByPrimaryKey(PackageDailyCount record);

    int updateByPrimaryKeySelective(PackageDailyCount record);

    int insert(PackageDailyCount record);

    int insertSelective(PackageDailyCount record);

    int insertByBatch(List<PackageDailyCount> list);

    int savePackageDailyCounts(List<PackageDailyCount> list);

    List<PackageDailyCount> select(Page<PackageDailyCount> record);

    long count(Page<PackageDailyCount> record);

}
