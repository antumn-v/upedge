package com.upedge.pms.modules.purchase.dao;

import com.upedge.common.base.Page;
import com.upedge.pms.modules.purchase.dto.PurchaseOrderListDto;
import com.upedge.pms.modules.purchase.entity.PurchaseOrderItem;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author gx
 */
public interface PurchaseOrderItemDao {

    int updatePriceBySpecId(@Param("items") List<PurchaseOrderItem> items);

    List<PurchaseOrderItem> selectByOrderId(Long orderId);

    List<PurchaseOrderItem> selectByOrderIds(@Param("orderIds") List<Long> orderIds);

    List<PurchaseOrderItem> selectByOrderListDto(PurchaseOrderListDto purchaseOrderListDto);

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
