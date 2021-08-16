package com.upedge.ums.modules.affiliate.dao;

import com.upedge.ums.modules.affiliate.entity.AffiliateCommissionRecord;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import com.upedge.common.base.Page;

/**
 * @author gx
 */
public interface AffiliateCommissionRecordDao{

    AffiliateCommissionRecord selectByPrimaryKey(AffiliateCommissionRecord record);

    int deleteByPrimaryKey(AffiliateCommissionRecord record);

    int updateByPrimaryKey(AffiliateCommissionRecord record);

    int updateByPrimaryKeySelective(AffiliateCommissionRecord record);

    int insert(AffiliateCommissionRecord record);

    int insertSelective(AffiliateCommissionRecord record);

    int insertByBatch(List<AffiliateCommissionRecord> list);

    List<AffiliateCommissionRecord> select(Page<AffiliateCommissionRecord> record);

    long count(Page<AffiliateCommissionRecord> record);

}
