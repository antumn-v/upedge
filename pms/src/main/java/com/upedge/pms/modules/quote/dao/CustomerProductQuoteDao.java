package com.upedge.pms.modules.quote.dao;

import com.upedge.common.base.Page;
import com.upedge.common.model.pms.quote.CustomerProductQuoteVo;
import com.upedge.common.model.pms.request.CustomerProductQuoteSearchRequest;
import com.upedge.pms.modules.quote.entity.CustomerProductQuote;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author gx
 */
public interface CustomerProductQuoteDao{

    List<CustomerProductQuoteVo> selectAllQuoteDetail();

    List<CustomerProductQuoteVo> selectQuoteDetail(CustomerProductQuoteSearchRequest request);

    CustomerProductQuote selectByStoreVariantId(@Param("storeVariantId")Long storeVariantId);

    List<CustomerProductQuote> selectByCustomerAndStoreVariantIds(@Param("customerId")Long customerId,
                                                                  @Param("storeVariantIds") List<Long> storeVariantIds);

    CustomerProductQuote selectByPrimaryKey(Long storeVariantId);

    int deleteByPrimaryKey(CustomerProductQuote record);

    int updateByPrimaryKey(CustomerProductQuote record);

    int updateByPrimaryKeySelective(CustomerProductQuote record);

    int insert(CustomerProductQuote record);

    int insertSelective(CustomerProductQuote record);

    int insertByBatch(List<CustomerProductQuote> list);

    List<CustomerProductQuote> select(Page<CustomerProductQuote> record);

    long count(Page<CustomerProductQuote> record);

}
