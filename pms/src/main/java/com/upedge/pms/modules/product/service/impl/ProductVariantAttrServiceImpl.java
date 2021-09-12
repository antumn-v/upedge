package com.upedge.pms.modules.product.service.impl;

import com.upedge.common.utils.ListUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import com.upedge.common.base.Page;
import org.springframework.beans.factory.annotation.Autowired;
import com.upedge.pms.modules.product.dao.ProductVariantAttrDao;
import com.upedge.pms.modules.product.entity.ProductVariantAttr;
import com.upedge.pms.modules.product.service.ProductVariantAttrService;


@Service
public class ProductVariantAttrServiceImpl implements ProductVariantAttrService {

    @Autowired
    private ProductVariantAttrDao productVariantAttrDao;



    /**
     *
     */
    @Transactional
    public int deleteByPrimaryKey(Long id) {
        ProductVariantAttr record = new ProductVariantAttr();
        record.setId(id);
        return productVariantAttrDao.deleteByPrimaryKey(record);
    }

    /**
     *
     */
    @Transactional
    public int insert(ProductVariantAttr record) {
        return productVariantAttrDao.insert(record);
    }

    /**
     *
     */
    @Transactional
    public int insertSelective(ProductVariantAttr record) {
        return productVariantAttrDao.insert(record);
    }

    @Override
    public List<ProductVariantAttr> selectByVariantIds(List<Long> variantIds) {
        if (ListUtils.isNotEmpty(variantIds)){
            return productVariantAttrDao.selectByVariantIds(variantIds);
        }
        return null;
    }

    @Override
    public int insertByBatch(List<ProductVariantAttr> productVariantAttrs) {
        return productVariantAttrDao.insertByBatch(productVariantAttrs);
    }

    /**
     *
     */
    public ProductVariantAttr selectByPrimaryKey(Long id){
        ProductVariantAttr record = new ProductVariantAttr();
        record.setId(id);
        return productVariantAttrDao.selectByPrimaryKey(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKeySelective(ProductVariantAttr record) {
        return productVariantAttrDao.updateByPrimaryKeySelective(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKey(ProductVariantAttr record) {
        return productVariantAttrDao.updateByPrimaryKey(record);
    }

    /**
    *
    */
    public List<ProductVariantAttr> select(Page<ProductVariantAttr> record){
        record.initFromNum();
        return productVariantAttrDao.select(record);
    }

    /**
    *
    */
    public long count(Page<ProductVariantAttr> record){
        return productVariantAttrDao.count(record);
    }

}