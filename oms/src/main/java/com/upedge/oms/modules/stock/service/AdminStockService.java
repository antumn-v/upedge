package com.upedge.oms.modules.stock.service;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.exception.CustomerException;
import com.upedge.common.model.user.vo.Session;
import com.upedge.oms.modules.stock.request.*;
import com.upedge.oms.modules.stock.response.*;

public interface AdminStockService {

    StockOrderListResponse stockList(AdminStockOrderListRequest request, Session session);

    StockOrderListResponse historyStockOrder(AdminStockOrderListRequest request, Session session);

    BaseResponse createProcurement(CreateProcurementRequest request) throws CustomerException;

    BaseResponse refreshSaiheSku(Long id);

    BaseResponse updateSaiheSku(UpdateSaiheSkuRequest request);

    StockOrderRefundListResponse refundOrderList(StockOrderRefundListRequest request, Session session);

    StockOrderRefundListResponse historyRefundOrder(StockOrderRefundListRequest request, Session session);

    ApplyStockOrderRefundResponse applyRefund(ApplyStockOrderRefundRequest request, Session session)throws CustomerException ;

    CustomerProductStockListResponse productStockList(CustomerProductStockListRequest request);

    BaseResponse stockRecordList(CustomerStockRecordListRequest request);

    BaseResponse confirmRefund(ConfirmStockOrderRefundRequest request, Session session)throws CustomerException ;

    BaseResponse rejectApply(RejectApplyStockOrderRefundRequest request, Session session)throws CustomerException ;
}
