package com.upedge.pms.modules.quote.service.impl;

import com.upedge.common.base.Page;
import com.upedge.common.model.pms.quote.CustomerProductQuoteVo;
import com.upedge.common.model.pms.request.CustomerProductQuoteSearchRequest;
import com.upedge.common.utils.ListUtils;
import com.upedge.pms.modules.product.dao.StoreProductVariantDao;
import com.upedge.pms.modules.quote.dao.CustomerProductQuoteDao;
import com.upedge.pms.modules.quote.entity.CustomerProductQuote;
import com.upedge.pms.modules.quote.service.CustomerProductQuoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
public class CustomerProductQuoteServiceImpl implements CustomerProductQuoteService {

    @Autowired
    private CustomerProductQuoteDao customerProductQuoteDao;

    @Autowired
    StoreProductVariantDao storeProductVariantDao;



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
        if (request == null){
            return new ArrayList<>();
        }
        List<Long> storeVariantIds = new ArrayList<>();
        List<CustomerProductQuoteVo> customerProductQuoteVos = customerProductQuoteDao.selectQuoteDetail(request);
        if (ListUtils.isEmpty(customerProductQuoteVos) && ListUtils.isNotEmpty(request.getStoreVariantIds())) {
            for (CustomerProductQuoteVo customerProductQuoteVo : customerProductQuoteVos) {
                storeVariantIds.add(customerProductQuoteVo.getStoreVariantId());
            }
            request.getStoreVariantIds().removeAll(storeVariantIds);
            if (ListUtils.isNotEmpty(request.getStoreVariantIds())){
                storeVariantIds = request.getStoreVariantIds();
                List<CustomerProductQuoteVo> customerProductQuoteVoList = storeProductVariantDao.selectQuoteDetailByIds(storeVariantIds);
                customerProductQuoteVos.addAll(customerProductQuoteVoList);
            }
        }
        return customerProductQuoteVos;
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