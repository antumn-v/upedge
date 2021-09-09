package com.upedge.pms.modules.product.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import com.upedge.common.base.Page;
import org.springframework.beans.factory.annotation.Autowired;
import com.upedge.pms.modules.product.dao.CustomerPrivateProductDao;
import com.upedge.pms.modules.product.entity.CustomerPrivateProduct;
import com.upedge.pms.modules.product.service.CustomerPrivateProductService;


@Service
public class CustomerPrivateProductServiceImpl implements CustomerPrivateProductService {

    @Autowired
    private CustomerPrivateProductDao customerPrivateProductDao;



    /**
     *
     */
    @Transactional
    public int deleteByPrimaryKey(Long id) {
        CustomerPrivateProduct record = new CustomerPrivateProduct();
        record.setId(id);
        return customerPrivateProductDao.deleteByPrimaryKey(record);
    }

    /**
     *
     */
    @Transactional
    public int insert(CustomerPrivateProduct record) {
        return customerPrivateProductDao.insert(record);
    }

    /**
     *
     */
    @Transactional
    public int insertSelective(CustomerPrivateProduct record) {
        return customerPrivateProductDao.insert(record);
    }

    /**
     *
     */
    public CustomerPrivateProduct selectByPrimaryKey(Long id){
        CustomerPrivateProduct record = new CustomerPrivateProduct();
        record.setId(id);
        return customerPrivateProductDao.selectByPrimaryKey(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKeySelective(CustomerPrivateProduct record) {
        return customerPrivateProductDao.updateByPrimaryKeySelective(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKey(CustomerPrivateProduct record) {
        return customerPrivateProductDao.updateByPrimaryKey(record);
    }

    /**
    *
    */
    public List<CustomerPrivateProduct> select(Page<CustomerPrivateProduct> record){
        record.initFromNum();
        return customerPrivateProductDao.select(record);
    }

    /**
    *
    */
    public long count(Page<CustomerPrivateProduct> record){
        return customerPrivateProductDao.count(record);
    }

}