package com.upedge.oms.modules.wholesale.dao;

import com.upedge.common.base.Page;
import com.upedge.common.model.order.PaymentDetail;
import com.upedge.common.model.order.TransactionDetail;
import com.upedge.common.model.order.vo.AllOrderAmountVo;
import com.upedge.common.model.ship.vo.ShipDetail;
import com.upedge.oms.modules.common.vo.RefundVo;
import com.upedge.oms.modules.order.dto.OrderTransactionDto;
import com.upedge.oms.modules.order.request.WholesaleOrderAppListRequest;
import com.upedge.oms.modules.statistics.dto.DashboardOrderDto;
import com.upedge.oms.modules.statistics.dto.WaitTrackOrderData;
import com.upedge.oms.modules.statistics.dto.WaitTrackOrderDataDetails;
import com.upedge.oms.modules.wholesale.entity.WholesaleOrder;
import com.upedge.oms.modules.wholesale.vo.WholesaleOrderAppVo;
import com.upedge.oms.modules.wholesale.vo.WholesaleOrderExport;
import com.upedge.thirdparty.saihe.entity.SaiheOrder;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author author
 */
public interface WholesaleOrderDao{


    List<WholesaleOrderExport> selectOrderTrackingByDate(@Param("customerId") Long customerId,
                                                         @Param("start") Date start,
                                                         @Param("end") Date end);

    BigDecimal selectPayAmountByPaymentId(Long paymentId);

    List<TransactionDetail> selectTransactionDetailByPaymentId(Long paymentId);

    List<WholesaleOrderAppVo> selectOrderAppList(WholesaleOrderAppListRequest request);

    List<WholesaleOrderAppVo> selectPayListByPaymentId(Long paymentId);

    Long selectOrderAppCount(WholesaleOrderAppListRequest request);

    WholesaleOrder selectByPrimaryKey(Long id);

    int updateOrderProductAmount(@Param("id") Long id,
                                 @Param("productAmount") BigDecimal productAmount,
                                 @Param("cnyProductAmount") BigDecimal cnyProductAmount);

    int updatePayStateById(@Param("id") Long id, @Param("state") Integer state);

    int updateVatAmountById(@Param("id") Long id, @Param("vatAmount") BigDecimal vatAmount);

    int updatePayStateByPaymentId(@Param("paymentId") Long paymentId, @Param("payState") Integer payState);

    int updateOrderAsPaid(OrderTransactionDto orderTransactionDto);

    int updateProductAmountByPaymentId(Long paymentId);

    int updateDischargeAmountByMap(@Param("map") Map<Long, BigDecimal> map);

    int updateVatAmountByMap(@Param("map") Map<Long, BigDecimal> map);

    int updateShipDetailByMap(@Param("map") Map<Long, ShipDetail> map);

    int updatePaymentIdByIds(@Param("paymentId") Long paymentId,
                             @Param("ids") List<Long> ids);

    boolean updateOrderShipDetail(@Param("methodId") Long methodId,
                                  @Param("price") BigDecimal price,
                                  @Param("weight") BigDecimal weight,
                                  @Param("id") Long id);

    int updateOrderToAreaId(@Param("id") Long id,
                            @Param("areaId") Long areaId);

    int deleteByPrimaryKey(WholesaleOrder record);

    int updateByPrimaryKey(WholesaleOrder record);

    int updateByPrimaryKeySelective(WholesaleOrder record);

    int insert(WholesaleOrder record);

    int insertSelective(WholesaleOrder record);

    int insertByBatch(List<WholesaleOrder> list);

    List<WholesaleOrder> select(Page<WholesaleOrder> record);

    long count(Page<WholesaleOrder> record);

    int existPendingOrderByOriginal(Long originalOrderId);

    int existReshipOrderWaitTrackByOriginal(Long originalOrderId);

    int reshipOrderTimesByOriginal(Long originalOrderId);

    int updatePendingOrderStatus(@Param("ids") List<Long> ids,
                                 @Param("orderStatus") int orderStatus);

    /**
     * 修改订单为退款中
     * @param id
     * @return
     */
    int updateOrderAsRefunding(Long id);

    /**
     * 恢复订单退款状态
     * @param id
     * @return
     */
    int restoreOrderRefundState(Long id);

    /**
     * 修改订单退款状态
     * @param id
     * @param state
     * @return
     */
    int updateOrderAsRefund(@Param("id") Long id, @Param("state") Integer state);

    /**
     * 保存赛盒code
     * @param id
     * @param orderCode
     */
    void updateSaiheOrderCode(@Param("id") String id, @Param("orderCode") String orderCode);

    /**
     * 查询赛盒订单信息
     * @param id
     * @return
     */
    SaiheOrder querySaiheOrder(Long id);

    /**
     * 批发订单未导入赛盒
     * @return
     */
    long wholesaleHandleImportData(@Param("userManager") String userManager);

    /**
     * 批发订单补发待审核
     * @return
     */
    long wholesaleHandleReshipData(@Param("userManager") String userManager);

    /**
     * 标记订单为发货
     * @param id
     */
    void updateOrderAsTracked(Long id);

    /**
     * 批发订单
     * 已支付订单总数  已支付订单已发货数 已支付订单未发货数 待支付订单数 已取消订单数
     * @return
     */
    long wholesaleOrderPaidTotalNum(Long customerId);
    long wholesaleOrderPaidShipYesNum(Long customerId);
    long wholesaleOrderPaidShipNoNum(Long customerId);
    long wholesaleOrderToPayNum(Long customerId);
    long wholesaleOrderCancelNum(Long customerId);
    BigDecimal wholesaleOrderPaidAmount(Long customerId);

    //今日付款批发订单数
    Integer todayPaidWholeSaleNum(@Param("todayDate") String todayDate,
                                  @Param("userManager") String userManager);

    //今日付款批发订单金额
    BigDecimal todayPaidWholeSaleAmount(@Param("todayDate") String todayDate,
                                        @Param("userManager") String userManager);

    //近30天付款批发订单数
    Integer paidWholeSaleNum30Day(@Param("todayDate") String todayDate,
                                  @Param("userManager") String userManager);

    //近30天付款批发订单金额
    BigDecimal paidWholeSaleAmount30Day(@Param("todayDate") String todayDate,
                                        @Param("userManager") String userManager);

    //客户付款批发订单数、付款金额，按天统计
    List<DashboardOrderDto> paidWholesaleOrderDataByDay(@Param("startDay") String startDay,
                                                        @Param("endDay") String endDay, @Param("userManager") String userManager);

    //客户批发订单数，按天统计
    List<DashboardOrderDto> wholesaleOrderNumByDay(@Param("startDay") String startDay,
                                                   @Param("endDay") String endDay, @Param("userManager") String userManager);

    //付款普通订单金额，按天区间统计
    BigDecimal paidWholesaleOrderAmountByDay(@Param("startDay") String startDay,
                                             @Param("endDay") String endDay, @Param("userManager") String userManager);

    //本月下批发订单客户数
    int paidWholesaleOrderUserNum(@Param("startDay") String startDay,
                                  @Param("endDay") String endDay, @Param("userManager") String userManager);

    //批发订单 未发货订单数
    long wholesaleWaitTrackNum(@Param("userManager") String userManager);

    //未发货订单分析(客户经理 订单数)
    List<WaitTrackOrderData> waitTrackOrderData(@Param("currDate") String currDate,
                                                @Param("dayNum") Integer dayNum);

    //未发货订单数据详情（客户维度）
    List<WaitTrackOrderDataDetails> waitTrackOrderDataDetails(@Param("currDate") String currDate,
                                                              @Param("adminUserId") String adminUserId, @Param("dayNum") Integer dayNum);

    RefundVo queryConfirmAppRefundById(@Param("id") String id);

    List<WholesaleOrder> selectWholesaleOrderByPaymentId(@Param("paymentId") Long paymentId);

    BigDecimal getWholeSaleOrderAmountByManagerCodeSet(@Param("t") AllOrderAmountVo allOrderAmountVo);

    Set<String> getWholeSaleOrderCount(@Param("t") AllOrderAmountVo allOrderAmountVo);

    List<WholesaleOrder> selectPage(Page<WholesaleOrder> page);
    Long countPage(Page<WholesaleOrder> page);

    int updateOrderShipMethod(@Param("id") Long id);

    void delOrderShipInfoByProductId(@Param("productId") Long productId);

    void delOrderShipInfoByVariantId(@Param("variantId") Long variantId);

    void updateShipDetailById(@Param("ship") ShipDetail shipDetail, @Param("id") Long orderId);

    List<WholesaleOrder> selectPageByMove(Page<WholesaleOrder> page);

    List<PaymentDetail> selectUploadSaiheAndUmsBywholesale();

    PaymentDetail selectUploadSaiheAndUmsOne(@Param("paymentId") Long paymentId);
}
