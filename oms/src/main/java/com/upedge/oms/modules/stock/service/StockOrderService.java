package com.upedge.oms.modules.stock.service;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.base.Page;
import com.upedge.common.model.order.PaymentDetail;
import com.upedge.common.model.product.VariantDetail;
import com.upedge.common.model.product.VariantQuantity;
import com.upedge.common.model.user.vo.Session;
import com.upedge.oms.modules.stock.entity.StockOrder;
import com.upedge.oms.modules.stock.request.StockOrderItemUpdatePurchaseNoRequest;
import com.upedge.oms.modules.stock.request.StockOrderListRequest;
import com.upedge.oms.modules.stock.request.StockOrderUpdateShipRequest;
import com.upedge.oms.modules.stock.request.StockOrderUpdateTrackRequest;
import com.upedge.common.model.oms.stock.StockOrderVo;

import java.util.List;

/**
 * @author author
 */
public interface StockOrderService{

    BaseResponse createPurchaseOrder(Long orderId);

    List<Long> selectUploadSaiheFailedIds();

    BaseResponse invoiceDetail(Long paymentId);

    BaseResponse confirmShipReview(Long orderId,Session session);

    BaseResponse updateTrack(StockOrderUpdateTrackRequest request, Session session);

    BaseResponse orderConfirmReceipt(Long orderId,Session session);

    BaseResponse orderConfirmReceipt(StockOrderVo stockOrderVo);

    BaseResponse rejectShipReview(Long orderId,Session session);

    BaseResponse updateShipDetail(Session session, StockOrderUpdateShipRequest request);

    BaseResponse excelImportStockOrders(Session session, List<VariantQuantity> variantQuantities);

    BaseResponse createPaypalOrder(List<Long> ids, Session session);

    List<StockOrderVo> selectOrderByIds(List<Long> ids);

    StockOrderVo selectOrderById(Long orderId);

    BaseResponse updateOrderItemPurchaseNo(StockOrderItemUpdatePurchaseNoRequest request);

    boolean refreshOrderDetail(Long orderId);

    int updateOrderPayState(StockOrder order);

    void updatePriceByVariantId(VariantDetail variantDetail);

    List<StockOrderVo> selectOrderList(StockOrderListRequest request);

    Long countOrderList(StockOrderListRequest request);

    int updatePayStateByPaymentId(Long paymentId,
                                  Integer payState);

    PaymentDetail orderPaidByPaypal(Long paymentId, Long customerId, Long userId);

    PaymentDetail payOrderByBalance(Long id, Session session);

    StockOrder selectByPrimaryKey(Long id);

    int deleteByPrimaryKey(Long id);

    int updateByPrimaryKey(StockOrder record);

    int updateByPrimaryKeySelective(StockOrder record);

    int insert(StockOrder record);

    int insertSelective(StockOrder record);

    List<StockOrder> select(Page<StockOrder> record);

    long count(Page<StockOrder> record);

    void sendSavePaymentDetailMessage(PaymentDetail detail);

    /**
     * 根据paymentId 查询支付号下的所有订单
     * @param paymentId
     * @return
     */
    List<StockOrder> selectStockOrderByPaymentId(Long paymentId);

    void payOrderAsync(PaymentDetail paymentDetail);
}

