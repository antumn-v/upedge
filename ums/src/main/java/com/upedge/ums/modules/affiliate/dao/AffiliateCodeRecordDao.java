package com.upedge.ums.modules.affiliate.dao;

import com.upedge.common.base.Page;
import com.upedge.ums.modules.affiliate.entity.AffiliateCodeRecord;

import java.util.List;

/**
 * @author gx
 */
public interface AffiliateCodeRecordDao{

    AffiliateCodeRecord selectCustomerLatestAffiliateCode(Long customerId);

    AffiliateCodeRecord selectByPrimaryKey(AffiliateCodeRecord record);

    int deleteByPrimaryKey(AffiliateCodeRecord record);

    int updateByPrimaryKey(AffiliateCodeRecord record);

    int updateByPrimaryKeySelective(AffiliateCodeRecord record);

    int insert(AffiliateCodeRecord record);

    int insertSelective(AffiliateCodeRecord record);

    int insertByBatch(List<AffiliateCodeRecord> list);

    List<AffiliateCodeRecord> select(Page<AffiliateCodeRecord> record);

    long count(Page<AffiliateCodeRecord> record);

}
