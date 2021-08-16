package com.upedge.ums.modules.store.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import com.upedge.common.base.Page;
import org.springframework.beans.factory.annotation.Autowired;
import com.upedge.ums.modules.store.dao.StoreAttrDao;
import com.upedge.ums.modules.store.entity.StoreAttr;
import com.upedge.ums.modules.store.service.StoreAttrService;


@Service
public class StoreAttrServiceImpl implements StoreAttrService {

    @Autowired
    private StoreAttrDao storeAttrDao;



    /**
     *
     */
    @Transactional
    public int deleteByPrimaryKey(Integer id) {
        StoreAttr record = new StoreAttr();
        record.setId(id);
        return storeAttrDao.deleteByPrimaryKey(record);
    }

    /**
     *
     */
    @Transactional
    public int insert(StoreAttr record) {
        return storeAttrDao.insert(record);
    }

    /**
     *
     */
    @Transactional
    public int insertSelective(StoreAttr record) {
        return storeAttrDao.insert(record);
    }

    /**
     *
     */
    public StoreAttr selectByPrimaryKey(Integer id){
        StoreAttr record = new StoreAttr();
        record.setId(id);
        return storeAttrDao.selectByPrimaryKey(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKeySelective(StoreAttr record) {
        return storeAttrDao.updateByPrimaryKeySelective(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKey(StoreAttr record) {
        return storeAttrDao.updateByPrimaryKey(record);
    }

    /**
    *
    */
    public List<StoreAttr> select(Page<StoreAttr> record){
        record.initFromNum();
        return storeAttrDao.select(record);
    }

    /**
    *
    */
    public long count(Page<StoreAttr> record){
        return storeAttrDao.count(record);
    }

}