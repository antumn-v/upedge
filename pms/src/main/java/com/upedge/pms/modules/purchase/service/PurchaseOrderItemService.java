package com.upedge.pms.modules.purchase.service;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.base.Page;
import com.upedge.pms.modules.purchase.dto.PurchaseOrderListDto;
import com.upedge.pms.modules.purchase.entity.PurchaseOrderItem;
import com.upedge.pms.modules.purchase.request.PurchaseOrderItemUpdatePriceRequest;
import com.upedge.pms.modules.purchase.request.PurchaseOrderItemUpdateQuantityRequest;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author gx
 */
public interface PurchaseOrderItemService{

    int updatePriceBySpecId( List<PurchaseOrderItem> items);

    BaseResponse updatePriceById(PurchaseOrderItemUpdatePriceRequest request);

    BaseResponse updateQuantityById(PurchaseOrderItemUpdateQuantityRequest request);

    List<PurchaseOrderItem> selectByOrderListDto(PurchaseOrderListDto purchaseOrderListDto);

    List<PurchaseOrderItem> selectByOrderId(Long orderId);
    List<PurchaseOrderItem> selectByOrderIds(List<Long> orderIds);

    PurchaseOrderItem selectByPrimaryKey(Long id);

    int deleteByPrimaryKey(Long id);

    int updateByPrimaryKey(PurchaseOrderItem record);

    int updateByPrimaryKeySelective(PurchaseOrderItem record);

    int insert(PurchaseOrderItem record);

    int insertByBatch(List<PurchaseOrderItem> records);

    int insertSelective(PurchaseOrderItem record);

    List<PurchaseOrderItem> select(Page<PurchaseOrderItem> record);

    long count(Page<PurchaseOrderItem> record);
}

