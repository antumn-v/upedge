package com.upedge.ums.modules.manager.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import com.upedge.common.base.Page;
import org.springframework.beans.factory.annotation.Autowired;
import com.upedge.ums.modules.manager.dao.CustomerManagerDao;
import com.upedge.ums.modules.manager.entity.CustomerManager;
import com.upedge.ums.modules.manager.service.CustomerManagerService;


@Service
public class CustomerManagerServiceImpl implements CustomerManagerService {

    @Autowired
    private CustomerManagerDao customerManagerDao;



    /**
     *
     */
    @Transactional
    public int deleteByPrimaryKey(Long customerId) {
        CustomerManager record = new CustomerManager();
        record.setCustomerId(customerId);
        return customerManagerDao.deleteByPrimaryKey(record);
    }

    /**
     *
     */
    @Transactional
    public int insert(CustomerManager record) {
        return customerManagerDao.insert(record);
    }

    /**
     *
     */
    @Transactional
    public int insertSelective(CustomerManager record) {
        return customerManagerDao.insert(record);
    }

    /**
     *
     */
    public CustomerManager selectByPrimaryKey(Long customerId){
        CustomerManager record = new CustomerManager();
        record.setCustomerId(customerId);
        return customerManagerDao.selectByPrimaryKey(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKeySelective(CustomerManager record) {
        return customerManagerDao.updateByPrimaryKeySelective(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKey(CustomerManager record) {
        return customerManagerDao.updateByPrimaryKey(record);
    }

    /**
    *
    */
    public List<CustomerManager> select(Page<CustomerManager> record){
        record.initFromNum();
        return customerManagerDao.select(record);
    }

    /**
    *
    */
    public long count(Page<CustomerManager> record){
        return customerManagerDao.count(record);
    }

}