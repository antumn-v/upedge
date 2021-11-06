package com.upedge.ums.modules.affiliate.service;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.base.Page;
import com.upedge.common.model.user.request.OrderBenefitsRequest;
import com.upedge.ums.modules.affiliate.entity.AffiliateCommissionRecord;

import java.util.List;

/**
 * @author author
 */
public interface AffiliateCommissionRecordService{

    AffiliateCommissionRecord selectByPrimaryKey(Long id);

    int deleteByPrimaryKey(Long id);

    int updateByPrimaryKey(AffiliateCommissionRecord record);

    int updateByPrimaryKeySelective(AffiliateCommissionRecord record);

    int insert(AffiliateCommissionRecord record);

    int insertSelective(AffiliateCommissionRecord record);

    List<AffiliateCommissionRecord> select(Page<AffiliateCommissionRecord> record);

    long count(Page<AffiliateCommissionRecord> record);

    BaseResponse packageMonthOrderBenefitsListAmount(OrderBenefitsRequest orderBenefitsRequest);

    BaseResponse packageMonthWholeSaleOrderBenefitsListAmount(OrderBenefitsRequest orderBenefitsRequest);
}

