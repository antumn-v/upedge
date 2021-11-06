package com.upedge.ums.modules.affiliate.service;

import com.upedge.common.model.user.vo.CustomerAffiliateVo;
import com.upedge.ums.modules.affiliate.request.AffiliateAddRequest;
import com.upedge.ums.modules.affiliate.request.AffiliateCommissionRecordListRequest;
import com.upedge.ums.modules.affiliate.request.AffiliateCommissionWithdrawalListRequest;
import com.upedge.ums.modules.affiliate.request.AffiliateListRequest;
import com.upedge.ums.modules.affiliate.response.AffiliateAddResponse;
import com.upedge.ums.modules.affiliate.response.AffiliateCommissionRecordListResponse;
import com.upedge.ums.modules.affiliate.response.AffiliateCommissionWithdrawalListResponse;
import com.upedge.ums.modules.affiliate.response.AffiliateListResponse;

public interface AdminAffiliateService {

    AffiliateListResponse affiliateList(AffiliateListRequest request);

    AffiliateCommissionRecordListResponse commissionRecordList(AffiliateCommissionRecordListRequest request);

    AffiliateCommissionWithdrawalListResponse commissionWithdrawalList(AffiliateCommissionWithdrawalListRequest request);

    AffiliateAddResponse addAffiliate(AffiliateAddRequest request);

    CustomerAffiliateVo customerAffiliateInfo(Long customerId);
}
