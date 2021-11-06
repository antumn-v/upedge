package com.upedge.oms.modules.pack.dao;

import com.upedge.common.base.Page;
import com.upedge.oms.modules.pack.entity.PackageTracking;
import com.upedge.oms.modules.pack.request.AnalysisLogisticsRequest;
import com.upedge.oms.modules.pack.request.PackageTrackingListRequest;
import com.upedge.oms.modules.pack.request.PackageTrackingRefreshRequest;
import com.upedge.oms.modules.pack.request.TrackingAnalysisRequest;
import com.upedge.oms.modules.pack.vo.TrackTableItemVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author author
 */
public interface PackageTrackingDao{

    Integer maxTrackDurationDays(TrackingAnalysisRequest request);

    List<PackageTracking> selectPackageTracking(PackageTrackingListRequest request);

    Long countPackageTracking(PackageTrackingListRequest request);

    List<PackageTracking> selectPackingTrackingForRefresh(PackageTrackingRefreshRequest refreshRequest);

    PackageTracking selectByPrimaryKey(PackageTracking record);

    int deleteByPrimaryKey(PackageTracking record);

    int updateByPrimaryKey(PackageTracking record);

    int updateByPrimaryKeySelective(PackageTracking record);

    int insert(PackageTracking record);

    int insertSelective(PackageTracking record);

    int insertByBatch(List<PackageTracking> list);

    List<PackageTracking> select(Page<PackageTracking> record);

    long count(Page<PackageTracking> record);

    void savePackageTracking(List<PackageTracking> packageTrackingList);

    void updateAppUserId(@Param("startDay") String startDay, @Param("endDay") String endDay);
    void updateWholesaleAppUserId(@Param("startDay") String startDay, @Param("endDay") String endDay);

    /**
     * 物流分析  package_tracking list
     * @param analysisLogistics
     * @return
     */
    List<TrackTableItemVo> getAnalysisLogisticsList(AnalysisLogisticsRequest analysisLogistics);
    Long getAnalysisLogisticsCount(AnalysisLogisticsRequest analysisLogistics);

    /**
     * 获取签收总时效
     * @param startDay
     * @param endDay
     * @param orderSourceId
     * @param customer
     * @param durationType
     * @param transportId
     * @param destination
     * @return
     */
    long totalTrackDuration(@Param("startDay") String startDay, @Param("endDay") String endDay,
                            @Param("orderSourceId") Integer orderSourceId, @Param("customerId") Long customer,
                            @Param("durationType") Integer durationType, @Param("transportId") Integer transportId,
                            @Param("destination") String destination);

    /**
     * 获取签收时效内的包裹数
     * @param startDay
     * @param endDay
     * @param orderSourceId
     * @param customer
     * @param withInDay
     * @param durationType
     * @param transportId
     * @param destination
     * @return
     */
    Integer countTrackDurationWithInDay(@Param("startDay") String startDay, @Param("endDay") String endDay,
                                        @Param("orderSourceId") Integer orderSourceId, @Param("customerId") Long customer,
                                        @Param("withInDay") Integer withInDay, @Param("durationType") Integer durationType,
                                        @Param("transportId") Integer transportId, @Param("destination") String destination);

    // 根据运输单号更新对应运输简码 客户id
    void updateTrackingMoreCodeByIds(@Param("ids") List<String> trackNumbers);
    void updateAppUserIdByIds(@Param("ids") List<String> trackNumbers);

    List<PackageTracking> refreshBlankStatus(@Param("page") Page page);

    List<PackageTracking> getTrackingMoreCodeByIds(@Param("ids") List<String> trackNumbers);

    void updateTrackingMoreCodeById(@Param("request") PackageTracking packageTracking);

    List<PackageTracking> selectPackingTrackingForRefreshPage(Page<PackageTrackingRefreshRequest> page);
}
