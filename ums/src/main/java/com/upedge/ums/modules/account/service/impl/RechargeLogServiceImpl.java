package com.upedge.ums.modules.account.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import com.upedge.common.base.Page;
import org.springframework.beans.factory.annotation.Autowired;
import com.upedge.ums.modules.account.dao.RechargeLogDao;
import com.upedge.ums.modules.account.entity.RechargeLog;
import com.upedge.ums.modules.account.service.RechargeLogService;


@Service
public class RechargeLogServiceImpl implements RechargeLogService {

    @Autowired
    private RechargeLogDao rechargeLogDao;



    /**
     *
     */
    @Transactional
    public int deleteByPrimaryKey(Long id) {
        RechargeLog record = new RechargeLog();
        record.setId(id);
        return rechargeLogDao.deleteByPrimaryKey(record);
    }

    /**
     *
     */
    @Transactional
    public int insert(RechargeLog record) {
        return rechargeLogDao.insert(record);
    }

    /**
     *
     */
    @Transactional
    public int insertSelective(RechargeLog record) {
        return rechargeLogDao.insert(record);
    }

    /**
     *
     */
    public RechargeLog selectByPrimaryKey(Long id){
        RechargeLog record = new RechargeLog();
        record.setId(id);
        return rechargeLogDao.selectByPrimaryKey(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKeySelective(RechargeLog record) {
        return rechargeLogDao.updateByPrimaryKeySelective(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKey(RechargeLog record) {
        return rechargeLogDao.updateByPrimaryKey(record);
    }

    /**
    *
    */
    public List<RechargeLog> select(Page<RechargeLog> record){
        record.initFromNum();
        return rechargeLogDao.select(record);
    }

    /**
    *
    */
    public long count(Page<RechargeLog> record){
        return rechargeLogDao.count(record);
    }

}