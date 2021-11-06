package com.upedge.ums.modules.affiliate.dao;

import com.upedge.common.base.Page;
import com.upedge.ums.modules.affiliate.entity.AffiliateCommissionWithdrawal;
import com.upedge.ums.modules.affiliate.vo.WithdrawalVo;

import java.util.List;

/**
 * @author author
 */
public interface AffiliateCommissionWithdrawalDao{

    List<WithdrawalVo> selectWithdrawalList(Long customerId);

    AffiliateCommissionWithdrawal selectByPrimaryKey(Long id);

    int deleteByPrimaryKey(AffiliateCommissionWithdrawal record);

    int updateByPrimaryKey(AffiliateCommissionWithdrawal record);

    int updateByPrimaryKeySelective(AffiliateCommissionWithdrawal record);

    int insert(AffiliateCommissionWithdrawal record);

    int insertSelective(AffiliateCommissionWithdrawal record);

    int insertByBatch(List<AffiliateCommissionWithdrawal> list);

    List<AffiliateCommissionWithdrawal> select(Page<AffiliateCommissionWithdrawal> record);

    long count(Page<AffiliateCommissionWithdrawal> record);

}
