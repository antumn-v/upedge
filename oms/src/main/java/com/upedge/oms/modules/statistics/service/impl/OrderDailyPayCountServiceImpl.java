package com.upedge.oms.modules.statistics.service.impl;

import com.alibaba.fastjson.JSON;
import com.upedge.common.base.BaseResponse;
import com.upedge.common.base.Page;
import com.upedge.common.constant.ResultCode;
import com.upedge.common.feign.UmsFeignClient;
import com.upedge.common.model.order.request.CustomerOrderDailyCountUpdateRequest;
import com.upedge.common.model.order.request.OrderDailyCountRequest;
import com.upedge.common.model.order.vo.OrderDailyCountVo;
import com.upedge.common.model.statistics.request.OrderStatisticsRequest;
import com.upedge.common.model.statistics.vo.CustomerOrderStatisticsVo;
import com.upedge.common.model.statistics.vo.ManagerOrderStatisticsVo;
import com.upedge.common.model.statistics.vo.OrderStatisticsVo;
import com.upedge.common.utils.IdGenerate;
import com.upedge.common.utils.ListUtils;
import com.upedge.oms.modules.order.dao.OrderDao;
import com.upedge.oms.modules.order.entity.Order;
import com.upedge.oms.modules.order.vo.OrderVo;
import com.upedge.oms.modules.redis.OmsRedisService;
import com.upedge.oms.modules.statistics.dao.OrderDailyPayCountDao;
import com.upedge.oms.modules.statistics.dto.DashboardOrderDto;
import com.upedge.oms.modules.statistics.entity.OrderDailyPayCount;
import com.upedge.oms.modules.statistics.entity.OrderDailyRefundCount;
import com.upedge.oms.modules.statistics.service.OrderDailyPayCountService;
import com.upedge.oms.modules.stock.dao.StockOrderDao;
import com.upedge.oms.modules.stock.entity.StockOrder;
import com.upedge.oms.modules.wholesale.dao.WholesaleOrderDao;
import com.upedge.oms.modules.wholesale.entity.WholesaleOrder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class OrderDailyPayCountServiceImpl implements OrderDailyPayCountService {

    @Autowired
    private OrderDailyPayCountDao orderDailyPayCountDao;

    @Autowired
    OrderDao orderDao;

    @Autowired
    WholesaleOrderDao wholesaleOrderDao;

    @Autowired
    StockOrderDao stockOrderDao;

    @Autowired
    UmsFeignClient umsFeignClient;

    @Autowired
    OmsRedisService omsRedisService;

    @Autowired
    RedisTemplate redisTemplate;

    /**
     *
     */
    @Transactional
    public int deleteByPrimaryKey(Long id) {
        OrderDailyPayCount record = new OrderDailyPayCount();
        record.setId(id);
        return orderDailyPayCountDao.deleteByPrimaryKey(record);
    }

    /**
     *
     */
    @Transactional
    public int insert(OrderDailyPayCount record) {
        return orderDailyPayCountDao.insert(record);
    }

    /**
     *
     */
    @Transactional
    public int insertSelective(OrderDailyPayCount record) {
        return orderDailyPayCountDao.insert(record);
    }

    @Override
    public List<OrderStatisticsVo> selectCustomerOrderStatisticsByDate(OrderStatisticsRequest request) {
        return orderDailyPayCountDao.selectCustomerOrderStatisticsByDate(request);
    }

    @Override
    public List<ManagerOrderStatisticsVo> managerOrderStatistics(OrderStatisticsRequest request) {
        return orderDailyPayCountDao.selectManagerOrderStatistics(request);
    }

    @Override
    public List<CustomerOrderStatisticsVo> customerOrderStatistics(OrderStatisticsRequest request) {
        return orderDailyPayCountDao.selectCustomerOrderStatistics(request);
    }

//    @Transactional
    @Override
    public void updateCustomerOrderDailyCount(CustomerOrderDailyCountUpdateRequest customerOrderDailyCountUpdateRequest) {
        Long customerId = customerOrderDailyCountUpdateRequest.getCustomerId();
        Long paymentId = customerOrderDailyCountUpdateRequest.getPaymentId();
        Date payTime = customerOrderDailyCountUpdateRequest.getPayTime();
        Integer orderType = customerOrderDailyCountUpdateRequest.getOrderType();
        //订单类型  备库 = 1，普通 = 2，批发 = 3
        OrderDailyPayCount orderDailyPayCount = null;
        switch (orderType) {
            case 1:
                orderDailyPayCount = updateCustomerStockOrderDailyCount(customerId,paymentId,payTime);
                break;
            case 2:
                orderDailyPayCount = updateCustomerNormalOrderDailyCount(customerId,paymentId,payTime);
                break;
            case 3:
                orderDailyPayCount = updateCustomerWholesaleOrderDailyCount(customerId, paymentId, payTime);
                break;
            default:
                break;
        }
        if(null == orderDailyPayCount){
            return;
        }
        orderDailyPayCount.setBenefitPayAmount(customerOrderDailyCountUpdateRequest.getRebate());
//        String managerCode = (String) redisTemplate.opsForHash().get(RedisKey.HASH_CUSTOMER_MANAGER_RELATE, String.valueOf(orderDailyPayCount.getCustomerId()));
//        if (StringUtils.isBlank(managerCode)){
//            managerCode = "system";
//        }
//        orderDailyPayCount.setManagerCode(managerCode);
        OrderDailyPayCount dailyPayCount = orderDailyPayCountDao.selectByCustomerOrderTypeAndDate(customerId,payTime,orderType);
        if(null != dailyPayCount){
            orderDailyPayCount.setId(dailyPayCount.getId());
            orderDailyPayCountDao.increaseOrderDailyCountById(orderDailyPayCount);
        }else {
            orderDailyPayCount.setId(IdGenerate.nextId());
            orderDailyPayCountDao.insert(orderDailyPayCount);
        }
    }

    private OrderDailyPayCount updateCustomerWholesaleOrderDailyCount(Long customerId, Long paymentId, Date payTime){
        Page<WholesaleOrder> page = new Page<>();
        WholesaleOrder wholesaleOrder = new WholesaleOrder();
        wholesaleOrder.setPayState(1);
        wholesaleOrder.setRefundState(0);
        wholesaleOrder.setPaymentId(paymentId);
        page.setT(wholesaleOrder);
        page.setPageSize(null);
        List<WholesaleOrder> wholesaleOrders = wholesaleOrderDao.selectPage(page);
        if(ListUtils.isEmpty(wholesaleOrders)){
            return null;
        }
        BigDecimal productAmount = BigDecimal.ZERO;
        BigDecimal fixFee = BigDecimal.ZERO;
        BigDecimal shipPrice = BigDecimal.ZERO;
        BigDecimal vatAmount = BigDecimal.ZERO;
        BigDecimal serviceFee = BigDecimal.ZERO;
        BigDecimal dischargeAmount = BigDecimal.ZERO;
        for (WholesaleOrder order : wholesaleOrders) {
            productAmount = productAmount.add(order.getProductAmount());
            fixFee = fixFee.add(order.getFixFee());
            shipPrice = shipPrice.add(order.getShipPrice());
            vatAmount = vatAmount.add(order.getVatAmount());
            dischargeAmount = dischargeAmount.add(order.getProductDischargeAmount());
            serviceFee = serviceFee.add(order.getServiceFee());
        }
        OrderDailyPayCount orderDailyPayCount = new OrderDailyPayCount(productAmount,fixFee,shipPrice,serviceFee,vatAmount,dischargeAmount,payTime,customerId,3);
        orderDailyPayCount.setOrderPayCount(wholesaleOrders.size());
        return orderDailyPayCount;

    }

    private OrderDailyPayCount updateCustomerNormalOrderDailyCount(Long customerId, Long paymentId, Date payTime){
        Page<Order> page = new Page<>();
        Order order = new Order();
        OrderVo orderVo = new OrderVo();
        orderVo.setPayState(1);
        orderVo.setRefundState(0);
        orderVo.setPaymentId(paymentId);
        BeanUtils.copyProperties(orderVo,order);
        page.setT(order);
        page.setPageSize(null);
        List<Order> orders = orderDao.selectPage(page);
        if(ListUtils.isEmpty(orders)){
            return null;
        }
        BigDecimal productAmount = BigDecimal.ZERO;
        BigDecimal fixFee = BigDecimal.ZERO;
        BigDecimal serviceFee = BigDecimal.ZERO;
        BigDecimal shipPrice = BigDecimal.ZERO;
        BigDecimal vatAmount = BigDecimal.ZERO;
        BigDecimal dischargeAmount = BigDecimal.ZERO;
        for (Order o : orders) {
            serviceFee = serviceFee.add(o.getServiceFee());
            productAmount = productAmount.add(o.getProductAmount());
            fixFee = fixFee.add(o.getFixFee());
            shipPrice = shipPrice.add(o.getShipPrice());
            vatAmount = vatAmount.add(o.getVatAmount());
            dischargeAmount = dischargeAmount.add(o.getProductDischargeAmount());
        }
        OrderDailyPayCount orderDailyPayCount = new OrderDailyPayCount(productAmount,fixFee,shipPrice,serviceFee,vatAmount,dischargeAmount,payTime,customerId,2);
        orderDailyPayCount.setOrderPayCount(orders.size());
        return orderDailyPayCount;
    }

    private OrderDailyPayCount updateCustomerStockOrderDailyCount(Long customerId, Long paymentId, Date payTime){
        BigDecimal productAmount = BigDecimal.ZERO;
        BigDecimal fixFee = BigDecimal.ZERO;
        Page<StockOrder> page = new Page<>();
        StockOrder stockOrder = new StockOrder();
        stockOrder.setPayState(1);
        stockOrder.setRefundState(0);
        stockOrder.setPaymentId(paymentId);
        page.setT(stockOrder);
        page.setPageSize(null);
        List<StockOrder> stockOrders = stockOrderDao.select(page);
        if(ListUtils.isEmpty(stockOrders)){
            return null;
        }
        for (StockOrder order : stockOrders) {
            productAmount = productAmount.add(order.getAmount());
            fixFee = fixFee.add(order.getPaypalFee() == null? BigDecimal.ZERO:order.getPaypalFee());
        }
        OrderDailyPayCount orderDailyPayCount = new OrderDailyPayCount(productAmount,fixFee,payTime,customerId,1);
        orderDailyPayCount.setOrderPayCount(stockOrders.size());
        return orderDailyPayCount;
    }

    /**
     *
     */
    @Override
    public OrderDailyPayCount selectByPrimaryKey(Long id) {
        OrderDailyPayCount record = new OrderDailyPayCount();
        record.setId(id);
        return orderDailyPayCountDao.selectByPrimaryKey(record);
    }

    /**
     *
     */
    @Transactional
    public int updateByPrimaryKeySelective(OrderDailyPayCount record) {
        return orderDailyPayCountDao.updateByPrimaryKeySelective(record);
    }

    /**
     *
     */
    @Transactional
    public int updateByPrimaryKey(OrderDailyPayCount record) {
        return orderDailyPayCountDao.updateByPrimaryKey(record);
    }

    /**
     *
     */
    public List<OrderDailyPayCount> select(Page<OrderDailyPayCount> record) {
        record.initFromNum();
        return orderDailyPayCountDao.select(record);
    }

    /**
     *
     */
    public long count(Page<OrderDailyPayCount> record) {
        return orderDailyPayCountDao.count(record);
    }

    @Override
    public OrderDailyPayCount  selectRefundCountByCustomerOrderTypeAndDate(Long customerId, Date refundTime, Integer orderType) {
        return orderDailyPayCountDao.selectByCustomerOrderTypeAndDate(customerId,refundTime,orderType);
    }


    /**
     * 退款操作统计  插入  使用 INSERT IGNORE INTO
     * @param orderDailyRefundCount
     * @return
     */
    @Override
    public int insertRefundCount(OrderDailyRefundCount orderDailyRefundCount) {
        return orderDailyPayCountDao.insertRefundCount(orderDailyRefundCount);
    }

    @Override
    public int updateRefundCount(OrderDailyRefundCount orderDailyRefundCount) {
        return orderDailyPayCountDao.updateRefundCount(orderDailyRefundCount);
    }

    /**
     * 当月发货包裹订单返点
     * @param startDay
     * @param endDay
     * @param orderSourceId
     * @return
     */
    @Override
    public BigDecimal totalPackageOrderBenefits(String startDay, String endDay, Long orderSourceId) {
        String managerCode = null;
        if (orderSourceId != -1){
            BaseResponse baseResponse   =  umsFeignClient.getManagerByOrderSourceId(orderSourceId);
            if (baseResponse.getCode()  == ResultCode.SUCCESS_CODE){
                managerCode  = JSON.toJSONString(baseResponse.getData());
            }
        }
        return orderDailyPayCountDao.totalPackageOrderBenefits(startDay,endDay, managerCode);
    }

    /**
     * 当月发货包裹批发订单返点
     * @param startDay
     * @param endDay
     * @param orderSourceId
     * @return
     */
    @Override
    public BigDecimal totalPackageWholeSaleOrderBenefits(String startDay, String endDay, Long orderSourceId) {
        String managerCode = null;
        if (orderSourceId != -1){
            BaseResponse baseResponse   =  umsFeignClient.getManagerByOrderSourceId(orderSourceId);
            if (baseResponse.getCode()  == ResultCode.SUCCESS_CODE){
                managerCode  = (String) baseResponse.getData();
            }
        }
        return orderDailyPayCountDao.totalPackageWholeSaleOrderBenefits(startDay,endDay, managerCode);
    }

    @Override
    public List<DashboardOrderDto> paidOrderDataByDay(String startDay, String endDay, String userManager, int orderType) {
        return orderDailyPayCountDao.paidOrderDataByDay(startDay,endDay,userManager,orderType);
    }

    @Override
    public List<OrderDailyCountVo> selectTodayPaidOrderCount(OrderDailyCountRequest request) {

        return orderDailyPayCountDao.selectTodayPaidOrderCount(request);
    }

    @Override
    public List<OrderDailyCountVo> selectThirtydayPaidOrderCount(OrderDailyCountRequest request) {
        return orderDailyPayCountDao.selectThirtydayPaidOrderCount(request);
    }

    @Override
    public List<OrderDailyCountVo> selectCurrMonthSaleAmount(OrderDailyCountRequest request) {
        return orderDailyPayCountDao.selectCurrMonthSaleAmount(request);
    }


}