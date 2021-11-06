package com.upedge.oms.modules.statistics.service;

import com.upedge.common.base.Page;
import com.upedge.common.model.order.request.CustomerOrderDailyCountUpdateRequest;
import com.upedge.common.model.order.request.OrderDailyCountRequest;
import com.upedge.common.model.order.vo.OrderDailyCountVo;
import com.upedge.common.model.statistics.request.OrderStatisticsRequest;
import com.upedge.common.model.statistics.vo.CustomerOrderStatisticsVo;
import com.upedge.common.model.statistics.vo.ManagerOrderStatisticsVo;
import com.upedge.oms.modules.statistics.dto.DashboardOrderDto;
import com.upedge.oms.modules.statistics.entity.OrderDailyPayCount;
import com.upedge.oms.modules.statistics.entity.OrderDailyRefundCount;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author author
 */
public interface OrderDailyPayCountService{

    List<ManagerOrderStatisticsVo> managerOrderStatistics(OrderStatisticsRequest request);

    List<CustomerOrderStatisticsVo> customerOrderStatistics(OrderStatisticsRequest request);

    void updateCustomerOrderDailyCount(CustomerOrderDailyCountUpdateRequest customerOrderDailyCountUpdateRequest);

    OrderDailyPayCount selectByPrimaryKey(Long id);

    int deleteByPrimaryKey(Long id);

    int updateByPrimaryKey(OrderDailyPayCount record);

    int updateByPrimaryKeySelective(OrderDailyPayCount record);

    int insert(OrderDailyPayCount record);

    int insertSelective(OrderDailyPayCount record);

    List<OrderDailyPayCount> select(Page<OrderDailyPayCount> record);

    long count(Page<OrderDailyPayCount> record);


    /**
     * 根据  客户id   确认退款时间   ordertype查询  OrderDailyPayCount
     * @param customerId
     * @param refundTime
     * @param orderType
     * @return
     */
    OrderDailyPayCount  selectRefundCountByCustomerOrderTypeAndDate(Long customerId, Date refundTime, Integer orderType);


    /**
     * 退款操作统计  插入  使用 INSERT IGNORE INTO
     * @param orderDailyRefundCount
     * @return
     */
    int insertRefundCount(OrderDailyRefundCount orderDailyRefundCount);

    int updateRefundCount(OrderDailyRefundCount orderDailyRefundCount);


    /**
     * 当月发货包裹订单返点
     * @param startDay
     * @param endDay
     * @param orderSourceId
     * @return
     */
    BigDecimal totalPackageOrderBenefits(String startDay, String endDay, Long orderSourceId);

    /**
     * 当月发货包裹批发订单返点
     * @param startDay
     * @param endDay
     * @param orderSourceId
     * @return
     */
    BigDecimal totalPackageWholeSaleOrderBenefits(String startDay, String endDay, Long orderSourceId);

    List<DashboardOrderDto> paidOrderDataByDay(String startDay, String endDay, String userManager, int orderType);

    List<OrderDailyCountVo> selectTodayPaidOrderCount(OrderDailyCountRequest request);

    List<OrderDailyCountVo> selectThirtydayPaidOrderCount(OrderDailyCountRequest request);

    List<OrderDailyCountVo> selectCurrMonthSaleAmount(OrderDailyCountRequest request);
}

