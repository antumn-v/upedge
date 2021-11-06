package com.upedge.ums.modules.account.service;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.exception.CustomerException;
import com.upedge.common.model.account.PaypalOrder;
import com.upedge.common.model.account.PaypalPayment;
import com.upedge.common.model.user.vo.Session;
import com.upedge.ums.modules.account.entity.RechargeRequestLog;
import com.upedge.ums.modules.account.entity.payoneer.PayoneerPayment;
import com.upedge.ums.modules.account.request.*;
import com.upedge.ums.modules.account.response.*;

import java.util.Date;

/**
 * @author 海桐
 */
public interface RechargeService {


    /**
     * 提交申请记录
     * @param request
     * @return
     */
    ApplyRechargeResponse addRechargeRequest(ApplyRechargeRequest request);

    /**
     * 分页查询申请记录
     * @param request
     * @return
     */
    ApplyRechargeListResponse rechargeRequestList(ApplyRechargeListRequest request);

    RechargeRequestLog savePaypalRechargeRequest(PaypalOrder order);


    /**
     * 银行转账充值申请
     * @param request
     * @return
     */
    ApplyRechargeResponse createTransferRequest(TransferRechargeRequest request);

    /**
     * 充值记录
     * @param request
     * @return
     */
    RechargeListResponse rechargeList(RechargeListRequest request);

    /**
     * 充值申请通过
     * @param requestId
     * @return
     */
    ApplyRechargeConfirmResponse confirmRechargeRequest(Long requestId, Session session) throws CustomerException;

    /**
     * 取消充值申请
     * @param requestId
     * @return
     */
    ApplyRechargeCancelResponse cancelRechargeRequest(Long requestId, ApplyRechargeCancelRequest request, Session session);

    /**
     * 撤销充值记录
     * @param rechargeId
     * @return
     */
    RechargeRevokeResponse revokeRecharge(Long rechargeId);

    PayoneerRechargeResponse payoneerRecharge(PayoneerPayment payment);

    ApplyRechargeResponse paypalRecharge(PaypalPayment payment);

    ApplyRechargeListResponse rechargeRequestHistory(ApplyRechargeListRequest request);

    ApplyRechargeUpdateResponse updateRechargeRequest(Long requestId, ApplyRechargeUpdateRequest request, Session session);

    /**
     * 充值历史分页
     * @param request
     * @return
     */
    BaseResponse rechargeHistoryList(RechargeHistoryListRequest request);

    RechargeRequestLog selectMaxNewRechargeRequestLog(Long customerId);

    RechargeRequestLog selectRechargeRequestLogByCustomerIdAndTime(Long customerId, Date createTime);

    void updateZoneByNull();
}
