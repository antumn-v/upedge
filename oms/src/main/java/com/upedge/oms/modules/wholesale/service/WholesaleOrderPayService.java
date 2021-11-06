package com.upedge.oms.modules.wholesale.service;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.model.order.PaymentDetail;
import com.upedge.common.model.user.vo.Session;
import com.upedge.oms.modules.common.vo.OrderPayCheckResultVo;
import com.upedge.oms.modules.order.vo.ItemDischargeQuantityVo;
import com.upedge.oms.modules.wholesale.vo.WholesaleOrderAppVo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface WholesaleOrderPayService {

    //库存，运费
    OrderPayCheckResultVo orderPayCheck(Long paymentId, BigDecimal amount, List<ItemDischargeQuantityVo> dischargeQuantityVos, Long customerId, String paypal);

    boolean orderPayRollBack(Long paymentId, Long customerId, List<ItemDischargeQuantityVo> dischargeQuantityVos);

    List<WholesaleOrderAppVo> orderPayList(List<Long> ids, Session session);

    String payOrderByBalance(Long paymentId, BigDecimal amount, Session session, Date payTime);

    BaseResponse createPaypalOrder(Session session, BigDecimal amount, Long paymentId);

    PaymentDetail payOrderByPaypal(Long userId, Long customerId, Long paymentId);


    void processAfterPaid(Long paymentId, Long customerId, Long userId, Date payTime);

    int updatePayStateByPaymentId(Long paymentId, Integer payState);

    int updatePaymentIdByIds(Long paymentId, List<Long> ids);
}
