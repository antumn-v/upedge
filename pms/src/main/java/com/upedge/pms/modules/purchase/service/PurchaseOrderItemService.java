package com.upedge.pms.modules.purchase.service;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.base.Page;
import com.upedge.common.model.user.vo.Session;
import com.upedge.pms.modules.purchase.dto.PurchaseOrderListDto;
import com.upedge.pms.modules.purchase.entity.PurchaseOrderItem;
import com.upedge.pms.modules.purchase.request.PurchaseOrderItemDeleteRequest;
import com.upedge.pms.modules.purchase.request.PurchaseOrderItemUpdatePriceRequest;
import com.upedge.pms.modules.purchase.request.PurchaseOrderItemUpdateQuantityRequest;

import java.util.List;

/**
 * @author gx
 */
public interface PurchaseOrderItemService{

    List<PurchaseOrderItem> selectGroupByPurchaseSku(Long orderId);

    int updateRefundQuantityById(Long id,Integer refundQuantity);

    List<PurchaseOrderItem> selectByIds(List<Long> ids,Long orderId);

    int updateStateByOrderIdAndPurchaseLink(Long orderId, List<String> purchaseLinks,Integer state);

    int updateStateInitByOrderId(Long orderId);

    BaseResponse deleteItem(PurchaseOrderItemDeleteRequest request, Session session);

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

