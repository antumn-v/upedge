package com.upedge.pms.modules.product.service.impl;

import com.upedge.common.base.Page;
import com.upedge.pms.modules.product.dao.VariantSkuUpdateLogDao;
import com.upedge.pms.modules.product.entity.VariantSkuUpdateLog;
import com.upedge.pms.modules.product.service.VariantSkuUpdateLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class VariantSkuUpdateLogServiceImpl implements VariantSkuUpdateLogService {

    @Autowired
    private VariantSkuUpdateLogDao variantSkuUpdateLogDao;



    /**
     *
     */
    @Transactional
    public int deleteByPrimaryKey(Long variantId) {
        VariantSkuUpdateLog record = new VariantSkuUpdateLog();
        record.setVariantId(variantId);
        return variantSkuUpdateLogDao.deleteByPrimaryKey(record);
    }

    /**
     *
     */
    @Transactional
    public int insert(VariantSkuUpdateLog record) {
        return variantSkuUpdateLogDao.insert(record);
    }

    /**
     *
     */
    @Transactional
    public int insertSelective(VariantSkuUpdateLog record) {
        return variantSkuUpdateLogDao.insert(record);
    }

    /**
     *
     */
    public VariantSkuUpdateLog selectByPrimaryKey(Long variantId){
        VariantSkuUpdateLog record = new VariantSkuUpdateLog();
        record.setVariantId(variantId);
        return variantSkuUpdateLogDao.selectByPrimaryKey(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKeySelective(VariantSkuUpdateLog record) {
        return variantSkuUpdateLogDao.updateByPrimaryKeySelective(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKey(VariantSkuUpdateLog record) {
        return variantSkuUpdateLogDao.updateByPrimaryKey(record);
    }

    /**
    *
    */
    public List<VariantSkuUpdateLog> select(Page<VariantSkuUpdateLog> record){
        record.initFromNum();
        return variantSkuUpdateLogDao.select(record);
    }

    /**
    *
    */
    public long count(Page<VariantSkuUpdateLog> record){
        return variantSkuUpdateLogDao.count(record);
    }

}