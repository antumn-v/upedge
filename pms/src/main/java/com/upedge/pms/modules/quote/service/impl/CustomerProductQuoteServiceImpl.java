package com.upedge.pms.modules.quote.service.impl;

import com.upedge.common.utils.ListUtils;
import com.upedge.pms.modules.quote.request.CustomerProductQuoteSearchRequest;
import com.upedge.pms.modules.quote.vo.CustomerProductQuoteVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import com.upedge.common.base.Page;
import org.springframework.beans.factory.annotation.Autowired;
import com.upedge.pms.modules.quote.dao.CustomerProductQuoteDao;
import com.upedge.pms.modules.quote.entity.CustomerProductQuote;
import com.upedge.pms.modules.quote.service.CustomerProductQuoteService;


@Service
public class CustomerProductQuoteServiceImpl implements CustomerProductQuoteService {

    @Autowired
    private CustomerProductQuoteDao customerProductQuoteDao;



    /**
     *
     */
    @Transactional
    public int deleteByPrimaryKey(Long customerId) {
        CustomerProductQuote record = new CustomerProductQuote();
        record.setCustomerId(customerId);
        return customerProductQuoteDao.deleteByPrimaryKey(record);
    }

    /**
     *
     */
    @Transactional
    public int insert(CustomerProductQuote record) {
        return customerProductQuoteDao.insert(record);
    }

    /**
     *
     */
    @Transactional
    public int insertSelective(CustomerProductQuote record) {
        return customerProductQuoteDao.insert(record);
    }

    @Override
    public List<CustomerProductQuoteVo> selectQuoteDetail(CustomerProductQuoteSearchRequest request) {
        if (request != null){
            return new ArrayList<>();
        }
        return customerProductQuoteDao.selectQuoteDetail(request);
    }

    @Override
    public List<CustomerProductQuote> selectByCustomerAndStoreVariantIds(Long customerId, List<Long> storeVariantIds) {
        if (ListUtils.isNotEmpty(storeVariantIds) && null != customerId){
            return customerProductQuoteDao.selectByCustomerAndStoreVariantIds(customerId,storeVariantIds);
        }
        return null;
    }

    /**
     *
     */
    public CustomerProductQuote selectByPrimaryKey(Long customerId){
        CustomerProductQuote record = new CustomerProductQuote();
        record.setCustomerId(customerId);
        return customerProductQuoteDao.selectByPrimaryKey(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKeySelective(CustomerProductQuote record) {
        return customerProductQuoteDao.updateByPrimaryKeySelective(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKey(CustomerProductQuote record) {
        return customerProductQuoteDao.updateByPrimaryKey(record);
    }

    /**
    *
    */
    public List<CustomerProductQuote> select(Page<CustomerProductQuote> record){
        record.initFromNum();
        return customerProductQuoteDao.select(record);
    }

    /**
    *
    */
    public long count(Page<CustomerProductQuote> record){
        return customerProductQuoteDao.count(record);
    }

}