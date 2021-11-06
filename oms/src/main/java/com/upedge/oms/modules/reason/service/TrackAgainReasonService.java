package com.upedge.oms.modules.reason.service;

import com.upedge.common.base.Page;
import com.upedge.oms.modules.reason.entity.TrackAgainReason;
import com.upedge.oms.modules.reason.response.TrackAgainReasonListResponse;

import java.util.List;

/**
 * @author author
 */
public interface TrackAgainReasonService{

    TrackAgainReason selectByPrimaryKey(Long id);

    int deleteByPrimaryKey(Long id);

    int updateByPrimaryKey(TrackAgainReason record);

    int updateByPrimaryKeySelective(TrackAgainReason record);

    int insert(TrackAgainReason record);

    int insertSelective(TrackAgainReason record);

    List<TrackAgainReason> select(Page<TrackAgainReason> record);

    long count(Page<TrackAgainReason> record);

    TrackAgainReasonListResponse all();
}

