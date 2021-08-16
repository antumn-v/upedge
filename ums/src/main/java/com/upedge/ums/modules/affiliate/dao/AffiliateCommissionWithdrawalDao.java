package com.upedge.ums.modules.affiliate.dao;

import com.upedge.ums.modules.affiliate.entity.AffiliateCommissionWithdrawal;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import com.upedge.common.base.Page;

/**
 * @author gx
 */
public interface AffiliateCommissionWithdrawalDao{

    AffiliateCommissionWithdrawal selectByPrimaryKey(AffiliateCommissionWithdrawal record);

    int deleteByPrimaryKey(AffiliateCommissionWithdrawal record);

    int updateByPrimaryKey(AffiliateCommissionWithdrawal record);

    int updateByPrimaryKeySelective(AffiliateCommissionWithdrawal record);

    int insert(AffiliateCommissionWithdrawal record);

    int insertSelective(AffiliateCommissionWithdrawal record);

    int insertByBatch(List<AffiliateCommissionWithdrawal> list);

    List<AffiliateCommissionWithdrawal> select(Page<AffiliateCommissionWithdrawal> record);

    long count(Page<AffiliateCommissionWithdrawal> record);

}
