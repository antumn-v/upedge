package com.upedge.pms.modules.product.service.impl;

import com.upedge.common.base.Page;
import com.upedge.common.model.pms.quote.CustomerProductQuoteVo;
import com.upedge.pms.modules.product.dao.StoreProductVariantDao;
import com.upedge.pms.modules.product.entity.StoreProductVariant;
import com.upedge.pms.modules.product.service.StoreProductVariantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class StoreProductVariantServiceImpl implements StoreProductVariantService {

    @Autowired
    private StoreProductVariantDao storeProductVariantDao;



    /**
     *
     */
    @Transactional
    public int deleteByPrimaryKey(Long id) {
        StoreProductVariant record = new StoreProductVariant();
        record.setId(id);
        return storeProductVariantDao.deleteByPrimaryKey(record);
    }

    /**
     *
     */
    @Transactional
    public int insert(StoreProductVariant record) {
        return storeProductVariantDao.insert(record);
    }

    /**
     *
     */
    @Transactional
    public int insertSelective(StoreProductVariant record) {
        return storeProductVariantDao.insert(record);
    }

    @Override
    public List<CustomerProductQuoteVo> selectQuoteDetailByIds(List<Long> ids) {
        return storeProductVariantDao.selectQuoteDetailByIds(ids);
    }

    /**
     *
     */
    public StoreProductVariant selectByPrimaryKey(Long id){
        StoreProductVariant record = new StoreProductVariant();
        record.setId(id);
        return storeProductVariantDao.selectByPrimaryKey(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKeySelective(StoreProductVariant record) {
        return storeProductVariantDao.updateByPrimaryKeySelective(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKey(StoreProductVariant record) {
        return storeProductVariantDao.updateByPrimaryKey(record);
    }

    /**
    *
    */
    public List<StoreProductVariant> select(Page<StoreProductVariant> record){
        record.initFromNum();
        return storeProductVariantDao.select(record);
    }

    /**
    *
    */
    public long count(Page<StoreProductVariant> record){
        return storeProductVariantDao.count(record);
    }

}