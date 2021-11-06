package com.upedge.oms.modules.wholesale.service;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.base.Page;
import com.upedge.common.exception.CustomerException;
import com.upedge.common.model.user.vo.Session;
import com.upedge.oms.modules.order.request.ConfirmRefundRequest;
import com.upedge.oms.modules.order.request.OrderRefundRejectRefundRequest;
import com.upedge.oms.modules.order.request.OrderRefundUpdateRemarkRequest;
import com.upedge.oms.modules.wholesale.entity.WholesaleRefund;
import com.upedge.oms.modules.wholesale.request.ApplyWholesaleOrderRefundRequest;
import com.upedge.oms.modules.wholesale.request.WholesaleRefundListRequest;
import com.upedge.oms.modules.wholesale.response.WholesaleRefundListResponse;

import java.util.List;

/**
 * @author author
 */
public interface WholesaleRefundService{

    BaseResponse orderApplyRefund(ApplyWholesaleOrderRefundRequest request, Session session);

    WholesaleRefund selectByPrimaryKey(Long id);

    int deleteByPrimaryKey(Long id);

    int updateByPrimaryKey(WholesaleRefund record);

    int updateByPrimaryKeySelective(WholesaleRefund record);

    int insert(WholesaleRefund record);

    int insertSelective(WholesaleRefund record);

    List<WholesaleRefund> select(Page<WholesaleRefund> record);

    long count(Page<WholesaleRefund> record);

    BaseResponse applyWholesaleOrder(ApplyWholesaleOrderRefundRequest request, Session session)throws CustomerException;

    WholesaleRefundListResponse refundOrderList(WholesaleRefundListRequest request);

    WholesaleRefundListResponse refundOrderHistory(WholesaleRefundListRequest request);

    BaseResponse rejectRefund(OrderRefundRejectRefundRequest request, Session session)throws CustomerException;

    BaseResponse updateRemark(OrderRefundUpdateRemarkRequest request);

    BaseResponse confirmRefund(ConfirmRefundRequest request, Session session)throws CustomerException;
}

