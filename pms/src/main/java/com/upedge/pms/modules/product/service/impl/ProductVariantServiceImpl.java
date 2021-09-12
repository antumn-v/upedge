package com.upedge.pms.modules.product.service.impl;

import com.upedge.pms.modules.product.entity.ProductVariantAttr;
import com.upedge.pms.modules.product.service.ProductVariantAttrService;
import com.upedge.pms.modules.product.vo.VariantAttrVo;
import com.upedge.pms.modules.product.vo.VariantValVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import com.upedge.common.base.Page;
import org.springframework.beans.factory.annotation.Autowired;
import com.upedge.pms.modules.product.dao.ProductVariantDao;
import com.upedge.pms.modules.product.entity.ProductVariant;
import com.upedge.pms.modules.product.service.ProductVariantService;


@Service
public class ProductVariantServiceImpl implements ProductVariantService {

    @Autowired
    private ProductVariantDao productVariantDao;

    @Autowired
    ProductVariantAttrService productVariantAttrService;



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
    public List<ProductVariant> getProductVariantList(List<Long> variantIds, Map<String, VariantAttrVo> attrMap, Map<String, Set<String>> attrValSet, Map<Long, ProductVariant> productVariantMap) {
        //获取产品属性
        List<ProductVariantAttr> productVariantAttrList = productVariantAttrService.selectByVariantIds(variantIds);
        productVariantAttrList.forEach(productVariantAttr -> {

            //属性
            VariantAttrVo variantAttrVo = attrMap.get(productVariantAttr.getVariantAttrCname());
            if (variantAttrVo == null) {
                variantAttrVo = new VariantAttrVo();
                variantAttrVo.setCname(productVariantAttr.getVariantAttrCname());
                variantAttrVo.setEname(productVariantAttr.getVariantAttrEname());
                attrMap.put(productVariantAttr.getVariantAttrCname(), variantAttrVo);
                attrValSet.put(productVariantAttr.getVariantAttrCname(), new HashSet<>());
            }
            //获取属性的值列表
            Set<String> valSet = attrValSet.get(productVariantAttr.getVariantAttrCname());
            //属性值
            if (!valSet.contains(productVariantAttr.getOriginalAttrCvalue())) {
                VariantValVo variantValVo = new VariantValVo();
                variantValVo.setOriginalCvalue(productVariantAttr.getOriginalAttrCvalue());
                variantValVo.setCvalue(productVariantAttr.getVariantAttrCvalue());
                variantValVo.setEvalue(productVariantAttr.getVariantAttrEvalue());
                variantAttrVo.getVariantValVoList().add(variantValVo);
                valSet.add(productVariantAttr.getOriginalAttrCvalue());
            }

            ProductVariant variant = productVariantMap.get(productVariantAttr.getVariantId());
            variant.getProductVariantAttrList().add(productVariantAttr);
        });
        List<ProductVariant> productVariantList = new ArrayList(productVariantMap.values());
        return productVariantList;
    }

    @Override
    public List<ProductVariant> selectByProductId(Long productId) {
        return productVariantDao.selectByProductId(productId);
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