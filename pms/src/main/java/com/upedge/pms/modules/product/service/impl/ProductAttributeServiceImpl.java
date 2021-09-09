package com.upedge.pms.modules.product.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import com.upedge.common.base.Page;
import org.springframework.beans.factory.annotation.Autowired;
import com.upedge.pms.modules.product.dao.ProductAttributeDao;
import com.upedge.pms.modules.product.entity.ProductAttribute;
import com.upedge.pms.modules.product.service.ProductAttributeService;


@Service
public class ProductAttributeServiceImpl implements ProductAttributeService {

    @Autowired
    private ProductAttributeDao productAttributeDao;



    /**
     *
     */
    @Transactional
    public int deleteByPrimaryKey(Long id) {
        ProductAttribute record = new ProductAttribute();
        record.setId(id);
        return productAttributeDao.deleteByPrimaryKey(record);
    }

    /**
     *
     */
    @Transactional
    public int insert(ProductAttribute record) {
        return productAttributeDao.insert(record);
    }

    /**
     *
     */
    @Transactional
    public int insertSelective(ProductAttribute record) {
        return productAttributeDao.insert(record);
    }

    /**
     *
     */
    public ProductAttribute selectByPrimaryKey(Long id){
        ProductAttribute record = new ProductAttribute();
        record.setId(id);
        return productAttributeDao.selectByPrimaryKey(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKeySelective(ProductAttribute record) {
        return productAttributeDao.updateByPrimaryKeySelective(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKey(ProductAttribute record) {
        return productAttributeDao.updateByPrimaryKey(record);
    }

    /**
    *
    */
    public List<ProductAttribute> select(Page<ProductAttribute> record){
        record.initFromNum();
        return productAttributeDao.select(record);
    }

    /**
    *
    */
    public long count(Page<ProductAttribute> record){
        return productAttributeDao.count(record);
    }

}