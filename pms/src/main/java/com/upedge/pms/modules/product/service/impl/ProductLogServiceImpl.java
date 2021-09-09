package com.upedge.pms.modules.product.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import com.upedge.common.base.Page;
import org.springframework.beans.factory.annotation.Autowired;
import com.upedge.pms.modules.product.dao.ProductLogDao;
import com.upedge.pms.modules.product.entity.ProductLog;
import com.upedge.pms.modules.product.service.ProductLogService;


@Service
public class ProductLogServiceImpl implements ProductLogService {

    @Autowired
    private ProductLogDao productLogDao;



    /**
     *
     */
    @Transactional
    public int deleteByPrimaryKey(Long id) {
        ProductLog record = new ProductLog();
        record.setId(id);
        return productLogDao.deleteByPrimaryKey(record);
    }

    /**
     *
     */
    @Transactional
    public int insert(ProductLog record) {
        return productLogDao.insert(record);
    }

    /**
     *
     */
    @Transactional
    public int insertSelective(ProductLog record) {
        return productLogDao.insert(record);
    }

    /**
     *
     */
    public ProductLog selectByPrimaryKey(Long id){
        ProductLog record = new ProductLog();
        record.setId(id);
        return productLogDao.selectByPrimaryKey(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKeySelective(ProductLog record) {
        return productLogDao.updateByPrimaryKeySelective(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKey(ProductLog record) {
        return productLogDao.updateByPrimaryKey(record);
    }

    /**
    *
    */
    public List<ProductLog> select(Page<ProductLog> record){
        record.initFromNum();
        return productLogDao.select(record);
    }

    /**
    *
    */
    public long count(Page<ProductLog> record){
        return productLogDao.count(record);
    }

}