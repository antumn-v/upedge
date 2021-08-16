package com.upedge.ums.modules.account.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import com.upedge.common.base.Page;
import org.springframework.beans.factory.annotation.Autowired;
import com.upedge.ums.modules.account.dao.RechargeRequestLogDao;
import com.upedge.ums.modules.account.entity.RechargeRequestLog;
import com.upedge.ums.modules.account.service.RechargeRequestLogService;


@Service
public class RechargeRequestLogServiceImpl implements RechargeRequestLogService {

    @Autowired
    private RechargeRequestLogDao rechargeRequestLogDao;



    /**
     *
     */
    @Transactional
    public int deleteByPrimaryKey(Long id) {
        RechargeRequestLog record = new RechargeRequestLog();
        record.setId(id);
        return rechargeRequestLogDao.deleteByPrimaryKey(record);
    }

    /**
     *
     */
    @Transactional
    public int insert(RechargeRequestLog record) {
        return rechargeRequestLogDao.insert(record);
    }

    /**
     *
     */
    @Transactional
    public int insertSelective(RechargeRequestLog record) {
        return rechargeRequestLogDao.insert(record);
    }

    /**
     *
     */
    public RechargeRequestLog selectByPrimaryKey(Long id){
        RechargeRequestLog record = new RechargeRequestLog();
        record.setId(id);
        return rechargeRequestLogDao.selectByPrimaryKey(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKeySelective(RechargeRequestLog record) {
        return rechargeRequestLogDao.updateByPrimaryKeySelective(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKey(RechargeRequestLog record) {
        return rechargeRequestLogDao.updateByPrimaryKey(record);
    }

    /**
    *
    */
    public List<RechargeRequestLog> select(Page<RechargeRequestLog> record){
        record.initFromNum();
        return rechargeRequestLogDao.select(record);
    }

    /**
    *
    */
    public long count(Page<RechargeRequestLog> record){
        return rechargeRequestLogDao.count(record);
    }

}