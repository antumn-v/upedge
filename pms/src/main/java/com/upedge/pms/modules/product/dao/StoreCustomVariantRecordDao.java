package com.upedge.pms.modules.product.dao;

import com.upedge.common.base.Page;
import com.upedge.pms.modules.product.entity.StoreCustomVariantRecord;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

/**
 * @author gx
 */
public interface StoreCustomVariantRecordDao {

    StoreCustomVariantRecord selectByPrimaryKey(@Param("customerId") Long customerId, @Param("sku") String sku);

    List<StoreCustomVariantRecord> selectByCustomerAndSkus(@Param("customerId") Long customerId, @Param("skus") Set<String> skus);

    int deleteByPrimaryKey(StoreCustomVariantRecord record);

    int updateByPrimaryKey(StoreCustomVariantRecord record);

    int updateByPrimaryKeySelective(StoreCustomVariantRecord record);

    int insert(StoreCustomVariantRecord record);

    int insertSelective(StoreCustomVariantRecord record);

    int insertByBatch(List<StoreCustomVariantRecord> list);

    List<StoreCustomVariantRecord> select(Page<StoreCustomVariantRecord> record);

    long count(Page<StoreCustomVariantRecord> record);

}
