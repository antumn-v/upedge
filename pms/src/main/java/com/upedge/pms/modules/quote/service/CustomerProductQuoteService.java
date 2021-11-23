package com.upedge.pms.modules.quote.service;

import com.upedge.pms.modules.quote.entity.CustomerProductQuote;
import com.upedge.common.base.Page;
import com.upedge.pms.modules.quote.request.CustomerProductQuoteSearchRequest;
import com.upedge.pms.modules.quote.vo.CustomerProductQuoteVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author gx
 */
public interface CustomerProductQuoteService{

    List<CustomerProductQuoteVo> selectQuoteDetail(CustomerProductQuoteSearchRequest request);

    List<CustomerProductQuote> selectByCustomerAndStoreVariantIds(Long customerId,
                                                                  List<Long> storeVariantIds);

    CustomerProductQuote selectByPrimaryKey(Long customerId);

    int deleteByPrimaryKey(Long customerId);

    int updateByPrimaryKey(CustomerProductQuote record);

    int updateByPrimaryKeySelective(CustomerProductQuote record);

    int insert(CustomerProductQuote record);

    int insertSelective(CustomerProductQuote record);

    List<CustomerProductQuote> select(Page<CustomerProductQuote> record);

    long count(Page<CustomerProductQuote> record);
}

