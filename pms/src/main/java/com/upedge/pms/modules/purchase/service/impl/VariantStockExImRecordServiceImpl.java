package com.upedge.pms.modules.purchase.service.impl;

import com.upedge.common.base.Page;
import com.upedge.pms.modules.purchase.dao.VariantStockExImRecordDao;
import com.upedge.pms.modules.purchase.entity.VariantStockExImRecord;
import com.upedge.pms.modules.purchase.service.VariantStockExImRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class VariantStockExImRecordServiceImpl implements VariantStockExImRecordService {

    @Autowired
    private VariantStockExImRecordDao variantStockExImRecordDao;



    /**
     *
     */
    @Transactional
    public int deleteByPrimaryKey(Long id) {
        VariantStockExImRecord record = new VariantStockExImRecord();
        record.setId(id);
        return variantStockExImRecordDao.deleteByPrimaryKey(record);
    }

    /**
     *
     */
    @Transactional
    public int insert(VariantStockExImRecord record) {
        return variantStockExImRecordDao.insert(record);
    }

    /**
     *
     */
    @Transactional
    public int insertSelective(VariantStockExImRecord record) {
        return variantStockExImRecordDao.insert(record);
    }

    /**
     *
     */
    public VariantStockExImRecord selectByPrimaryKey(Long id){
        VariantStockExImRecord record = new VariantStockExImRecord();
        record.setId(id);
        return variantStockExImRecordDao.selectByPrimaryKey(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKeySelective(VariantStockExImRecord record) {
        return variantStockExImRecordDao.updateByPrimaryKeySelective(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKey(VariantStockExImRecord record) {
        return variantStockExImRecordDao.updateByPrimaryKey(record);
    }

    /**
    *
    */
    public List<VariantStockExImRecord> select(Page<VariantStockExImRecord> record){
        record.initFromNum();
        return variantStockExImRecordDao.select(record);
    }

    /**
    *
    */
    public long count(Page<VariantStockExImRecord> record){
        return variantStockExImRecordDao.count(record);
    }

}