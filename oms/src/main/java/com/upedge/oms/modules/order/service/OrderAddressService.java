package com.upedge.oms.modules.order.service;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.model.user.vo.Session;
import com.upedge.oms.modules.order.entity.OrderAddress;
import com.upedge.oms.modules.order.entity.StoreOrderAddress;

public interface OrderAddressService {

    void updateByStoreOrderAddress(StoreOrderAddress storeOrderAddress);

    BaseResponse update(OrderAddress orderAddress, Session session);

    BaseResponse managerUpdate(OrderAddress orderAddress, Session session);

}
