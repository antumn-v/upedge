package com.upedge.pms.modules.product.service.impl;

import com.upedge.common.base.Page;
import com.upedge.pms.modules.product.dao.StoreProductAttributeDao;
import com.upedge.pms.modules.product.entity.StoreProductAttribute;
import com.upedge.pms.modules.product.request.StoreProductListRequest;
import com.upedge.pms.modules.product.service.StoreProductAttributeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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