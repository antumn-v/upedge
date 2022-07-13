package com.upedge.pms.modules.purchase.service.impl;

import com.upedge.common.base.Page;
import com.upedge.pms.modules.purchase.dao.VariantWarehouseStockRecordDao;
import com.upedge.pms.modules.purchase.entity.VariantWarehouseStockRecord;
import com.upedge.pms.modules.purchase.service.VariantWarehouseStockRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class VariantWarehouseStockRecordServiceImpl implements VariantWarehouseStockRecordService {

    @Autowired
    private VariantWarehouseStockRecordDao variantWarehouseStockRecordDao;



    /**
     *
     */
    @Transactional
    public int deleteByPrimaryKey(Integer id) {
        VariantWarehouseStockRecord record = new VariantWarehouseStockRecord();
        record.setId(id);
        return variantWarehouseStockRecordDao.deleteByPrimaryKey(record);
    }

    /**
     *
     */
    @Transactional
    public int insert(VariantWarehouseStockRecord record) {
        return variantWarehouseStockRecordDao.insert(record);
    }

    /**
     *
     */
    @Transactional
    public int insertSelective(VariantWarehouseStockRecord record) {
        return variantWarehouseStockRecordDao.insert(record);
    }

    /**
     *
     */
    public VariantWarehouseStockRecord selectByPrimaryKey(Integer id){
        VariantWarehouseStockRecord record = new VariantWarehouseStockRecord();
        record.setId(id);
        return variantWarehouseStockRecordDao.selectByPrimaryKey(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKeySelective(VariantWarehouseStockRecord record) {
        return variantWarehouseStockRecordDao.updateByPrimaryKeySelective(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKey(VariantWarehouseStockRecord record) {
        return variantWarehouseStockRecordDao.updateByPrimaryKey(record);
    }

    /**
    *
    */
    public List<VariantWarehouseStockRecord> select(Page<VariantWarehouseStockRecord> record){
        record.initFromNum();
        return variantWarehouseStockRecordDao.select(record);
    }

    /**
    *
    */
    public long count(Page<VariantWarehouseStockRecord> record){
        return variantWarehouseStockRecordDao.count(record);
    }

}