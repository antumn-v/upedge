package com.upedge.ums.modules.user.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import com.upedge.common.base.Page;
import org.springframework.beans.factory.annotation.Autowired;
import com.upedge.ums.modules.user.dao.CustomerDao;
import com.upedge.ums.modules.user.entity.Customer;
import com.upedge.ums.modules.user.service.CustomerService;


@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerDao customerDao;



    /**
     *
     */
    @Transactional
    public int deleteByPrimaryKey(Long id) {
        Customer record = new Customer();
        record.setId(id);
        return customerDao.deleteByPrimaryKey(record);
    }

    /**
     *
     */
    @Transactional
    public int insert(Customer record) {
        return customerDao.insert(record);
    }

    /**
     *
     */
    @Transactional
    public int insertSelective(Customer record) {
        return customerDao.insert(record);
    }

    /**
     *
     */
    public Customer selectByPrimaryKey(Long id){
        Customer record = new Customer();
        record.setId(id);
        return customerDao.selectByPrimaryKey(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKeySelective(Customer record) {
        return customerDao.updateByPrimaryKeySelective(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKey(Customer record) {
        return customerDao.updateByPrimaryKey(record);
    }

    /**
    *
    */
    public List<Customer> select(Page<Customer> record){
        record.initFromNum();
        return customerDao.select(record);
    }

    /**
    *
    */
    public long count(Page<Customer> record){
        return customerDao.count(record);
    }

}