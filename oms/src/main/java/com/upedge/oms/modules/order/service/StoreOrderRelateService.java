package com.upedge.oms.modules.order.service;

import com.upedge.oms.modules.order.entity.StoreOrderRelate;

import java.util.List;

public interface StoreOrderRelateService {

    List<StoreOrderRelate> selectUnPaidByStoreOrderId(List<Long> storeOrderIds);

    List<StoreOrderRelate> selectByStoreOrderId(Long storeOrderId);

    int updateCustomerNameByOrderId(Long orderId,
                                    String customerName);
}
