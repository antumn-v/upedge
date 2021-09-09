package com.upedge.pms.modules.product.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import com.upedge.common.base.Page;
import org.springframework.beans.factory.annotation.Autowired;
import com.upedge.pms.modules.product.dao.ProductVariantDao;
import com.upedge.pms.modules.product.entity.ProductVariant;
import com.upedge.pms.modules.product.service.ProductVariantService;


@Service
public class ProductVariantServiceImpl implements ProductVariantService {

    @Autowired
    private ProductVariantDao productVariantDao;



    /**
     *
     */
    @Transactional
    public int deleteByPrimaryKey(Long id) {
        ProductVariant record = new ProductVariant();
        record.setId(id);
        return productVariantDao.deleteByPrimaryKey(record);
    }

    /**
     *
     */
    @Transactional
    public int insert(ProductVariant record) {
        return productVariantDao.insert(record);
    }

    /**
     *
     */
    @Transactional
    public int insertSelective(ProductVariant record) {
        return productVariantDao.insert(record);
    }

    @Override
    public int insertByBatch(List<ProductVariant> productVariants) {
        return productVariantDao.insertByBatch(productVariants);
    }

    /**
     *
     */
    public ProductVariant selectByPrimaryKey(Long id){
        ProductVariant record = new ProductVariant();
        record.setId(id);
        return productVariantDao.selectByPrimaryKey(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKeySelective(ProductVariant record) {
        return productVariantDao.updateByPrimaryKeySelective(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKey(ProductVariant record) {
        return productVariantDao.updateByPrimaryKey(record);
    }

    /**
    *
    */
    public List<ProductVariant> select(Page<ProductVariant> record){
        record.initFromNum();
        return productVariantDao.select(record);
    }

    /**
    *
    */
    public long count(Page<ProductVariant> record){
        return productVariantDao.count(record);
    }

}