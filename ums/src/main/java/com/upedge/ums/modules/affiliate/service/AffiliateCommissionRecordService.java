package com.upedge.ums.modules.affiliate.service;

import com.upedge.ums.modules.affiliate.entity.AffiliateCommissionRecord;
import com.upedge.common.base.Page;
import java.util.List;

/**
 * @author gx
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
}

