package com.upedge.oms.modules.order.dao;

import com.upedge.common.base.Page;
import com.upedge.oms.modules.order.entity.OrderRefund;
import com.upedge.oms.modules.order.request.OrderRefundListRequest;
import com.upedge.oms.modules.order.vo.OrderRefundVo;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author author
 */
public interface OrderRefundDao{

    OrderRefundVo selectUnderReviewRefundOrder(Long orderId);

    BigDecimal selectRefundAmountByOrderId(Long orderId);

    OrderRefund selectByPrimaryKey(Long id);

    int deleteByPrimaryKey(OrderRefund record);

    int updateByPrimaryKey(OrderRefund record);

    int updateByPrimaryKeySelective(OrderRefund record);

    int insert(OrderRefund record);

    int insertSelective(OrderRefund record);

    int insertByBatch(List<OrderRefund> list);

    List<OrderRefund> select(Page<OrderRefund> record);

    long count(Page<OrderRefund> record);

    List<OrderRefundVo> refundOrderList(OrderRefundListRequest request);
    Long refundOrderCount(OrderRefundListRequest request);

    void updateRemark(@Param("id") Long id, @Param("remark") String remark);

    int rejectRefund(OrderRefund orderRefund);

    int updateConfirmRefund(OrderRefund orderRefund);

    void updateConfirmAppRefundTrackingState(@Param("id") Long id,
                                             @Param("trackingState") Integer trackingState, @Param("orderSourceId") Integer orderSourceId);

    /**
     * 普通订单退款待处理数
     * @return
     */
    long orderHandleRefundData(@Param("managerCode") String managerCode);

    void updateRefundTrackingState(@Param("id") String id, @Param("trackingState") Integer trackingState, @Param("orderSourceId") Integer orderSourceId);
}
