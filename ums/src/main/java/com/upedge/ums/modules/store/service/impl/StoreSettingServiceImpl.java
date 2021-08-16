package com.upedge.ums.modules.store.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import com.upedge.common.base.Page;
import org.springframework.beans.factory.annotation.Autowired;
import com.upedge.ums.modules.store.dao.StoreSettingDao;
import com.upedge.ums.modules.store.entity.StoreSetting;
import com.upedge.ums.modules.store.service.StoreSettingService;


@Service
public class StoreSettingServiceImpl implements StoreSettingService {

    @Autowired
    private StoreSettingDao storeSettingDao;



    /**
     *
     */
    @Transactional
    public int deleteByPrimaryKey(Integer id) {
        StoreSetting record = new StoreSetting();
        record.setId(id);
        return storeSettingDao.deleteByPrimaryKey(record);
    }

    /**
     *
     */
    @Transactional
    public int insert(StoreSetting record) {
        return storeSettingDao.insert(record);
    }

    /**
     *
     */
    @Transactional
    public int insertSelective(StoreSetting record) {
        return storeSettingDao.insert(record);
    }

    /**
     *
     */
    public StoreSetting selectByPrimaryKey(Integer id){
        StoreSetting record = new StoreSetting();
        record.setId(id);
        return storeSettingDao.selectByPrimaryKey(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKeySelective(StoreSetting record) {
        return storeSettingDao.updateByPrimaryKeySelective(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKey(StoreSetting record) {
        return storeSettingDao.updateByPrimaryKey(record);
    }

    /**
    *
    */
    public List<StoreSetting> select(Page<StoreSetting> record){
        record.initFromNum();
        return storeSettingDao.select(record);
    }

    /**
    *
    */
    public long count(Page<StoreSetting> record){
        return storeSettingDao.count(record);
    }

}