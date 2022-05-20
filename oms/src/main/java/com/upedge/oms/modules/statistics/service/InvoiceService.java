package com.upedge.oms.modules.statistics.service;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.exception.CustomerException;
import com.upedge.common.model.account.vo.InvoiceProductVo;
import com.upedge.oms.modules.statistics.request.InvoiceDetailRequest;
import com.upedge.oms.modules.statistics.request.InvoiceListRequest;
import com.upedge.oms.modules.statistics.request.InvoiceSearchRequest;
import com.upedge.oms.modules.statistics.vo.InvoiceDetailVo;

import java.util.List;

public interface InvoiceService {

    InvoiceDetailVo selectStockInvoiceDetailByPaymentId(Long paymentId);

    List<InvoiceProductVo> selectStockInvoiceProductByPaymentId(Long paymentId);

    BaseResponse customerInvoiceList(InvoiceListRequest request);

    InvoiceDetailVo customerInvoiceDetail(InvoiceDetailRequest request);

    InvoiceDetailVo customerInvoiceSearch(InvoiceSearchRequest request);

    String customerSearchInvoice(InvoiceSearchRequest request) throws CustomerException;
}
