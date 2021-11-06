package com.upedge.oms.modules.statistics.service;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.exception.CustomerException;
import com.upedge.oms.modules.statistics.request.InvoiceDetailRequest;
import com.upedge.oms.modules.statistics.request.InvoiceListRequest;
import com.upedge.oms.modules.statistics.request.InvoiceSearchRequest;
import com.upedge.oms.modules.statistics.vo.InvoiceDetailVo;

public interface InvoiceService {

    BaseResponse customerInvoiceList(InvoiceListRequest request);

    InvoiceDetailVo customerInvoiceDetail(InvoiceDetailRequest request);

    InvoiceDetailVo customerInvoiceSearch(InvoiceSearchRequest request);

    String customerSearchInvoice(InvoiceSearchRequest request) throws CustomerException;
}
