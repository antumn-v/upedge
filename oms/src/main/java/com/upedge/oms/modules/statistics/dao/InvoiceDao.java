package com.upedge.oms.modules.statistics.dao;

import com.upedge.oms.modules.statistics.request.InvoiceListRequest;
import com.upedge.oms.modules.statistics.request.InvoiceSearchRequest;
import com.upedge.oms.modules.statistics.vo.InvoiceDetailVo;
import com.upedge.common.model.account.vo.InvoiceProductVo;
import com.upedge.oms.modules.statistics.vo.InvoiceVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface InvoiceDao {

    List<InvoiceVo> selectCustomerInvoiceList(InvoiceListRequest request);

    Long selectCustomerInvoiceCount(InvoiceListRequest request);

    InvoiceDetailVo selectOrderInvoiceDetailByPaymentId(@Param("paymentId") Long paymentId,@Param("paymentIds") List<Long> paymentIds);

    List<InvoiceProductVo> selectOrderInvoiceProductByPaymentId(@Param("paymentId") Long paymentId,@Param("paymentIds") List<Long> paymentIds);

    InvoiceDetailVo selectWholesaleInvoiceDetailByPaymentId(Long paymentId);

    List<InvoiceProductVo> selectWholesaleInvoiceProductByPaymentId(Long paymentId);

    InvoiceDetailVo selectStockInvoiceDetailByPaymentId(Long paymentId);

    List<InvoiceProductVo> selectStockInvoiceProductByPaymentId(Long paymentId);

    InvoiceDetailVo selectOrderInvoiceDetailByTime(InvoiceSearchRequest request);

    List<InvoiceProductVo> selectOrderInvoiceProductByTime(InvoiceSearchRequest request);

    InvoiceDetailVo selectWholesaleInvoiceDetailByTime(InvoiceSearchRequest request);

    List<InvoiceProductVo> selectWholesaleInvoiceProductByTime(InvoiceSearchRequest request);

    InvoiceDetailVo selectStockInvoiceDetailByTime(InvoiceSearchRequest request);

    List<InvoiceProductVo> selectStockInvoiceProductByTime(InvoiceSearchRequest request);
}
