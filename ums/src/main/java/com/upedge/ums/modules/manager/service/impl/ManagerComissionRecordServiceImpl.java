package com.upedge.ums.modules.manager.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import com.upedge.common.base.Page;
import org.springframework.beans.factory.annotation.Autowired;
import com.upedge.ums.modules.manager.dao.ManagerComissionRecordDao;
import com.upedge.ums.modules.manager.entity.ManagerComissionRecord;
import com.upedge.ums.modules.manager.service.ManagerComissionRecordService;


@Service
public class ManagerComissionRecordServiceImpl implements ManagerComissionRecordService {

    @Autowired
    private ManagerComissionRecordDao managerComissionRecordDao;



    /**
     *
     */
    @Transactional
    public int deleteByPrimaryKey(Long id) {
        ManagerComissionRecord record = new ManagerComissionRecord();
        record.setId(id);
        return managerComissionRecordDao.deleteByPrimaryKey(record);
    }

    /**
     *
     */
    @Transactional
    public int insert(ManagerComissionRecord record) {
        return managerComissionRecordDao.insert(record);
    }

    /**
     *
     */
    @Transactional
    public int insertSelective(ManagerComissionRecord record) {
        return managerComissionRecordDao.insert(record);
    }

    /**
     *
     */
    public ManagerComissionRecord selectByPrimaryKey(Long id){
        ManagerComissionRecord record = new ManagerComissionRecord();
        record.setId(id);
        return managerComissionRecordDao.selectByPrimaryKey(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKeySelective(ManagerComissionRecord record) {
        return managerComissionRecordDao.updateByPrimaryKeySelective(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKey(ManagerComissionRecord record) {
        return managerComissionRecordDao.updateByPrimaryKey(record);
    }

    /**
    *
    */
    public List<ManagerComissionRecord> select(Page<ManagerComissionRecord> record){
        record.initFromNum();
        return managerComissionRecordDao.select(record);
    }

    /**
    *
    */
    public long count(Page<ManagerComissionRecord> record){
        return managerComissionRecordDao.count(record);
    }

}