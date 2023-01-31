package com.upedge.oms.modules.stock.service;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.base.Page;
import com.upedge.common.model.pms.dto.StockPurchaseOrderItemReceiveDto;
import com.upedge.oms.modules.stock.entity.StockOrderItem;

import java.util.List;

/**
 * @author author
 */
public interface StockOrderItemService{

    BaseResponse updateInboundQuantity(StockPurchaseOrderItemReceiveDto stockPurchaseOrderItemReceiveDto);

    int updatePurchaseInfo(Long variantId,String purchaseSku,String supplierName);

    StockOrderItem selectByPrimaryKey(Long id);

    int deleteByPrimaryKey(Long id);

    int updateByPrimaryKey(StockOrderItem record);

    int updateByPrimaryKeySelective(StockOrderItem record);

    int insert(StockOrderItem record);

    int insertSelective(StockOrderItem record);

    List<StockOrderItem> select(Page<StockOrderItem> record);

    long count(Page<StockOrderItem> record);
}

