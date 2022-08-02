package com.upedge.oms.modules.order.dao;

import com.upedge.common.base.Page;
import com.upedge.common.model.mq.ChangeManagerVo;
import com.upedge.common.model.oms.order.OrderItemQuantityVo;
import com.upedge.common.model.order.TransactionDetail;
import com.upedge.common.model.order.request.ManagerActualRequest;
import com.upedge.common.model.order.vo.AllOrderAmountVo;
import com.upedge.common.model.order.vo.CustomerOrderStatisticalVo;
import com.upedge.common.model.order.vo.ManagerActualVo;
import com.upedge.common.model.ship.vo.ShipDetail;
import com.upedge.oms.modules.common.vo.RefundVo;
import com.upedge.oms.modules.order.dto.OrderAnalysisDto;
import com.upedge.common.model.order.OrderItemQuantityDto;
import com.upedge.oms.modules.order.dto.OrderTransactionDto;
import com.upedge.oms.modules.order.dto.PandaOrderListDto;
import com.upedge.oms.modules.order.entity.Order;
import com.upedge.oms.modules.order.request.AppOrderListRequest;
import com.upedge.oms.modules.order.request.OrderCogsSelectRequest;
import com.upedge.oms.modules.order.vo.*;
import com.upedge.oms.modules.rules.vo.OrderShipRuleVo;
import com.upedge.oms.modules.statistics.dto.DashboardOrderDto;
import com.upedge.oms.modules.statistics.dto.WaitTrackOrderData;
import com.upedge.oms.modules.statistics.dto.WaitTrackOrderDataDetails;
import com.upedge.oms.modules.statistics.request.OrderPayCountRequest;
import com.upedge.oms.modules.statistics.request.ToOrderSkuDataRequest;
import com.upedge.oms.modules.statistics.vo.OrderPayCountVo;
import com.upedge.oms.modules.statistics.vo.ToOrderSkuVo;
import com.upedge.thirdparty.saihe.entity.SaiheOrder;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author author
 */
public interface OrderDao {


    void orderCancelUploadSaihe(Long id);

    List<Order> selectByIds(@Param("ids") List<Long> ids);

    List<Order> selectAllPaymentId();

    int updateWarehouseByMethodId(@Param("methodId") Long methodId, @Param("warehouseCode") String warehouseCode);

    List<Long> selectUploadSaiheFailedIds();

    List<Order> selectUnPaidOrderByAreaId(@Param("areaId") Long areaId, @Param("customerId") Long customerId);

    List<Long> selectUnPaidIdsByShipRule(@Param("rule") OrderShipRuleVo shipRuleVo, @Param("areaId") Long areaId);

    int updateOrderVatAmountByAreaId(@Param("areaIds") List<Long> areaIds,
                                     @Param("vatAmount") BigDecimal vatAmount);

    int cancelOrderByIds(@Param("ids") List<Long> ids);

    int updateQuoteStateByIds(@Param("ids") List<Long> ids,
                              @Param("quoteState") Integer quoteState);

    int initVatAmountByCustomerId(Long customerId);

    int initProductAmountById(@Param("orderIds") List<Long> orderIds);

    int initShipByShipUnitId(Long shipUnitId);

    List<Long> selectUnpaidOrderIdsByShipUnitId(Long shipUnitId);

    List<OrderProductAmountVo> selectOrderItemAmountByPaymentId(Long paymentId);


    List<OrderPayCountVo> countOrderPayByDay(OrderPayCountRequest request);

    List<OrderPayCountVo> countOrderPayByMonth(OrderPayCountRequest request);

    List<UnPaidOrderCountVo> countCustomerUnPaidOrder(@Param("customerId") Long customerId,
                                                      @Param("orgIds") List<Long> orgIds);

    List<OrderCogsVo> selectCustomerOrderCogs(@Param("customerId") Long customerId,
                                              @Param("param") OrderCogsSelectRequest request);

    List<TransactionDetail> selectTransactionDetailByPaymentId(Long paymentId);

    BigDecimal selectPayAmountByPaymentId(Long paymentId);

    List<Order> selectOrderByPaymentId(Long paymentId);

    List<AppOrderVo> selectPayOrderListByPaymentId(Long paymentID);

    AppOrderVo selectAppOrderById(Long id);

    List<AppOrderVo> selectAppOrderByIds(@Param("ids") List<Long> ids);

    List<AppOrderVo> selectAppOrderList(AppOrderListRequest request);

    List<Long> selectAppOrderIds(AppOrderListRequest request);

    Long selectAppOrderCount(AppOrderListRequest request);

    int updateOrderProductAmount(OrderProductAmountVo orderProductAmountVo);
/*    int updateOrderProductAmount(@Param("id") Long id,
                                 @Param("productAmount") BigDecimal productAmount,
                                 @Param("cnyProductAmount") BigDecimal cnyProductAmount);*/

    int updateProductAmountByPaymentId(Long paymentId);

    int updatePayStateByPaymentId(@Param("paymentId") Long paymentId, @Param("payState") Integer payState);

    boolean updateOrderVatAmountById(@Param("id") Long id,
                                     @Param("vatAmount") BigDecimal vatAmount);

    int updateToAreaIdById(@Param("id") Long id,
                           @Param("toAreaId") Long toAreaId);

    void updateShipDetailById(@Param("ship") ShipDetail shipDetail,
                              @Param("id") Long id);

    int updateVatAmountByMap(@Param("map") Map<Long, BigDecimal> map);

    int updateShipDetailByMap(@Param("map") Map<Long, ShipDetail> map);

    int updateDischargeAmountByMap(@Param("map") Map<Long, BigDecimal> map);

    int updatePaymentIdByIds(@Param("paymentId") Long paymentId,
                             @Param("customerId") Long customerId,
                             @Param("ids") List<Long> ids);

    int updateOrderAsPending(@Param("paymentId") Long paymentId);

    int updateOrderAsPaid(OrderTransactionDto orderTransactionDto);

    int deleteByIds(@Param("ids") List<Long> ids);

    int deleteReshipOrder(Long id);

    int deleteOrderByIds(@Param("ids") List<Long> ids);

    int updateOrderType(@Param("id") Long id,
                        @Param("orderType") Integer orderType);

    Order selectByPrimaryKey(Long id);

    int updateByPrimaryKey(Order record);

    int updateByPrimaryKeySelective(Order record);

    int insert(Order record);

    int insertSelective(Order record);

    int insertByBatch(List<Order> list);

    List<Order> select(Page<Order> record);

    long count(Page<Order> record);


    List<Order> historySelect(Page<OrderManageQuery> record);

    long historyCount(Page<OrderManageQuery> record);

    int updatePendingOrderStatus(@Param("ids") List<Long> ids,
                                 @Param("orderStatus") int orderStatus,
                                 @Param("userCode") String userCode);

    int existPendingOrderByOriginal(Long originalOrderId);

    int existReshipOrderWaitTrackByOriginal(Long originalOrderId);

    int reshipOrderTimesByOriginal(Long originalOrderId);

    //更新订单状态为退款中
    int updateOrderAsRefunding(Long id);

    //驳回退款，恢复订单退款状态
    int restoreOrderRefundState(Long id);

    //修改订单状态为已退款
    int updateOrderAsRefund(@Param("id") Long id, @Param("state") Integer state);

    /**
     * 查询赛盒订单信息
     *
     * @param id
     * @return
     */
    SaiheOrder querySaiheOrder(Long id);

    /**
     * 更新赛盒orderCode
     */
    void updateSaiheOrderCode(@Param("id") String id,
                              @Param("saiheOrderCode") String saiheOrderCode);

    /**
     * 更新订单为已发货
     *
     * @param id
     * @return
     */
    int updateOrderAsTracked(@Param("id") Long id,
                             @Param("trackNum") String trackNum);

    /**
     * 普通订单未导入赛盒
     *
     * @return
     */
    long orderHandleImportData(@Param("userManager") String userManager);

    /**
     * 普通订单补发待审核
     *
     * @return
     */
    long orderHandleReshipData(@Param("userManager") String userManager);

    /**
     * 未付款普通订单SKU统计
     *
     * @param request
     * @return
     */
    List<ToOrderSkuVo> selectToOrderSkuData(ToOrderSkuDataRequest request);

    Long countToOrderSkuData(ToOrderSkuDataRequest request);

    /**
     * 普通订单
     * 已支付订单总数  已支付订单已发货数 已支付订单未发货数 待支付订单数 已取消订单数
     *
     * @return
     */
    long normalOrderPaidTotalNum(Long customerId);

    long normalOrderPaidShipYesNum(Long customerId);

    long normalOrderPaidShipNoNum(Long customerId);

    long normalOrderToPayNum(Long customerId);

    long normalOrderCancelNum(Long customerId);

    BigDecimal normalOrderPaidAmount(Long customerId);

    //今日付款普通订单数
    Integer todayPaidOrderNum(@Param("todayDate") String todayDate,
                              @Param("userManager") String userManager);

    //今日付款普通订单金额
    BigDecimal todayPaidOrderAmount(@Param("todayDate") String todayDate,
                                    @Param("userManager") String userManager);

    //近30天付款普通订单数
    Integer paidOrderNum30Day(@Param("todayDate") String todayDate,
                              @Param("userManager") String userManager);

    //近30天付款普通订单金额
    BigDecimal paidOrderAmount30Day(@Param("todayDate") String todayDate,
                                    @Param("userManager") String userManager);

    //客户付款普通订单数、付款金额，按天统计
    List<DashboardOrderDto> paidNormalOrderDataByDay(@Param("startDay") String startDay,
                                                     @Param("endDay") String endDay, @Param("userManager") String userManager);

    //客户普通订单数，按天统计
    List<DashboardOrderDto> normalOrderNumByDay(@Param("startDay") String startDay,
                                                @Param("endDay") String endDay, @Param("userManager") String userManager);

    //付款普通订单金额，按天区间统计
    BigDecimal paidNormalOrderAmountByDay(@Param("startDay") String startDay,
                                          @Param("endDay") String endDay, @Param("userManager") String userManager);

    //本月下普通订单客户数
    int paidNormalOrderUserNum(@Param("startDay") String startDay,
                               @Param("endDay") String endDay, @Param("userManager") String userManager);

    //普通订单未发货订单数
    long normalWaitTrackNum(@Param("userManager") String userManager);

    //未发货订单分析(客户经理 订单数)
    List<WaitTrackOrderData> waitTrackOrderData(@Param("currDate") String currDate,
                                                @Param("dayNum") Integer dayNum);

    //未发货订单数据详情（客户维度）
    List<WaitTrackOrderDataDetails> waitTrackOrderDataDetails(@Param("currDate") String currDate,
                                                              @Param("adminUserId") String adminUserId, @Param("dayNum") Integer dayNum);

    /**
     * 客户潘达订单
     *
     * @param record
     * @return
     */
    List<PandaOrderListVo> upedgeOrderPage(Page<PandaOrderListDto> record);

    Long upedgeOrderCount(Page<PandaOrderListDto> record);

    /**
     * 客户该时间区间内下单数量
     *
     * @param query
     * @return
     */
    Integer getOrderAllCountByTime(@Param("query") OrderAnalysisDto query);

    /**
     * 客户该时间区间内下单未发货数量
     *
     * @param query
     * @return
     */
    Integer getOrderNotDeliverCountByTime(@Param("query") OrderAnalysisDto query);


    /**
     * 客户该时间区间内下单已发货数量
     *
     * @param query
     * @return
     */
    Integer getOrderDeliverCountByTime(@Param("query") OrderAnalysisDto query);


    /**
     * 客户该时间区间内订单地区分布
     *
     * @query
     */
    List<EchartsPieVo> orderDistributionByTime(@Param("query") OrderAnalysisDto query);


    /**
     * 客户该月内 订单和金额
     *
     * @param customerId
     * @param startDay
     * @param endDay
     * @return
     */
    List<AppOrderDataVo> appOrderWithDate(@Param("customerId") String customerId, @Param("startDay") String startDay, @Param("endDay") String endDay);

    List<Order> getOrderList(@Param("t") PandaOrderListDto dto);

    ManagerActualVo getManagerActual(@Param("t") ManagerActualRequest request);

    RefundVo queryConfirmAppRefundById(@Param("id") String id);

    BigDecimal getNormalOrderAmountByManagerCode(@Param("t") AllOrderAmountVo allOrderAmountVo);

    Set<String> getNormalOrderCount(@Param("t") AllOrderAmountVo allOrderAmountVo);

    CustomerOrderStatisticalVo OrderTotalAmount(@Param("customerId") Long customerId);

    Integer getCancelOrderCount(@Param("customerId") Long customerId);

    Integer getPayAndShipOrderCount(@Param("customerId") Long customerId);

    Integer getPayButNoShipOrderCount(@Param("customerId") Long customerId);

    Integer getCompleteOrderCount(@Param("customerId") Long customerId);

    Integer getNoPayOrderCount(@Param("customerId") Long customerId);

    List<Order> selectPage(Page<Order> page);

    int updateOrderShipMethod(@Param("id") Long id);

    /**
     * 通过枚举修改各表的managerCode  不是修改一张order表  请勿随意调用
     *
     * @param changeManagerVo
     * @param tableName
     */
    void updateManagerCode(@Param("changeManagerVo") ChangeManagerVo changeManagerVo, @Param("tableName") String tableName);

    void delOrderShipInfoByProductId(@Param("productId") Long productId);

    void delOrderShipInfoByVariantId(@Param("variantId") Long variantId);

    List<Order> selectPageBymove(Page<Order> page);

    List<Long> selectShippedIdsByCustomer(Long customerId);

    List<OrderItemQuantityVo> selectOrderItemQuantities(OrderItemQuantityDto orderItemQuantityDto);

}
