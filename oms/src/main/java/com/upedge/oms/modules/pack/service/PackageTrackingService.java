package com.upedge.oms.modules.pack.service;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.base.Page;
import com.upedge.common.model.user.vo.Session;
import com.upedge.oms.modules.pack.entity.PackageTracking;
import com.upedge.oms.modules.pack.request.AnalysisLogisticsRequest;
import com.upedge.oms.modules.pack.request.PackageTrackingListRequest;
import com.upedge.oms.modules.pack.request.PackageTrackingRefreshRequest;

import java.util.Calendar;
import java.util.List;

/**
 * @author author
 */
public interface PackageTrackingService{


    BaseResponse packageTrackingList(PackageTrackingListRequest request);

    BaseResponse refreshTrackingState(PackageTrackingRefreshRequest request);

    PackageTracking selectByPrimaryKey(String trackNumber);

    int deleteByPrimaryKey(String trackNumber);

    int updateByPrimaryKey(PackageTracking record);

    int updateByPrimaryKeySelective(PackageTracking record);

    int insert(PackageTracking record);

    int insertSelective(PackageTracking record);

    List<PackageTracking> select(Page<PackageTracking> record);

    long count(Page<PackageTracking> record);

    /**
     * 物流分析
     * @param analysisLogistics
     * @return
     */
    BaseResponse analysisLogistics(AnalysisLogisticsRequest analysisLogistics, Session session);

    void refreshBlankStatus();

    void updateTrackingMoreByDate(Calendar time);
}

