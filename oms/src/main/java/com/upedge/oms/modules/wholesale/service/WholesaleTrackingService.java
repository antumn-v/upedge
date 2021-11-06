package com.upedge.oms.modules.wholesale.service;

import com.upedge.common.base.Page;
import com.upedge.oms.modules.wholesale.entity.WholesaleTracking;

import java.util.List;

/**
 * @author author
 */
public interface WholesaleTrackingService{

    WholesaleTracking selectByPrimaryKey(Long id);

    int deleteByPrimaryKey(Long id);

    int updateByPrimaryKey(WholesaleTracking record);

    int updateByPrimaryKeySelective(WholesaleTracking record);

    int insert(WholesaleTracking record);

    int insertSelective(WholesaleTracking record);

    List<WholesaleTracking> select(Page<WholesaleTracking> record);

    long count(Page<WholesaleTracking> record);
}

