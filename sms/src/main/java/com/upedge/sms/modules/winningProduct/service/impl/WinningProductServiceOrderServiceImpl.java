package com.upedge.sms.modules.winningProduct.service.impl;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.base.Page;
import com.upedge.common.constant.OrderConstant;
import com.upedge.common.constant.OrderType;
import com.upedge.common.constant.ResultCode;
import com.upedge.common.feign.UmsFeignClient;
import com.upedge.common.model.account.AccountPaymentRequest;
import com.upedge.common.model.order.PaymentDetail;
import com.upedge.common.model.order.TransactionDetail;
import com.upedge.common.model.user.vo.Session;
import com.upedge.common.utils.IdGenerate;
import com.upedge.sms.modules.center.entity.ServiceOrder;
import com.upedge.sms.modules.center.service.ServiceOrderService;
import com.upedge.sms.modules.winningProduct.dao.WinningProductServiceOrderDao;
import com.upedge.sms.modules.winningProduct.entity.WinningProductServiceOrder;
import com.upedge.sms.modules.winningProduct.request.WinningProductServiceOrderAddRequest;
import com.upedge.sms.modules.winningProduct.service.WinningProductServiceOrderService;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;


@Service
public class WinningProductServiceOrderServiceImpl implements WinningProductServiceOrderService {

    @Autowired
    private WinningProductServiceOrderDao winningProductServiceOrderDao;

    @Autowired
    private ServiceOrderService serviceOrderService;

    @Autowired
    UmsFeignClient umsFeignClient;

    @Autowired
    ThreadPoolExecutor threadPoolExecutor;

    /**
     *
     */
    @Transactional
    public int deleteByPrimaryKey(Long id) {
        WinningProductServiceOrder record = new WinningProductServiceOrder();
        record.setId(id);
        return winningProductServiceOrderDao.deleteByPrimaryKey(record);
    }

    /**
     *
     */
    @Transactional
    public int insert(WinningProductServiceOrder record) {
        return winningProductServiceOrderDao.insert(record);
    }

    /**
     *
     */
    @Transactional
    public int insertSelective(WinningProductServiceOrder record) {
        return winningProductServiceOrderDao.insert(record);
    }

    @GlobalTransactional
    @Override
    public BaseResponse create(WinningProductServiceOrderAddRequest request, Session session) {
        Long orderId = IdGenerate.nextId();
        WinningProductServiceOrder order = request.toWinningProductServiceOrder();
        if(null == order.getCategory()
        || null == order.getDescription()
        || null == order.getServiceOption()
        || null == order.getPayAmount()){
            return BaseResponse.failed();
        }
        order.setId(orderId);
        order.setCustomerId(session.getCustomerId());
        AccountPaymentRequest accountPaymentRequest = new AccountPaymentRequest(order.getPaymentId(),session.getId(),session.getAccountId(),session.getCustomerId(), OrderType.EXTRA_SERVICE_WINNING_PRODUCT,order.getPayAmount(), BigDecimal.ZERO,0);
        BaseResponse paymentResponse = umsFeignClient.accountPayment(accountPaymentRequest);
        if (paymentResponse.getCode() != ResultCode.SUCCESS_CODE){
            return paymentResponse;
        }
        insert(order);

        ServiceOrder serviceOrder = new ServiceOrder();
        serviceOrder.setServiceTitle(request.getServiceTitle());
        serviceOrder.setId(orderId);
        serviceOrder.setRelateId(orderId);
        serviceOrder.setServiceState(0);
        serviceOrder.setCustomerId(session.getCustomerId());
        serviceOrder.setCreateTime(order.getCreateTime());
        serviceOrder.setPayState(OrderConstant.PAY_STATE_PAID);
        serviceOrder.setRefundState(0);
        serviceOrder.setServiceType(OrderType.EXTRA_SERVICE_WINNING_PRODUCT);
        serviceOrder.setUpdateTime(new Date());
        serviceOrder.setRemark(request.getRemark());
        serviceOrderService.insert(serviceOrder);
        saveTransactionRecordMessage(session.getId(),orderId);
        return BaseResponse.success();
    }

    /**
     *
     */
    public WinningProductServiceOrder selectByPrimaryKey(Long id){
        WinningProductServiceOrder record = new WinningProductServiceOrder();
        record.setId(id);
        return winningProductServiceOrderDao.selectByPrimaryKey(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKeySelective(WinningProductServiceOrder record) {
        return winningProductServiceOrderDao.updateByPrimaryKeySelective(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKey(WinningProductServiceOrder record) {
        return winningProductServiceOrderDao.updateByPrimaryKey(record);
    }

    /**
    *
    */
    public List<WinningProductServiceOrder> select(Page<WinningProductServiceOrder> record){
        record.initFromNum();
        return winningProductServiceOrderDao.select(record);
    }

    /**
    *
    */
    public long count(Page<WinningProductServiceOrder> record){
        return winningProductServiceOrderDao.count(record);
    }


    @Override
    public void saveTransactionRecordMessage(Long userId, Long orderId) {
        WinningProductServiceOrder order = selectByPrimaryKey(orderId);
        if (order.getPayState() != OrderConstant.PAY_STATE_PAID){
            return;
        }
        PaymentDetail detail = new PaymentDetail();
        detail.setPaymentId(order.getPaymentId());
        detail.setUserId(userId);
        detail.setCustomerId(order.getCustomerId());
        detail.setPayMethod(0);
        detail.setPayAmount(order.getPayAmount());
        detail.setOrderType(OrderType.EXTRA_SERVICE_OVERSEA_WAREHOUSE);
        detail.setPayTime(order.getPayTime());

        List<TransactionDetail> transactionDetails = new ArrayList<>();
        TransactionDetail transactionDetail = new TransactionDetail();
        transactionDetail.setOrderId(order.getId());
        transactionDetail.setPaymentId(order.getPaymentId());
        transactionDetail.setPayTime(order.getPayTime());
        transactionDetail.setAmount(order.getPayAmount());
        transactionDetail.setOrderType(OrderType.EXTRA_SERVICE_OVERSEA_WAREHOUSE);
        transactionDetails.add(transactionDetail);

        detail.setOrderTransactions(transactionDetails);

        umsFeignClient.saveTransactionDetails(detail);

    }

}