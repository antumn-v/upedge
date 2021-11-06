package com.upedge.oms.modules.order.service.impl;

import com.upedge.common.base.Page;
import com.upedge.common.utils.ListUtils;
import com.upedge.oms.modules.order.dao.*;
import com.upedge.oms.modules.order.entity.*;
import com.upedge.oms.modules.order.service.OrderProfitService;
import com.upedge.oms.modules.order.service.StoreOrderRefundService;
import com.upedge.oms.modules.order.vo.OrderProfitVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service
public class OrderProfitServiceImpl implements OrderProfitService {

    @Autowired
    private OrderProfitDao orderProfitDao;

    @Autowired
    OrderDao orderDao;

    @Autowired
    OrderRefundDao orderRefundDao;

    @Autowired
    StoreOrderRelateDao storeOrderRelateDao;

    @Autowired
    StoreOrderRefundDao storeOrderRefundDao;
    
    @Autowired
    StoreOrderItemDao storeOrderItemDao;

    @Autowired
    StoreOrderRefundService storeOrderRefundService;

    @Autowired
    StoreOrderOtherFeeRecordDao storeOrderOtherFeeRecordDao;

    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    /**
     *
     */
    @Transactional
    public int deleteByPrimaryKey(Long orderId) {
        OrderProfit record = new OrderProfit();
        record.setOrderId(orderId);
        return orderProfitDao.deleteByPrimaryKey(record);
    }

    /**
     *
     */
    @Transactional
    public int insert(OrderProfit record) {
        return orderProfitDao.insert(record);
    }

    /**
     *
     */
    @Transactional
    public int insertSelective(OrderProfit record) {
        return orderProfitDao.insert(record);
    }

    @Override
    public OrderProfit updateOrderProfit(Long orderId) {
        Order order = orderDao.selectByPrimaryKey(orderId);
        if(null == order || 0 ==order.getPayState() || 1 == order.getOrderType()){
            return null;
        }
        Date date = new Date();

        List<StoreOrderOtherFeeRecord> otherFeeRecords = new ArrayList<>();
        List<StoreOrderRelate> storeOrderRelates = storeOrderRelateDao.selectByOrderId(orderId);
        BigDecimal refundAmount = BigDecimal.ZERO;
        BigDecimal otherFee = BigDecimal.ZERO;
        for (StoreOrderRelate relate: storeOrderRelates) {
            Long storeOrderId = relate.getStoreOrderId();
            if(0 != relate.getFinancialStatus()){
                BigDecimal fee = BigDecimal.ZERO;
                List<StoreOrderRefund> storeOrderRefunds = storeOrderRefundDao.selectByStoreOrderId(storeOrderId);
                if(ListUtils.isEmpty(storeOrderRefunds)){
                    storeOrderRefunds = storeOrderRefundService.getStoreOrderRefund(storeOrderId);
                }

                for (StoreOrderRefund refund: storeOrderRefunds) {
                    refundAmount = refundAmount.add(refund.getUsdAmount());
                    otherFee = otherFee.add(refund.getOtherFee());
                    fee = fee.add(refund.getOtherFee());
                }
                StoreOrderOtherFeeRecord orderOtherFeeRecord = storeOrderOtherFeeRecordDao.selectByPrimaryKey(storeOrderId);
                if(null != orderOtherFeeRecord){
                    orderOtherFeeRecord = new StoreOrderOtherFeeRecord();
                    orderOtherFeeRecord.setCreateTime(date);
                    orderOtherFeeRecord.setUpedgeOrderId(orderId);
                    orderOtherFeeRecord.setStoreOrderId(storeOrderId);
                    orderOtherFeeRecord.setStoreOrderOtherFee(fee);
                    otherFeeRecords.add(orderOtherFeeRecord);
                }
            }
        }

        BigDecimal orderRefundAmount = BigDecimal.ZERO;
        if(order.getRefundState() > 1){
            orderRefundAmount = orderRefundDao.selectRefundAmountByOrderId(orderId);
        }

        BigDecimal payAmount = order.getProductAmount().add(order.getShipPrice()).add(order.getVatAmount());
        BigDecimal storeOrderItemAmount = storeOrderItemDao.selectItemAmountByOrderId(orderId);
        OrderProfit profit = new OrderProfit();
        profit.setOrderId(orderId);
        profit.setStoreOrderItemAmount(storeOrderItemAmount);
        profit.setOrderPayAmount(payAmount);
        profit.setOrderRefundAmount(orderRefundAmount);
        profit.setCreateTime(date);
        profit.setUpdateTime(date);
        profit.setStoreOrderOtherFee(otherFee);
        profit.setStoreOrderRefundAmount(refundAmount);
        orderProfitDao.replaceOrderProfit(profit);
        if(ListUtils.isNotEmpty(otherFeeRecords)){
            storeOrderOtherFeeRecordDao.insertByBatch(otherFeeRecords);
        }
        return profit;
    }

    /**
     *
     */
    @Override
    public OrderProfitVo selectByPrimaryKey(Long orderId){
        OrderProfit record = new OrderProfit();
        record.setOrderId(orderId);
        record = orderProfitDao.selectByPrimaryKey(record);
        if(null == record){
            record = updateOrderProfit(orderId);
        }
        OrderProfitVo profitVo = new OrderProfitVo();
        if(null != record){
            Order order = orderDao.selectByPrimaryKey(orderId);

            profitVo.setOrderProductCost(order.getProductAmount());
            profitVo.setOrderShipCost(order.getShipPrice());
            profitVo.setStoreOrderRefund(record.getStoreOrderRefundAmount());
            profitVo.setOrderRefund(record.getOrderRefundAmount());
            if(record.getStoreOrderItemAmount() == null){
                record.setStoreOrderItemAmount(BigDecimal.ZERO);
            }
            profitVo.setStoreOrderIncome(record.getStoreOrderItemAmount().add(record.getStoreOrderOtherFee()));
            profitVo.initProfit();
        }
        return profitVo;
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKeySelective(OrderProfit record) {
        return orderProfitDao.updateByPrimaryKeySelective(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKey(OrderProfit record) {
        return orderProfitDao.updateByPrimaryKey(record);
    }

    /**
    *
    */
    public List<OrderProfit> select(Page<OrderProfit> record){
        record.initFromNum();
        return orderProfitDao.select(record);
    }

    /**
    *
    */
    public long count(Page<OrderProfit> record){
        return orderProfitDao.count(record);
    }

}