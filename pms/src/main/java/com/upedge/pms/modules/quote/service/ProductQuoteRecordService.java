package com.upedge.pms.modules.quote.service;

import com.upedge.pms.modules.quote.entity.ProductQuoteRecord;
import com.upedge.common.base.Page;
import java.util.List;

/**
 * @author gx
 */
public interface ProductQuoteRecordService{

    ProductQuoteRecord selectByPrimaryKey(Integer id);

    int insertByBatch(List<ProductQuoteRecord> productQuoteRecords);

    int deleteByPrimaryKey(Integer id);

    int updateByPrimaryKey(ProductQuoteRecord record);

    int updateByPrimaryKeySelective(ProductQuoteRecord record);

    int insert(ProductQuoteRecord record);

    int insertSelective(ProductQuoteRecord record);

    List<ProductQuoteRecord> select(Page<ProductQuoteRecord> record);

    long count(Page<ProductQuoteRecord> record);
}

