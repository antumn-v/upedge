package com.upedge.oms.modules.pack.service.impl;

import com.upedge.common.base.Page;
import com.upedge.oms.modules.pack.dao.PackageReplaceRecordDao;
import com.upedge.oms.modules.pack.entity.PackageReplaceRecord;
import com.upedge.oms.modules.pack.service.PackageReplaceRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class PackageReplaceRecordServiceImpl implements PackageReplaceRecordService {

    @Autowired
    private PackageReplaceRecordDao packageReplaceRecordDao;



    /**
     *
     */
    @Transactional
    public int deleteByPrimaryKey(Integer id) {
        PackageReplaceRecord record = new PackageReplaceRecord();
        record.setId(id);
        return packageReplaceRecordDao.deleteByPrimaryKey(record);
    }

    /**
     *
     */
    @Transactional
    public int insert(PackageReplaceRecord record) {
        return packageReplaceRecordDao.insert(record);
    }

    /**
     *
     */
    @Transactional
    public int insertSelective(PackageReplaceRecord record) {
        return packageReplaceRecordDao.insert(record);
    }

    /**
     *
     */
    public PackageReplaceRecord selectByPrimaryKey(Integer id){
        PackageReplaceRecord record = new PackageReplaceRecord();
        record.setId(id);
        return packageReplaceRecordDao.selectByPrimaryKey(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKeySelective(PackageReplaceRecord record) {
        return packageReplaceRecordDao.updateByPrimaryKeySelective(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKey(PackageReplaceRecord record) {
        return packageReplaceRecordDao.updateByPrimaryKey(record);
    }

    /**
    *
    */
    public List<PackageReplaceRecord> select(Page<PackageReplaceRecord> record){
        record.initFromNum();
        return packageReplaceRecordDao.select(record);
    }

    /**
    *
    */
    public long count(Page<PackageReplaceRecord> record){
        return packageReplaceRecordDao.count(record);
    }

}