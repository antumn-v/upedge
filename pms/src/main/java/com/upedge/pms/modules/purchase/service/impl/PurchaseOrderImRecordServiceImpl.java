package com.upedge.pms.modules.purchase.service.impl;

import com.upedge.common.base.Page;
import com.upedge.common.utils.ListUtils;
import com.upedge.pms.modules.purchase.dao.PurchaseOrderImRecordDao;
import com.upedge.pms.modules.purchase.entity.PurchaseOrderImRecord;
import com.upedge.pms.modules.purchase.service.PurchaseOrderImRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class PurchaseOrderImRecordServiceImpl implements PurchaseOrderImRecordService {

    @Autowired
    private PurchaseOrderImRecordDao purchaseOrderImRecordDao;



    /**
     *
     */
    @Transactional
    public int deleteByPrimaryKey(Long id) {
        PurchaseOrderImRecord record = new PurchaseOrderImRecord();
        record.setId(id);
        return purchaseOrderImRecordDao.deleteByPrimaryKey(record);
    }

    /**
     *
     */
    @Transactional
    public int insert(PurchaseOrderImRecord record) {
        return purchaseOrderImRecordDao.insert(record);
    }

    @Override
    public int insertByBatch(List<PurchaseOrderImRecord> records) {
        if (ListUtils.isNotEmpty(records)){
            return purchaseOrderImRecordDao.insertByBatch(records);
        }
        return 0;
    }

    /**
     *
     */
    @Transactional
    public int insertSelective(PurchaseOrderImRecord record) {
        return purchaseOrderImRecordDao.insert(record);
    }

    /**
     *
     */
    public PurchaseOrderImRecord selectByPrimaryKey(Long id){
        PurchaseOrderImRecord record = new PurchaseOrderImRecord();
        record.setId(id);
        return purchaseOrderImRecordDao.selectByPrimaryKey(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKeySelective(PurchaseOrderImRecord record) {
        return purchaseOrderImRecordDao.updateByPrimaryKeySelective(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKey(PurchaseOrderImRecord record) {
        return purchaseOrderImRecordDao.updateByPrimaryKey(record);
    }

    /**
    *
    */
    public List<PurchaseOrderImRecord> select(Page<PurchaseOrderImRecord> record){
        record.initFromNum();
        return purchaseOrderImRecordDao.select(record);
    }

    /**
    *
    */
    public long count(Page<PurchaseOrderImRecord> record){
        return purchaseOrderImRecordDao.count(record);
    }

}