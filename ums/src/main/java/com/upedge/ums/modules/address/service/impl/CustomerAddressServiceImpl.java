package com.upedge.ums.modules.address.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import com.upedge.common.base.Page;
import org.springframework.beans.factory.annotation.Autowired;
import com.upedge.ums.modules.address.dao.CustomerAddressDao;
import com.upedge.ums.modules.address.entity.CustomerAddress;
import com.upedge.ums.modules.address.service.CustomerAddressService;


@Service
public class CustomerAddressServiceImpl implements CustomerAddressService {

    @Autowired
    private CustomerAddressDao customerAddressDao;



    /**
     *
     */
    @Transactional
    public int deleteByPrimaryKey(Long id) {
        CustomerAddress record = new CustomerAddress();
        record.setId(id);
        return customerAddressDao.deleteByPrimaryKey(record);
    }

    /**
     *
     */
    @Transactional
    public int insert(CustomerAddress record) {
        return customerAddressDao.insert(record);
    }

    /**
     *
     */
    @Transactional
    public int insertSelective(CustomerAddress record) {
        return customerAddressDao.insert(record);
    }

    /**
     *
     */
    public CustomerAddress selectByPrimaryKey(Long id){
        CustomerAddress record = new CustomerAddress();
        record.setId(id);
        return customerAddressDao.selectByPrimaryKey(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKeySelective(CustomerAddress record) {
        return customerAddressDao.updateByPrimaryKeySelective(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKey(CustomerAddress record) {
        return customerAddressDao.updateByPrimaryKey(record);
    }

    /**
    *
    */
    public List<CustomerAddress> select(Page<CustomerAddress> record){
        record.initFromNum();
        return customerAddressDao.select(record);
    }

    /**
    *
    */
    public long count(Page<CustomerAddress> record){
        return customerAddressDao.count(record);
    }

}