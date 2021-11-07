package com.upedge.pms.modules.quote.dao;

import com.upedge.pms.modules.quote.entity.CustomerProductQuote;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import com.upedge.common.base.Page;
import org.apache.ibatis.annotations.Param;

/**
 * @author gx
 */
public interface CustomerProductQuoteDao{

    CustomerProductQuote selectByCustomerIdAndStoreVairantId(@Param("customerId") Long customerId,
                                                             @Param("storeVariantId")Long storeVariantId);

    List<CustomerProductQuote> selectByCustomerAndStoreVariantIds(@Param("customerId")Long customerId,
                                                                  @Param("storeVariantIds") List<Long> storeVariantIds);

    CustomerProductQuote selectByPrimaryKey(CustomerProductQuote record);

    int deleteByPrimaryKey(CustomerProductQuote record);

    int updateByPrimaryKey(CustomerProductQuote record);

    int updateByPrimaryKeySelective(CustomerProductQuote record);

    int insert(CustomerProductQuote record);

    int insertSelective(CustomerProductQuote record);

    int insertByBatch(List<CustomerProductQuote> list);

    List<CustomerProductQuote> select(Page<CustomerProductQuote> record);

    long count(Page<CustomerProductQuote> record);

}