package com.upedge.ums.modules.user.service.impl;

import com.upedge.common.base.Page;
import com.upedge.ums.modules.user.dao.CustomerVipRecordDao;
import com.upedge.ums.modules.user.entity.CustomerVipRecord;
import com.upedge.ums.modules.user.service.CustomerVipRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class CustomerVipRecordServiceImpl implements CustomerVipRecordService {

    @Autowired
    private CustomerVipRecordDao customerVipRecordDao;



    /**
     *
     */
    @Transactional
    public int deleteByPrimaryKey(Integer id) {
        CustomerVipRecord record = new CustomerVipRecord();
        record.setId(id);
        return customerVipRecordDao.deleteByPrimaryKey(record);
    }

    /**
     *
     */
    @Transactional
    public int insert(CustomerVipRecord record) {
        return customerVipRecordDao.insert(record);
    }

    /**
     *
     */
    @Transactional
    public int insertSelective(CustomerVipRecord record) {
        return customerVipRecordDao.insert(record);
    }

    /**
     *
     */
    public CustomerVipRecord selectByPrimaryKey(Integer id){
        CustomerVipRecord record = new CustomerVipRecord();
        record.setId(id);
        return customerVipRecordDao.selectByPrimaryKey(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKeySelective(CustomerVipRecord record) {
        return customerVipRecordDao.updateByPrimaryKeySelective(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKey(CustomerVipRecord record) {
        return customerVipRecordDao.updateByPrimaryKey(record);
    }

    /**
    *
    */
    public List<CustomerVipRecord> select(Page<CustomerVipRecord> record){
        record.initFromNum();
        return customerVipRecordDao.select(record);
    }

    /**
    *
    */
    public long count(Page<CustomerVipRecord> record){
        return customerVipRecordDao.count(record);
    }

}