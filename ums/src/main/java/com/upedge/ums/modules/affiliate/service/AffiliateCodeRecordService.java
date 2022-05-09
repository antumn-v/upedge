package com.upedge.ums.modules.affiliate.service;

import com.upedge.ums.modules.affiliate.entity.AffiliateCodeRecord;
import com.upedge.common.base.Page;
import java.util.List;

/**
 * @author gx
 */
public interface AffiliateCodeRecordService{

    AffiliateCodeRecord selectCustomerLatestAffiliateCode(Long customerId);

    AffiliateCodeRecord selectByPrimaryKey(String affiliateCode);

    int deleteByPrimaryKey(String affiliateCode);

    int updateByPrimaryKey(AffiliateCodeRecord record);

    int updateByPrimaryKeySelective(AffiliateCodeRecord record);

    int insert(AffiliateCodeRecord record);

    int insertSelective(AffiliateCodeRecord record);

    List<AffiliateCodeRecord> select(Page<AffiliateCodeRecord> record);

    long count(Page<AffiliateCodeRecord> record);
}

