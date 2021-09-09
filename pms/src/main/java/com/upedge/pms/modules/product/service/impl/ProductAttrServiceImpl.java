package com.upedge.pms.modules.product.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import com.upedge.common.base.Page;
import org.springframework.beans.factory.annotation.Autowired;
import com.upedge.pms.modules.product.dao.ProductAttrDao;
import com.upedge.pms.modules.product.entity.ProductAttr;
import com.upedge.pms.modules.product.service.ProductAttrService;


@Service
public class ProductAttrServiceImpl implements ProductAttrService {

    @Autowired
    private ProductAttrDao productAttrDao;



    /**
     *
     */
    @Transactional
    public int deleteByPrimaryKey(Long id) {
        ProductAttr record = new ProductAttr();
        record.setId(id);
        return productAttrDao.deleteByPrimaryKey(record);
    }

    /**
     *
     */
    @Transactional
    public int insert(ProductAttr record) {
        return productAttrDao.insert(record);
    }

    /**
     *
     */
    @Transactional
    public int insertSelective(ProductAttr record) {
        return productAttrDao.insert(record);
    }

    /**
     *
     */
    public ProductAttr selectByPrimaryKey(Long id){
        ProductAttr record = new ProductAttr();
        record.setId(id);
        return productAttrDao.selectByPrimaryKey(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKeySelective(ProductAttr record) {
        return productAttrDao.updateByPrimaryKeySelective(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKey(ProductAttr record) {
        return productAttrDao.updateByPrimaryKey(record);
    }

    /**
    *
    */
    public List<ProductAttr> select(Page<ProductAttr> record){
        record.initFromNum();
        return productAttrDao.select(record);
    }

    /**
    *
    */
    public long count(Page<ProductAttr> record){
        return productAttrDao.count(record);
    }

}