package com.upedge.oms.modules.common.service.impl;

import com.upedge.common.base.Page;
import com.upedge.oms.modules.common.dao.SaiheOrderRecordDao;
import com.upedge.oms.modules.common.entity.SaiheOrderRecord;
import com.upedge.oms.modules.common.service.SaiheOrderRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class SaiheOrderRecordServiceImpl implements SaiheOrderRecordService {

    @Autowired
    private SaiheOrderRecordDao saiheOrderRecordDao;



    /**
     *
     */
    @Transactional
    public int deleteByPrimaryKey(Long id) {
        SaiheOrderRecord record = new SaiheOrderRecord();
        record.setId(id);
        return saiheOrderRecordDao.deleteByPrimaryKey(record);
    }

    /**
     *
     */
    @Transactional
    public int insert(SaiheOrderRecord record) {
        return saiheOrderRecordDao.insert(record);
    }

    /**
     *
     */
    @Transactional
    public int insertSelective(SaiheOrderRecord record) {
        return saiheOrderRecordDao.insert(record);
    }

    @Override
    public List<SaiheOrderRecord> selectTwiceUploadOrder() {
        return saiheOrderRecordDao.selectTwiceUploadOrder();
    }

    /**
     *
     */
    public SaiheOrderRecord selectByPrimaryKey(Long id){
        SaiheOrderRecord record = new SaiheOrderRecord();
        record.setId(id);
        return saiheOrderRecordDao.selectByPrimaryKey(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKeySelective(SaiheOrderRecord record) {
        return saiheOrderRecordDao.updateByPrimaryKeySelective(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKey(SaiheOrderRecord record) {
        return saiheOrderRecordDao.updateByPrimaryKey(record);
    }

    /**
    *
    */
    public List<SaiheOrderRecord> select(Page<SaiheOrderRecord> record){
        record.initFromNum();
        return saiheOrderRecordDao.select(record);
    }

    /**
    *
    */
    public long count(Page<SaiheOrderRecord> record){
        return saiheOrderRecordDao.count(record);
    }

}