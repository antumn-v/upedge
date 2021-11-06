package com.upedge.oms.modules.stock.service;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.model.user.vo.Session;
import com.upedge.oms.modules.stock.request.StockAdviceCreateOrderRequest;

public interface StockAdviceService {

    /**
     * @param session
     * @param fromNum 起始
     * @param pageSize 页数
     * @return
     */
    BaseResponse customerStockAdvice(Session session, Integer fromNum, Integer pageSize);

    BaseResponse stockAdviceCreateOrder(StockAdviceCreateOrderRequest request, Session session);
}
