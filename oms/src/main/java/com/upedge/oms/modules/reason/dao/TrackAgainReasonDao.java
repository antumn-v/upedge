package com.upedge.oms.modules.reason.dao;

import com.upedge.common.base.Page;
import com.upedge.oms.modules.reason.entity.TrackAgainReason;

import java.util.List;

/**
 * @author author
 */
public interface TrackAgainReasonDao{

    TrackAgainReason selectByPrimaryKey(TrackAgainReason record);

    int deleteByPrimaryKey(TrackAgainReason record);

    int updateByPrimaryKey(TrackAgainReason record);

    int updateByPrimaryKeySelective(TrackAgainReason record);

    int insert(TrackAgainReason record);

    int insertSelective(TrackAgainReason record);

    int insertByBatch(List<TrackAgainReason> list);

    List<TrackAgainReason> select(Page<TrackAgainReason> record);

    long count(Page<TrackAgainReason> record);

    List<TrackAgainReason> all();
}
