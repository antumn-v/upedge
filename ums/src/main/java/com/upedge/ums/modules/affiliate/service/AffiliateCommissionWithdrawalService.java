package com.upedge.ums.modules.affiliate.service;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.base.Page;
import com.upedge.common.exception.CustomerException;
import com.upedge.common.model.user.vo.Session;
import com.upedge.ums.modules.affiliate.entity.AffiliateCommissionWithdrawal;
import com.upedge.ums.modules.affiliate.request.AffiliateWithdrawalConfirmRequest;
import com.upedge.ums.modules.affiliate.request.AffiliateWithdrawalRejectRequest;
import com.upedge.ums.modules.affiliate.request.AffiliateWithdrawalRequest;

import java.util.List;

/**
 * @author author
 */
public interface AffiliateCommissionWithdrawalService{

    /**
     * 佣金提现申请
     * @param request
     * @param session
     * @return
     */
    BaseResponse affiliateWithdrawalRequest(AffiliateWithdrawalRequest request, Session session) throws CustomerException;

    /**
     * 佣金提现申请通过
     * @param withdrawalId
     * @param confirm
     * @return
     */
    BaseResponse affiliateWithdrawalConfirm(Long withdrawalId, Session session, AffiliateWithdrawalConfirmRequest confirm);

    /**
     * 佣金提现申请拒绝
     * @return
     */
    BaseResponse affiliateWithdrawalReject(AffiliateWithdrawalRejectRequest request, Session session);

    AffiliateCommissionWithdrawal selectByPrimaryKey(Long id);

    int deleteByPrimaryKey(Long id);

    int updateByPrimaryKey(AffiliateCommissionWithdrawal record);

    int updateByPrimaryKeySelective(AffiliateCommissionWithdrawal record);

    int insert(AffiliateCommissionWithdrawal record);

    int insertSelective(AffiliateCommissionWithdrawal record);

    List<AffiliateCommissionWithdrawal> select(Page<AffiliateCommissionWithdrawal> record);

    long count(Page<AffiliateCommissionWithdrawal> record);
}

