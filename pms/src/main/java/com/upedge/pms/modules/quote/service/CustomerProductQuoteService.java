package com.upedge.pms.modules.quote.service;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.base.Page;
import com.upedge.common.model.pms.quote.CustomerProductQuoteVo;
import com.upedge.common.model.pms.request.CustomerProductQuoteSearchRequest;
import com.upedge.pms.modules.quote.entity.CustomerProductQuote;
import com.upedge.pms.modules.quote.request.CustomerProductQuoteUpdateRequest;

import java.util.List;

/**
 * @author gx
 */
public interface CustomerProductQuoteService{


    BaseResponse updateCustomerProductQuote(CustomerProductQuoteUpdateRequest request);

    List<CustomerProductQuoteVo> selectQuoteDetail(CustomerProductQuoteSearchRequest request);

    List<CustomerProductQuote> selectByCustomerAndStoreVariantIds(Long customerId,
                                                                  List<Long> storeVariantIds);

    CustomerProductQuote selectByPrimaryKey(Long customerId);

    boolean sendCustomerProductQuoteUpdateMessage(List<Long> storeVariantIds);

    int deleteByPrimaryKey(Long customerId);

    int updateByPrimaryKey(CustomerProductQuote record);

    int updateByPrimaryKeySelective(CustomerProductQuote record);

    int insert(CustomerProductQuote record);

    int insertSelective(CustomerProductQuote record);

    List<CustomerProductQuote> select(Page<CustomerProductQuote> record);

    long count(Page<CustomerProductQuote> record);
}

