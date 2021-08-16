package com.upedge.ums.modules.store.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import com.upedge.common.base.Page;
import org.springframework.beans.factory.annotation.Autowired;
import com.upedge.ums.modules.store.dao.StoreDao;
import com.upedge.ums.modules.store.entity.Store;
import com.upedge.ums.modules.store.service.StoreService;


@Service
public class StoreServiceImpl implements StoreService {

    @Autowired
    private StoreDao storeDao;



    /**
     *
     */
    @Transactional
    public int deleteByPrimaryKey(Long id) {
        Store record = new Store();
        record.setId(id);
        return storeDao.deleteByPrimaryKey(record);
    }

    /**
     *
     */
    @Transactional
    public int insert(Store record) {
        return storeDao.insert(record);
    }

    /**
     *
     */
    @Transactional
    public int insertSelective(Store record) {
        return storeDao.insert(record);
    }

    /**
     *
     */
    public Store selectByPrimaryKey(Long id){
        Store record = new Store();
        record.setId(id);
        return storeDao.selectByPrimaryKey(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKeySelective(Store record) {
        return storeDao.updateByPrimaryKeySelective(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKey(Store record) {
        return storeDao.updateByPrimaryKey(record);
    }

    /**
    *
    */
    public List<Store> select(Page<Store> record){
        record.initFromNum();
        return storeDao.select(record);
    }

    /**
    *
    */
    public long count(Page<Store> record){
        return storeDao.count(record);
    }

}