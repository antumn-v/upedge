package com.upedge.oms.modules.wholesale.dao;

import com.upedge.common.base.Page;
import com.upedge.oms.modules.wholesale.entity.WholesaleTracking;

import java.util.List;

/**
 * @author author
 */
public interface WholesaleTrackingDao{

    WholesaleTracking selectByPrimaryKey(WholesaleTracking record);

    int deleteByPrimaryKey(WholesaleTracking record);

    int updateByPrimaryKey(WholesaleTracking record);

    int updateByPrimaryKeySelective(WholesaleTracking record);

    int insert(WholesaleTracking record);

    int insertSelective(WholesaleTracking record);

    int insertByBatch(List<WholesaleTracking> list);

    List<WholesaleTracking> select(Page<WholesaleTracking> record);

    long count(Page<WholesaleTracking> record);

    WholesaleTracking queryWholesaleTrackingByOrderId(Long orderId);
}
