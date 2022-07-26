package com.upedge.pms.modules.purchase.service;

import com.upedge.pms.modules.purchase.entity.PurchaseOrderTracking;
import com.upedge.common.base.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author gx
 */
public interface PurchaseOrderTrackingService{

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

