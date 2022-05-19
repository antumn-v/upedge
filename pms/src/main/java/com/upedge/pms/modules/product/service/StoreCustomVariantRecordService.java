package com.upedge.pms.modules.product.service;

import com.upedge.common.base.Page;
import com.upedge.common.model.pms.quote.CustomerProductQuoteVo;
import com.upedge.common.model.pms.request.StoreCustomVariantRecordSaveRequest;
import com.upedge.pms.modules.product.entity.StoreCustomVariantRecord;

import java.util.List;

/**
 * @author gx
 */
public interface StoreCustomVariantRecordService{

    List<CustomerProductQuoteVo> saveCustomVariant(StoreCustomVariantRecordSaveRequest request);

    StoreCustomVariantRecord selectByPrimaryKey(Long customerId, String sku);

    int deleteByPrimaryKey(Long customerId);

    int updateByPrimaryKey(StoreCustomVariantRecord record);

    int updateByPrimaryKeySelective(StoreCustomVariantRecord record);

    int insert(StoreCustomVariantRecord record);

    int insertSelective(StoreCustomVariantRecord record);

    List<StoreCustomVariantRecord> select(Page<StoreCustomVariantRecord> record);

    long count(Page<StoreCustomVariantRecord> record);
}

