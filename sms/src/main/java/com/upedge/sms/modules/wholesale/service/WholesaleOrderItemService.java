package com.upedge.sms.modules.wholesale.service;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.base.Page;
import com.upedge.sms.modules.wholesale.entity.WholesaleOrderItem;
import com.upedge.sms.modules.wholesale.request.WholesaleOrderItemUpdateDischargeQuantityRequest;

import java.util.List;

/**
 * @author gx
 */
public interface WholesaleOrderItemService{

    BaseResponse customUpdateDischargeQuantity(WholesaleOrderItemUpdateDischargeQuantityRequest request);

    int updateDischargeQuantityById(Long id, Integer dischargeQuantity);

    List<WholesaleOrderItem> selectByOrderId(Long orderId);

    WholesaleOrderItem selectByPrimaryKey(Long id);

    int deleteByPrimaryKey(Long id);

    int updateByPrimaryKey(WholesaleOrderItem record);

    int updateByPrimaryKeySelective(WholesaleOrderItem record);

    int insert(WholesaleOrderItem record);

    int insertByBatch(List<WholesaleOrderItem> wholesaleOrderItems);

    int insertSelective(WholesaleOrderItem record);

    List<WholesaleOrderItem> select(Page<WholesaleOrderItem> record);

    long count(Page<WholesaleOrderItem> record);
}

