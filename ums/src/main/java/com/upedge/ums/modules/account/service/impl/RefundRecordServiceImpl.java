package com.upedge.ums.modules.account.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import com.upedge.common.base.Page;
import org.springframework.beans.factory.annotation.Autowired;
import com.upedge.ums.modules.account.dao.RefundRecordDao;
import com.upedge.ums.modules.account.entity.RefundRecord;
import com.upedge.ums.modules.account.service.RefundRecordService;


@Service
public class RefundRecordServiceImpl implements RefundRecordService {

    @Autowired
    private RefundRecordDao refundRecordDao;



    /**
     *
     */
    @Transactional
    public int deleteByPrimaryKey(Integer id) {
        RefundRecord record = new RefundRecord();
        record.setId(id);
        return refundRecordDao.deleteByPrimaryKey(record);
    }

    /**
     *
     */
    @Transactional
    public int insert(RefundRecord record) {
        return refundRecordDao.insert(record);
    }

    /**
     *
     */
    @Transactional
    public int insertSelective(RefundRecord record) {
        return refundRecordDao.insert(record);
    }

    /**
     *
     */
    public RefundRecord selectByPrimaryKey(Integer id){
        RefundRecord record = new RefundRecord();
        record.setId(id);
        return refundRecordDao.selectByPrimaryKey(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKeySelective(RefundRecord record) {
        return refundRecordDao.updateByPrimaryKeySelective(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKey(RefundRecord record) {
        return refundRecordDao.updateByPrimaryKey(record);
    }

    /**
    *
    */
    public List<RefundRecord> select(Page<RefundRecord> record){
        record.initFromNum();
        return refundRecordDao.select(record);
    }

    /**
    *
    */
    public long count(Page<RefundRecord> record){
        return refundRecordDao.count(record);
    }

}