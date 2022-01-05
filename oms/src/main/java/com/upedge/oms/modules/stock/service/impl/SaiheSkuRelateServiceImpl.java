package com.upedge.oms.modules.stock.service.impl;

import com.upedge.common.base.Page;
import com.upedge.oms.modules.stock.dao.SaiheSkuRelateDao;
import com.upedge.oms.modules.stock.entity.SaiheSkuRelate;
import com.upedge.oms.modules.stock.service.SaiheSkuRelateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class SaiheSkuRelateServiceImpl implements SaiheSkuRelateService {

    @Autowired
    private SaiheSkuRelateDao saiheSkuRelateDao;



    /**
     *
     */
    @Transactional
    public int deleteByPrimaryKey(String variantSku) {
        SaiheSkuRelate record = new SaiheSkuRelate();
        record.setVariantSku(variantSku);
        return saiheSkuRelateDao.deleteByPrimaryKey(record);
    }

    /**
     *
     */
    @Transactional
    public int insert(SaiheSkuRelate record) {
        return saiheSkuRelateDao.insert(record);
    }

    /**
     *
     */
    @Transactional
    public int insertSelective(SaiheSkuRelate record) {
        return saiheSkuRelateDao.insert(record);
    }

    /**
     *
     */
    public SaiheSkuRelate selectByPrimaryKey(String variantSku){
        return saiheSkuRelateDao.selectByPrimaryKey(variantSku);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKeySelective(SaiheSkuRelate record) {
        return saiheSkuRelateDao.updateByPrimaryKeySelective(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKey(SaiheSkuRelate record) {
        return saiheSkuRelateDao.updateByPrimaryKey(record);
    }

    /**
    *
    */
    public List<SaiheSkuRelate> select(Page<SaiheSkuRelate> record){
        record.initFromNum();
        return saiheSkuRelateDao.select(record);
    }

    /**
    *
    */
    public long count(Page<SaiheSkuRelate> record){
        return saiheSkuRelateDao.count(record);
    }

}