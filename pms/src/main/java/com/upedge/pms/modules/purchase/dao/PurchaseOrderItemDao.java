package com.upedge.pms.modules.purchase.dao;

import com.upedge.common.base.Page;
import com.upedge.pms.modules.purchase.entity.PurchaseOrderItem;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author gx
 */
public interface PurchaseOrderItemDao {

    List<PurchaseOrderItem> selectByOrderId(Long orderId);

    List<PurchaseOrderItem> selectByOrderIds(@Param("orderIds") List<Long> orderIds);

    PurchaseOrderItem selectByPrimaryKey(PurchaseOrderItem record);

    int deleteByPrimaryKey(PurchaseOrderItem record);

    int updateByPrimaryKey(PurchaseOrderItem record);

    int updateByPrimaryKeySelective(PurchaseOrderItem record);

    int insert(PurchaseOrderItem record);

    int insertSelective(PurchaseOrderItem record);

    int insertByBatch(List<PurchaseOrderItem> list);

    List<PurchaseOrderItem> select(Page<PurchaseOrderItem> record);

    long count(Page<PurchaseOrderItem> record);

}
