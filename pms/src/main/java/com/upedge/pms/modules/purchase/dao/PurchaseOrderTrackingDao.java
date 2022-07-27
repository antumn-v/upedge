package com.upedge.pms.modules.purchase.dao;

import com.upedge.common.base.Page;
import com.upedge.pms.modules.purchase.entity.PurchaseOrderTracking;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author gx
 */
public interface PurchaseOrderTrackingDao {

    List<PurchaseOrderTracking> selectByOrderId(Long orderId);

    List<PurchaseOrderTracking> selectByOrderIds(@Param("orderIds") List<Long> orderIds);

    PurchaseOrderTracking selectByPrimaryKey(PurchaseOrderTracking record);

    int deleteByPrimaryKey(PurchaseOrderTracking record);

    int updateByPrimaryKey(PurchaseOrderTracking record);

    int updateByPrimaryKeySelective(PurchaseOrderTracking record);

    int insert(PurchaseOrderTracking record);

    int insertSelective(PurchaseOrderTracking record);

    int insertByBatch(List<PurchaseOrderTracking> list);

    List<PurchaseOrderTracking> select(Page<PurchaseOrderTracking> record);

    long count(Page<PurchaseOrderTracking> record);

}
