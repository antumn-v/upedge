package com.upedge.tms.modules.ship.dao;

import com.upedge.common.base.Page;
import com.upedge.tms.modules.ship.entity.TrackingMoreCarrier;

import java.util.List;

/**
 * @author author
 */
public interface TrackingMoreCarrierDao{

    TrackingMoreCarrier selectByPrimaryKey(TrackingMoreCarrier record);

    int deleteByPrimaryKey(TrackingMoreCarrier record);

    int updateByPrimaryKey(TrackingMoreCarrier record);

    int updateByPrimaryKeySelective(TrackingMoreCarrier record);

    int insert(TrackingMoreCarrier record);

    int insertSelective(TrackingMoreCarrier record);

    int insertByBatch(List<TrackingMoreCarrier> list);

    List<TrackingMoreCarrier> select(Page<TrackingMoreCarrier> record);

    long count(Page<TrackingMoreCarrier> record);

    List<TrackingMoreCarrier> allTrackingMoreCarrier();
}
