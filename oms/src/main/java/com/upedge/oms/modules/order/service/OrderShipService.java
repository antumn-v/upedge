package com.upedge.oms.modules.order.service;

import com.upedge.common.model.ship.vo.ShipDetail;
import com.upedge.common.model.user.vo.Session;

import java.util.List;

public interface OrderShipService {


    List<ShipDetail> orderOverseasWarehouseShipMethods(Long orderId, Session session);
}
