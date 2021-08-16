package com.upedge.ums.modules.user.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import com.upedge.common.base.Page;
import org.springframework.beans.factory.annotation.Autowired;
import com.upedge.ums.modules.user.dao.CustomerApplicationDao;
import com.upedge.ums.modules.user.entity.CustomerApplication;
import com.upedge.ums.modules.user.service.CustomerApplicationService;


@Service
public class CustomerApplicationServiceImpl implements CustomerApplicationService {

    @Autowired
    private CustomerApplicationDao customerApplicationDao;



    /**
     *
     */
    @Transactional
    public int deleteByPrimaryKey(Long customerId) {
        CustomerApplication record = new CustomerApplication();
        record.setCustomerId(customerId);
        return customerApplicationDao.deleteByPrimaryKey(record);
    }

    /**
     *
     */
    @Transactional
    public int insert(CustomerApplication record) {
        return customerApplicationDao.insert(record);
    }

    /**
     *
     */
    @Transactional
    public int insertSelective(CustomerApplication record) {
        return customerApplicationDao.insert(record);
    }

    /**
     *
     */
    public CustomerApplication selectByPrimaryKey(Long customerId){
        CustomerApplication record = new CustomerApplication();
        record.setCustomerId(customerId);
        return customerApplicationDao.selectByPrimaryKey(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKeySelective(CustomerApplication record) {
        return customerApplicationDao.updateByPrimaryKeySelective(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKey(CustomerApplication record) {
        return customerApplicationDao.updateByPrimaryKey(record);
    }

    /**
    *
    */
    public List<CustomerApplication> select(Page<CustomerApplication> record){
        record.initFromNum();
        return customerApplicationDao.select(record);
    }

    /**
    *
    */
    public long count(Page<CustomerApplication> record){
        return customerApplicationDao.count(record);
    }

}