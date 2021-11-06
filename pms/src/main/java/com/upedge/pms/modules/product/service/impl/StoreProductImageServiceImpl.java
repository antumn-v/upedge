package com.upedge.pms.modules.product.service.impl;

import com.upedge.common.base.Page;
import com.upedge.pms.modules.product.dao.StoreProductImageDao;
import com.upedge.pms.modules.product.entity.StoreProductImage;
import com.upedge.pms.modules.product.service.StoreProductImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class StoreProductImageServiceImpl implements StoreProductImageService {

    @Autowired
    private StoreProductImageDao storeProductImageDao;



    /**
     *
     */
    @Transactional
    public int deleteByPrimaryKey(Long id) {
        StoreProductImage record = new StoreProductImage();
        record.setId(id);
        return storeProductImageDao.deleteByPrimaryKey(record);
    }

    /**
     *
     */
    @Transactional
    public int insert(StoreProductImage record) {
        return storeProductImageDao.insert(record);
    }

    /**
     *
     */
    @Transactional
    public int insertSelective(StoreProductImage record) {
        return storeProductImageDao.insert(record);
    }

    /**
     *
     */
    public StoreProductImage selectByPrimaryKey(Long id){
        StoreProductImage record = new StoreProductImage();
        record.setId(id);
        return storeProductImageDao.selectByPrimaryKey(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKeySelective(StoreProductImage record) {
        return storeProductImageDao.updateByPrimaryKeySelective(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKey(StoreProductImage record) {
        return storeProductImageDao.updateByPrimaryKey(record);
    }

    /**
    *
    */
    public List<StoreProductImage> select(Page<StoreProductImage> record){
        record.initFromNum();
        return storeProductImageDao.select(record);
    }

    /**
    *
    */
    public long count(Page<StoreProductImage> record){
        return storeProductImageDao.count(record);
    }

}