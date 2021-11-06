package com.upedge.oms.modules.stock.dao;

import com.upedge.common.base.Page;
import com.upedge.common.model.order.PaymentAmount;
import com.upedge.common.model.order.PaymentDetail;
import com.upedge.common.model.order.TransactionDetail;
import com.upedge.oms.modules.stock.dto.StockOrderListDto;
import com.upedge.oms.modules.stock.entity.StockOrder;
import com.upedge.oms.modules.stock.request.AdminStockOrderListRequest;
import com.upedge.oms.modules.stock.vo.AdminStockOrderVo;
import com.upedge.oms.modules.stock.vo.StockOrderVo;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author author
 */
public interface StockOrderDao{

    PaymentAmount selectSumAmountByPaymentId(Long paymentId);

    List<TransactionDetail> selectTransactionDetailByPaymentId(Long paymentId);

    /**
     * 根据交易id修改支付状态，无法修改已支付订单的交易状态
     * @param paymentId
     * @param payState
     * @return
     */
    int updatePayStateByPaymentId(@Param("paymentId") Long paymentId,
                                  @Param("payState") Integer payState);

    BigDecimal selectAmountByPaymentId(Long paymentId);

    int completeOrderTransaction(@Param("detail") PaymentDetail detail,
                                 @Param("percentage") BigDecimal percentage);

    int updatePaymentIdByIds(@Param("paymentId") Long paymentId,
                             @Param("ids") List<Long> ids);

    StockOrderVo selectOrderById(Long orderId);

    List<StockOrderVo> selectOrderByIds(@Param("ids") List<Long> ids);

    /**修改订单支付状态，已支付的订单无法修改
     * @param order
     * @return
     */
    int updateOrderPayState(StockOrder order);

    int updateOrderAmountById(Long id);

    List<StockOrderVo> selectOrderList(Page<StockOrderListDto> page);

    Long countOrderList(Page<StockOrderListDto> page);

    StockOrder selectByPrimaryKey(Long id);

    int deleteByPrimaryKey(StockOrder record);

    int updateByPrimaryKey(StockOrder record);

    int updateByPrimaryKeySelective(StockOrder record);

    int insert(StockOrder record);

    int insertSelective(StockOrder record);

    int insertByBatch(List<StockOrder> list);

    List<StockOrder> select(Page<StockOrder> record);

    long count(Page<StockOrder> record);

    List<AdminStockOrderVo> selectAdminStockOrderList(AdminStockOrderListRequest request);
    Long countAdminStockOrderList(AdminStockOrderListRequest request);

    //检查备库订单未同步的子体sku数量
    long countWithOutSaiheSku(Long id);
    //标记备库订单已上传赛盒
    int markStockOrder(Long id);
    //更新状态创建备库单成功
    void updateOrderStockedSuccess(StockOrder stockOrder);
    //备库单待同步赛盒产品SKU列表
    List<String> listVariantSkuForRefresh(Long id);
    //修改备库单为退款中
    Integer applyStockOrderRefund(Long id);

    /**
     * todo 备库订单状态改为 已付款
     * @param id
     * @return
     */
    Integer restoreStockOrderAsPaid(Long id);

    /**
     * 修改备库订单状态
     * @param id
     * @param state
     * @return
     */
    int updateOrderAsRefund(@Param("id") Long id, @Param("state") Integer state);

    /**
     * 备库订单待处理
     * @return
     */
    long orderHandleImportData(@Param("userManager") String userManager);

    /**
     * 根据paymentId 查询支付号下的所有订单
     * @param paymentId
     * @return
     */
    List<StockOrder> selectStockOrderByPaymentId(@Param("paymentId") Long paymentId);

    List<StockOrder> selectPageByMove(Page<StockOrder> page);
}
