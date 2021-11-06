package com.upedge.tms.modules.ship.service;

import com.upedge.common.base.Page;
import com.upedge.tms.modules.ship.entity.TrackingMoreCarrier;
import com.upedge.tms.modules.ship.response.TrackingMoreCarrierListResponse;

import java.util.List;

/**
 * @author author
 */
public interface TrackingMoreCarrierService{

    TrackingMoreCarrier selectByPrimaryKey(String code);

    int deleteByPrimaryKey(String code);

    int updateByPrimaryKey(TrackingMoreCarrier record);

    int updateByPrimaryKeySelective(TrackingMoreCarrier record);

    int insert(TrackingMoreCarrier record);

    int insertSelective(TrackingMoreCarrier record);

    List<TrackingMoreCarrier> select(Page<TrackingMoreCarrier> record);

    long count(Page<TrackingMoreCarrier> record);

    TrackingMoreCarrierListResponse allTrackingMoreCarrier();
}

