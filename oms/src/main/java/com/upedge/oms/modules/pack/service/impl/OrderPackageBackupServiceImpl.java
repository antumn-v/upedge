package com.upedge.oms.modules.pack.service.impl;

import com.upedge.common.base.Page;
import com.upedge.oms.modules.pack.dao.OrderPackageBackupDao;
import com.upedge.oms.modules.pack.entity.OrderPackageBackup;
import com.upedge.oms.modules.pack.service.OrderPackageBackupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class OrderPackageBackupServiceImpl implements OrderPackageBackupService {

    @Autowired
    private OrderPackageBackupDao orderPackageBackupDao;



    /**
     *
     */
    @Transactional
    public int deleteByPrimaryKey(Long id) {
        OrderPackageBackup record = new OrderPackageBackup();
        record.setId(id);
        return orderPackageBackupDao.deleteByPrimaryKey(record);
    }

    /**
     *
     */
    @Transactional
    public int insert(OrderPackageBackup record) {
        return orderPackageBackupDao.insert(record);
    }

    /**
     *
     */
    @Transactional
    public int insertSelective(OrderPackageBackup record) {
        return orderPackageBackupDao.insert(record);
    }

    /**
     *
     */
    public OrderPackageBackup selectByPrimaryKey(Long id){
        OrderPackageBackup record = new OrderPackageBackup();
        record.setId(id);
        return orderPackageBackupDao.selectByPrimaryKey(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKeySelective(OrderPackageBackup record) {
        return orderPackageBackupDao.updateByPrimaryKeySelective(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKey(OrderPackageBackup record) {
        return orderPackageBackupDao.updateByPrimaryKey(record);
    }

    /**
    *
    */
    public List<OrderPackageBackup> select(Page<OrderPackageBackup> record){
        record.initFromNum();
        return orderPackageBackupDao.select(record);
    }

    /**
    *
    */
    public long count(Page<OrderPackageBackup> record){
        return orderPackageBackupDao.count(record);
    }

}