package com.upedge.oms.modules.statistics.dao;

import com.upedge.common.base.Page;
import com.upedge.common.model.order.request.OrderDailyCountRequest;
import com.upedge.common.model.order.vo.OrderDailyCountVo;
import com.upedge.common.model.statistics.request.OrderStatisticsRequest;
import com.upedge.common.model.statistics.vo.CustomerOrderStatisticsVo;
import com.upedge.common.model.statistics.vo.ManagerOrderStatisticsVo;
import com.upedge.common.model.statistics.vo.OrderStatisticsVo;
import com.upedge.oms.modules.statistics.dto.DashboardOrderDto;
import com.upedge.oms.modules.statistics.entity.OrderDailyPayCount;
import com.upedge.oms.modules.statistics.entity.OrderDailyRefundCount;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author author
 */
public interface OrderDailyPayCountDao{

    List<OrderStatisticsVo> selectCustomerOrderStatisticsByDate(OrderStatisticsRequest request);

    List<ManagerOrderStatisticsVo> selectManagerOrderStatistics(OrderStatisticsRequest request);

    List<CustomerOrderStatisticsVo> selectCustomerOrderStatistics(OrderStatisticsRequest request);

    int increaseOrderDailyCountById(OrderDailyPayCount orderDailyPayCount);

    /**
     * 查询客户在某天支付的某种类型的数据
     * @param customerId
     * @param payTime yyyy-MM-dd
     * @param orderType
     * @return
     */
    OrderDailyPayCount selectByCustomerOrderTypeAndDate(@Param("customerId") Long customerId,
                                                        @Param("payTime") Date payTime,
                                                        @Param("orderType") Integer orderType);

    OrderDailyPayCount selectByPrimaryKey(OrderDailyPayCount record);

    int deleteByPrimaryKey(OrderDailyPayCount record);

    int updateByPrimaryKey(OrderDailyPayCount record);

    int updateByPrimaryKeySelective(OrderDailyPayCount record);

    int insert(OrderDailyPayCount record);

    int insertSelective(OrderDailyPayCount record);

    int insertByBatch(List<OrderDailyPayCount> list);

    List<OrderDailyPayCount> select(Page<OrderDailyPayCount> record);

    long count(Page<OrderDailyPayCount> record);

    int insertRefundCount(OrderDailyRefundCount orderDailyRefundCount);

    int updateRefundCount(@Param("t") OrderDailyRefundCount orderDailyRefundCount);

    /**
     * 当月发货包裹订单返点
     * @param startDay
     * @param endDay
     * @param managerCode
     * @return
     */
    BigDecimal totalPackageOrderBenefits(@Param("startDay") String startDay, @Param("endDay") String endDay, @Param("managerCode") String managerCode);

    /**
     * 当月发货包裹批发订单返点
     * @param startDay
     * @param endDay
     * @param managerCode
     * @return
     */
    BigDecimal totalPackageWholeSaleOrderBenefits(@Param("startDay") String startDay, @Param("endDay") String endDay, @Param("managerCode") String managerCode);

    List<DashboardOrderDto> paidOrderDataByDay(@Param("startDay") String startDay, @Param("endDay") String endDay, @Param("userManager") String userManager, @Param("orderType") int orderType);

    List<OrderDailyCountVo> selectTodayPaidOrderCount(@Param("request") OrderDailyCountRequest request);

    List<OrderDailyCountVo> selectThirtydayPaidOrderCount(@Param("request") OrderDailyCountRequest request);

    List<OrderDailyCountVo> selectCurrMonthSaleAmount(@Param("request") OrderDailyCountRequest request);
}
