package com.upedge.pms.modules.purchase.service;

import com.alibaba.logistics.param.AlibabaLogisticsOpenPlatformLogisticsTrace;
import com.upedge.common.base.Page;
import com.upedge.pms.modules.purchase.entity.PurchaseOrderTracking;

import java.util.List;

/**
 * @author gx
 */
public interface PurchaseOrderTrackingService{

    List<PurchaseOrderTracking> selectByTrackCode(String trackCode);

    List<PurchaseOrderTracking> selectByOrderId(Long orderId);


    void updateOrderTrackingLatestUpdateInfo(Long orderId,List<AlibabaLogisticsOpenPlatformLogisticsTrace> alibabaLogisticsOpenPlatformLogisticsTraces);

    List<PurchaseOrderTracking> selectByOrderIds(List<Long> orderIds);

    PurchaseOrderTracking selectByPrimaryKey(Long id);

    int deleteByPrimaryKey(Long id);

    int updateByPrimaryKey(PurchaseOrderTracking record);

    int updateByPrimaryKeySelective(PurchaseOrderTracking record);

    int insert(PurchaseOrderTracking record);

    int insertByBatch(List<PurchaseOrderTracking> records);

    int insertSelective(PurchaseOrderTracking record);

    List<PurchaseOrderTracking> select(Page<PurchaseOrderTracking> record);

    long count(Page<PurchaseOrderTracking> record);
}

