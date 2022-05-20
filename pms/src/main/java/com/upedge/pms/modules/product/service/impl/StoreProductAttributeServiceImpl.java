package com.upedge.pms.modules.product.service.impl;

import com.upedge.common.base.Page;
import com.upedge.common.utils.IdGenerate;
import com.upedge.pms.modules.product.dao.StoreProductAttributeDao;
import com.upedge.pms.modules.product.entity.StoreProductAttribute;
import com.upedge.pms.modules.product.request.StoreProductListRequest;
import com.upedge.pms.modules.product.service.StoreProductAttributeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;


@Service
public class StoreProductAttributeServiceImpl implements StoreProductAttributeService {

    @Autowired
    private StoreProductAttributeDao storeProductAttributeDao;



    /**
     *
     */
    @Transactional
    public int deleteByPrimaryKey(Long id) {
        StoreProductAttribute record = new StoreProductAttribute();
        record.setId(id);
        return storeProductAttributeDao.deleteByPrimaryKey(record);
    }

    /**
     *
     */
    @Transactional
    public int insert(StoreProductAttribute record) {
        return storeProductAttributeDao.insert(record);
    }

    /**
     *
     */
    @Transactional
    public int insertSelective(StoreProductAttribute record) {
        return storeProductAttributeDao.insert(record);
    }

    @Override
    public List<StoreProductAttribute> selectStoreProduct(StoreProductListRequest request) {
        return storeProductAttributeDao.selectStoreProduct(request);
    }

    @Override
    public StoreProductAttribute saveDefaultCustomProduct(Long customerId) {
        StoreProductAttribute storeProductAttribute = storeProductAttributeDao.selectCustomerDefaultProduct(customerId);
        if (storeProductAttribute != null){
            return storeProductAttribute;
        }
        Date date = new Date();
        storeProductAttribute = new StoreProductAttribute();
        storeProductAttribute.setId(IdGenerate.nextId());
        storeProductAttribute.setTitle("default title");
        storeProductAttribute.setStoreId(0L);
        storeProductAttribute.setPlatProductId("0");
        storeProductAttribute.setStoreName("default store");
        storeProductAttribute.setRelateState(0);
        storeProductAttribute.setTransformState(0);
        storeProductAttribute.setImportTime(date);
        storeProductAttribute.setCreateAt(date);
        storeProductAttribute.setUpdateAt(date);
        storeProductAttribute.setPrice("0");
        storeProductAttribute.setCustomerId(customerId);
        insert(storeProductAttribute);
        return storeProductAttribute;
    }

    @Override
    public Long countStoreProduct(StoreProductListRequest request) {
        return storeProductAttributeDao.countStoreProduct(request);
    }

    /**
     *
     */
    public StoreProductAttribute selectByPrimaryKey(Long id){
        return storeProductAttributeDao.selectByPrimaryKey(id);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKeySelective(StoreProductAttribute record) {
        return storeProductAttributeDao.updateByPrimaryKeySelective(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKey(StoreProductAttribute record) {
        return storeProductAttributeDao.updateByPrimaryKey(record);
    }

    /**
    *
    */
    public List<StoreProductAttribute> select(Page<StoreProductAttribute> record){
        record.initFromNum();
        return storeProductAttributeDao.select(record);
    }

    /**
    *
    */
    public long count(Page<StoreProductAttribute> record){
        return storeProductAttributeDao.count(record);
    }

}