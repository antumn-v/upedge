package com.upedge.pms.modules.product.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import com.upedge.common.base.Page;
import org.springframework.beans.factory.annotation.Autowired;
import com.upedge.pms.modules.product.dao.ImportPublishedRecordDao;
import com.upedge.pms.modules.product.entity.ImportPublishedRecord;
import com.upedge.pms.modules.product.service.ImportPublishedRecordService;


@Service
public class ImportPublishedRecordServiceImpl implements ImportPublishedRecordService {

    @Autowired
    private ImportPublishedRecordDao importPublishedRecordDao;



    /**
     *
     */
    @Transactional
    public int deleteByPrimaryKey(Long importProductId) {
        ImportPublishedRecord record = new ImportPublishedRecord();
        record.setImportProductId(importProductId);
        return importPublishedRecordDao.deleteByPrimaryKey(record);
    }

    /**
     *
     */
    @Transactional
    public int insert(ImportPublishedRecord record) {
        return importPublishedRecordDao.insert(record);
    }

    /**
     *
     */
    @Transactional
    public int insertSelective(ImportPublishedRecord record) {
        return importPublishedRecordDao.insert(record);
    }

    /**
     *
     */
    public ImportPublishedRecord selectByPrimaryKey(Long importProductId){
        ImportPublishedRecord record = new ImportPublishedRecord();
        record.setImportProductId(importProductId);
        return importPublishedRecordDao.selectByPrimaryKey(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKeySelective(ImportPublishedRecord record) {
        return importPublishedRecordDao.updateByPrimaryKeySelective(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKey(ImportPublishedRecord record) {
        return importPublishedRecordDao.updateByPrimaryKey(record);
    }

    /**
    *
    */
    public List<ImportPublishedRecord> select(Page<ImportPublishedRecord> record){
        record.initFromNum();
        return importPublishedRecordDao.select(record);
    }

    /**
    *
    */
    public long count(Page<ImportPublishedRecord> record){
        return importPublishedRecordDao.count(record);
    }

}