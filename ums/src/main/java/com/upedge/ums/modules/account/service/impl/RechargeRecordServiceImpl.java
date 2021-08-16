package com.upedge.ums.modules.account.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import com.upedge.common.base.Page;
import org.springframework.beans.factory.annotation.Autowired;
import com.upedge.ums.modules.account.dao.RechargeRecordDao;
import com.upedge.ums.modules.account.entity.RechargeRecord;
import com.upedge.ums.modules.account.service.RechargeRecordService;


@Service
public class RechargeRecordServiceImpl implements RechargeRecordService {

    @Autowired
    private RechargeRecordDao rechargeRecordDao;



    /**
     *
     */
    @Transactional
    public int deleteByPrimaryKey(Integer id) {
        RechargeRecord record = new RechargeRecord();
        record.setId(id);
        return rechargeRecordDao.deleteByPrimaryKey(record);
    }

    /**
     *
     */
    @Transactional
    public int insert(RechargeRecord record) {
        return rechargeRecordDao.insert(record);
    }

    /**
     *
     */
    @Transactional
    public int insertSelective(RechargeRecord record) {
        return rechargeRecordDao.insert(record);
    }

    /**
     *
     */
    public RechargeRecord selectByPrimaryKey(Integer id){
        RechargeRecord record = new RechargeRecord();
        record.setId(id);
        return rechargeRecordDao.selectByPrimaryKey(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKeySelective(RechargeRecord record) {
        return rechargeRecordDao.updateByPrimaryKeySelective(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKey(RechargeRecord record) {
        return rechargeRecordDao.updateByPrimaryKey(record);
    }

    /**
    *
    */
    public List<RechargeRecord> select(Page<RechargeRecord> record){
        record.initFromNum();
        return rechargeRecordDao.select(record);
    }

    /**
    *
    */
    public long count(Page<RechargeRecord> record){
        return rechargeRecordDao.count(record);
    }

}