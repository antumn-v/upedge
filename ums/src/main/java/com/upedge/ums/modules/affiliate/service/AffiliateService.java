package com.upedge.ums.modules.affiliate.service;

import com.upedge.common.base.Page;
import com.upedge.common.model.user.vo.CommissionRecordVo;
import com.upedge.ums.modules.affiliate.entity.Affiliate;
import com.upedge.ums.modules.affiliate.request.AffiliateCommissionWithdrawalAddRequest;
import com.upedge.ums.modules.affiliate.response.AffiliateCommissionRecordListResponse;
import com.upedge.ums.modules.affiliate.response.AffiliateCommissionWithdrawalAddResponse;
import com.upedge.ums.modules.affiliate.response.AffiliateCommissionWithdrawalListResponse;
import com.upedge.ums.modules.affiliate.response.AffiliateListResponse;
import com.upedge.ums.modules.user.entity.Customer;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author author
 */
public interface AffiliateService{


    AffiliateCommissionWithdrawalListResponse withdrawalList(Long customerId);


    AffiliateCommissionRecordListResponse commissionMonthRecord(Long customerId);

    AffiliateListResponse refereeCommissionList(Long customerId);

    void affiliateBind(Customer customer);

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

    Affiliate selectAffiliateVoByrefereeId(@Param("customerId") Long customerId);
}

