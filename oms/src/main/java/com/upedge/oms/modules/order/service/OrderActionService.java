package com.upedge.oms.modules.order.service;

import com.upedge.common.exception.CustomerException;
import com.upedge.common.model.ship.vo.ShipDetail;
import com.upedge.oms.modules.order.entity.Order;
import com.upedge.oms.modules.order.request.MergeOrderRequest;
import com.upedge.oms.modules.order.request.SplitNormalOrderRequest;
import com.upedge.oms.modules.order.vo.SameAddressOrderVo;

import java.util.List;

public interface OrderActionService {

    /**
     * 获取同一客户地址相同的订单
     * @param customerId
     * @return
     */
    List<SameAddressOrderVo> selectSameAddressOrderByStore(Long customerId);

    List<ShipDetail> mergeOrderShipList(List<Long> orderIds);

    String splitNormalOrder(Long orderId, SplitNormalOrderRequest request) throws CustomerException;

    String mergeNormalOrder(MergeOrderRequest request);

    String restoreSplitOrder(Order order) throws CustomerException;

    String revertMergedOrder(Order order);
}
