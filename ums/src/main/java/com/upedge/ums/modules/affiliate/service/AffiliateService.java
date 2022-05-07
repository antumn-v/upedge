package com.upedge.ums.modules.affiliate.service;

import com.upedge.common.base.Page;
import com.upedge.common.model.user.vo.CommissionRecordVo;
import com.upedge.ums.modules.affiliate.entity.Affiliate;
import com.upedge.ums.modules.affiliate.request.AffiliateAddRequest;
import com.upedge.ums.modules.affiliate.request.AffiliateCommissionWithdrawalAddRequest;
import com.upedge.ums.modules.affiliate.response.*;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author author
 */
public interface AffiliateService{

    BigDecimal selectTotalByReferrerId(Long customerId);

    String customerReferrerToken(Long customerId);

    AffiliateCommissionWithdrawalListResponse withdrawalList(Long customerId);


    AffiliateCommissionRecordListResponse commissionMonthRecord(Long customerId);

    AffiliateListResponse refereeCommissionList(Long customerId);

    void affiliateBind(String referrerToken,Long refereeId);

    Affiliate selectByPrimaryKey(Long id);

    AffiliateCommissionWithdrawalAddResponse CommissionWithdrawRequest(AffiliateCommissionWithdrawalAddRequest request);

    void addAffiliateCommissionRecord(CommissionRecordVo commissionRecordVo);

    int deleteByPrimaryKey(Long id);

    int updateByPrimaryKey(Affiliate record);

    int updateByPrimaryKeySelective(Affiliate record);

    int insert(Affiliate record);

    int insertSelective(Affiliate record);

    List<Affiliate> select(Page<Affiliate> record);

    long count(Page<Affiliate> record);

    Affiliate queryAffiliateByReferee(Long refereeId);

    Affiliate selectAffiliateVoByRefereeId(@Param("customerId") Long customerId);

    AffiliateAddResponse addAffiliate(AffiliateAddRequest request);

    List<Affiliate> allAffiliates();
}

