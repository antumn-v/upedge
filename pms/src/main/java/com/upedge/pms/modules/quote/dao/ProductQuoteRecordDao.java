package com.upedge.pms.modules.quote.dao;

import com.upedge.common.base.Page;
import com.upedge.pms.modules.quote.entity.ProductQuoteRecord;

import java.util.List;

/**
 * @author gx
 */
public interface ProductQuoteRecordDao{

    ProductQuoteRecord selectByPrimaryKey(ProductQuoteRecord record);

    int deleteByPrimaryKey(ProductQuoteRecord record);

    int updateByPrimaryKey(ProductQuoteRecord record);

    int updateByPrimaryKeySelective(ProductQuoteRecord record);

    int insert(ProductQuoteRecord record);

    int insertSelective(ProductQuoteRecord record);

    int insertByBatch(List<ProductQuoteRecord> list);

    List<ProductQuoteRecord> select(Page<ProductQuoteRecord> record);

    long count(Page<ProductQuoteRecord> record);

}
