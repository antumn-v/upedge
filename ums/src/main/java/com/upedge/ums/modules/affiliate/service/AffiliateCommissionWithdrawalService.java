package com.upedge.ums.modules.affiliate.service;

import com.upedge.ums.modules.affiliate.entity.AffiliateCommissionWithdrawal;
import com.upedge.common.base.Page;
import java.util.List;

/**
 * @author gx
 */
public interface AffiliateCommissionWithdrawalService{

    AffiliateCommissionWithdrawal selectByPrimaryKey(Long id);

    int deleteByPrimaryKey(Long id);

    int updateByPrimaryKey(AffiliateCommissionWithdrawal record);

    int updateByPrimaryKeySelective(AffiliateCommissionWithdrawal record);

    int insert(AffiliateCommissionWithdrawal record);

    int insertSelective(AffiliateCommissionWithdrawal record);

    List<AffiliateCommissionWithdrawal> select(Page<AffiliateCommissionWithdrawal> record);

    long count(Page<AffiliateCommissionWithdrawal> record);
}

