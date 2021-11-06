package com.upedge.oms.modules.order.service;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.model.user.vo.Session;
import com.upedge.oms.modules.order.request.OrderCogsSelectRequest;
import com.upedge.oms.modules.order.request.OrderInsightSelectRequest;
import com.upedge.oms.modules.order.request.StoreOrderSaleSelectRequest;

public interface ReportService {

    BaseResponse storeOrderSales(Session session, StoreOrderSaleSelectRequest request);

    BaseResponse customerOrderCogs(Session session, OrderCogsSelectRequest request);

    BaseResponse customerOrderInsights(Session session, OrderInsightSelectRequest request);
}
