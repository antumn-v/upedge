package com.upedge.pms.modules.product.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import com.upedge.common.base.Page;
import org.springframework.beans.factory.annotation.Autowired;
import com.upedge.pms.modules.product.dao.ProductInfoDao;
import com.upedge.pms.modules.product.entity.ProductInfo;
import com.upedge.pms.modules.product.service.ProductInfoService;


@Service
public class ProductInfoServiceImpl implements ProductInfoService {

    @Autowired
    private ProductInfoDao productInfoDao;



    /**
     *
     */
    @Transactional
    public int deleteByPrimaryKey(Long id) {
        ProductInfo record = new ProductInfo();
        record.setId(id);
        return productInfoDao.deleteByPrimaryKey(record);
    }

    /**
     *
     */
    @Transactional
    public int insert(ProductInfo record) {
        return productInfoDao.insert(record);
    }

    /**
     *
     */
    @Transactional
    public int insertSelective(ProductInfo record) {
        return productInfoDao.insert(record);
    }

    @Override
    public ProductInfo selectByProductId(Long id) {
        return productInfoDao.selectByProductId(id);
    }

    /**
     *
     */
    public ProductInfo selectByPrimaryKey(Long id){
        ProductInfo record = new ProductInfo();
        record.setId(id);
        return productInfoDao.selectByPrimaryKey(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKeySelective(ProductInfo record) {
        return productInfoDao.updateByPrimaryKeySelective(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKey(ProductInfo record) {
        return productInfoDao.updateByPrimaryKey(record);
    }

    /**
    *
    */
    public List<ProductInfo> select(Page<ProductInfo> record){
        record.initFromNum();
        return productInfoDao.select(record);
    }

    /**
    *
    */
    public long count(Page<ProductInfo> record){
        return productInfoDao.count(record);
    }

}