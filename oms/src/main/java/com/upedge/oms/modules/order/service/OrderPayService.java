package com.upedge.oms.modules.order.service;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.exception.CustomerException;
import com.upedge.common.model.order.PaymentDetail;
import com.upedge.common.model.user.vo.Session;
import com.upedge.oms.modules.order.vo.AppOrderVo;
import com.upedge.oms.modules.order.vo.ItemDischargeQuantityVo;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.ExecutionException;

public interface OrderPayService {

    List<AppOrderVo> selectPayOrderListByPaymentId(Long paymentId);

    boolean orderShipPriceCheck(List<AppOrderVo> orders);

    int updatePaymentIdByIds(List<Long> ids, Long paymentId, Long customerId);

    int updatePayStateByPaymentId(Long paymentId, Integer payState);

    boolean orderPayRollback(Long paymentId, Long customerId, List<ItemDischargeQuantityVo> dischargeQuantityVos);

    //检查库存，运费
    BaseResponse orderPayCheck(Long paymentId, BigDecimal amount, List<ItemDischargeQuantityVo> dischargeQuantityVos, Long customerId, String payType);

    String payOrderByBalance(Session session, BigDecimal amount, Long paymentId, List<ItemDischargeQuantityVo> dischargeQuantityVos);

    BaseResponse orderPayList(Long paymnetId, Session session,String warehouseCode) throws ExecutionException, InterruptedException, CustomerException;

    void sendSaveTransactionRecordMessage(Long paymentId, Long customerId, Long userId, Integer payMethod);

    BaseResponse createPaypalOrder(Session session, BigDecimal amount, Long paymentId, List<ItemDischargeQuantityVo> dischargeQuantityVos);

    PaymentDetail payOrderByPaypal(Long userId, Long customerId, Long paymentId);

    void payOrderAsync(Long userId, Long customerId, Long paymentId,Integer payMethod);
}
