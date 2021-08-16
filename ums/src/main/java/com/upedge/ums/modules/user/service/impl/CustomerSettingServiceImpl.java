package com.upedge.ums.modules.user.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import com.upedge.common.base.Page;
import org.springframework.beans.factory.annotation.Autowired;
import com.upedge.ums.modules.user.dao.CustomerSettingDao;
import com.upedge.ums.modules.user.entity.CustomerSetting;
import com.upedge.ums.modules.user.service.CustomerSettingService;


@Service
public class CustomerSettingServiceImpl implements CustomerSettingService {

    @Autowired
    private CustomerSettingDao customerSettingDao;



    /**
     *
     */
    @Transactional
    public int deleteByPrimaryKey(Integer id) {
        CustomerSetting record = new CustomerSetting();
        record.setId(id);
        return customerSettingDao.deleteByPrimaryKey(record);
    }

    /**
     *
     */
    @Transactional
    public int insert(CustomerSetting record) {
        return customerSettingDao.insert(record);
    }

    @Override
    public int insertByBatch(List<CustomerSetting> customerSettings) {
        return customerSettingDao.insertByBatch(customerSettings);
    }

    /**
     *
     */
    @Transactional
    public int insertSelective(CustomerSetting record) {
        return customerSettingDao.insert(record);
    }

    /**
     *
     */
    public CustomerSetting selectByPrimaryKey(Integer id){
        CustomerSetting record = new CustomerSetting();
        record.setId(id);
        return customerSettingDao.selectByPrimaryKey(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKeySelective(CustomerSetting record) {
        return customerSettingDao.updateByPrimaryKeySelective(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKey(CustomerSetting record) {
        return customerSettingDao.updateByPrimaryKey(record);
    }

    /**
    *
    */
    public List<CustomerSetting> select(Page<CustomerSetting> record){
        record.initFromNum();
        return customerSettingDao.select(record);
    }

    /**
    *
    */
    public long count(Page<CustomerSetting> record){
        return customerSettingDao.count(record);
    }

}