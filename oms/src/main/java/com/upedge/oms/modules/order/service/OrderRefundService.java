package com.upedge.oms.modules.order.service;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.base.Page;
import com.upedge.common.exception.CustomerException;
import com.upedge.common.model.user.vo.Session;
import com.upedge.oms.modules.order.entity.OrderRefund;
import com.upedge.oms.modules.order.request.*;
import com.upedge.oms.modules.order.response.OrderApplyRefundResponse;
import com.upedge.oms.modules.order.response.OrderRefundListResponse;
import com.upedge.oms.modules.order.vo.OrderRefundVo;

import java.util.List;

/**
 * @author author
 */
public interface OrderRefundService{

    BaseResponse orderBatchApplyRefund(OrderBatchApplyRefundRequest request,Session session);

    OrderRefundVo selectByOrderId(Long orderId);

    void cancelSaiheOrderAndSynState(Long id, String clientOrderCode) throws CustomerException;

    OrderRefund selectByPrimaryKey(Long id);

    BaseResponse appApplyRefund(ApplyOrderRefundRequest request, Session session);

    int deleteByPrimaryKey(Long id);

    int updateByPrimaryKey(OrderRefund record);

    int updateByPrimaryKeySelective(OrderRefund record);

    int insert(OrderRefund record);

    int insertSelective(OrderRefund record);

    List<OrderRefund> select(Page<OrderRefund> record);

    long count(Page<OrderRefund> record);

    OrderApplyRefundResponse applyRefund(ApplyOrderRefundRequest request, Session session)throws CustomerException;

    OrderRefundListResponse refundOrderList(OrderRefundListRequest request);

    BaseResponse updateRemark(OrderRefundUpdateRemarkRequest request);

    BaseResponse rejectRefund(OrderRefundRejectRefundRequest request, Session session) throws CustomerException;

    OrderRefundListResponse refundOrderHistory(OrderRefundListRequest request);

    BaseResponse confirmRefund(ConfirmRefundRequest request, Session session)throws CustomerException;
}

