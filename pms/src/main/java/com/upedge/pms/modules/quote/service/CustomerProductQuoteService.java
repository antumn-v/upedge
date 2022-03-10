package com.upedge.pms.modules.quote.service;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.base.Page;
import com.upedge.common.model.pms.quote.CustomerProductQuoteVo;
import com.upedge.common.model.pms.request.CustomerProductQuoteSearchRequest;
import com.upedge.common.model.user.vo.Session;
import com.upedge.pms.modules.quote.entity.CustomerProductQuote;
import com.upedge.pms.modules.quote.request.CustomerProductQuoteUpdateRequest;

import java.util.List;

/**
 * @author gx
 */
public interface CustomerProductQuoteService{



    BaseResponse revokeQuote(Long id,Session session);

    BaseResponse updateCustomerProductQuote(CustomerProductQuoteUpdateRequest request, Session session);

    List<CustomerProductQuoteVo> selectQuoteDetail(CustomerProductQuoteSearchRequest request);

    List<CustomerProductQuote> selectByCustomerAndStoreVariantIds(Long customerId,
                                                                  List<Long> storeVariantIds);

    CustomerProductQuote selectByPrimaryKey(Long storeVariantId);

    boolean sendCustomerProductQuoteUpdateMessage(List<Long> storeVariantIds);

    int deleteByPrimaryKey(Long customerId);

    int updateByPrimaryKey(CustomerProductQuote record);

    int updateByPrimaryKeySelective(CustomerProductQuote record);

    int insert(CustomerProductQuote record);

    int insertSelective(CustomerProductQuote record);

    List<CustomerProductQuote> select(Page<CustomerProductQuote> record);

    long count(Page<CustomerProductQuote> record);
}

