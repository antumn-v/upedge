package com.upedge.oms.modules.pack.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import com.upedge.common.base.Page;
import org.springframework.beans.factory.annotation.Autowired;
import com.upedge.oms.modules.pack.dao.OrderPackageImportLogDao;
import com.upedge.oms.modules.pack.entity.OrderPackageImportLog;
import com.upedge.oms.modules.pack.service.OrderPackageImportLogService;


@Service
public class OrderPackageImportLogServiceImpl implements OrderPackageImportLogService {

    @Autowired
    private OrderPackageImportLogDao orderPackageImportLogDao;



    /**
     *
     */
    @Transactional
    public int deleteByPrimaryKey(Integer id) {
        OrderPackageImportLog record = new OrderPackageImportLog();
        record.setId(id);
        return orderPackageImportLogDao.deleteByPrimaryKey(record);
    }

    /**
     *
     */
    @Transactional
    public int insert(OrderPackageImportLog record) {
        return orderPackageImportLogDao.insert(record);
    }

    /**
     *
     */
    @Transactional
    public int insertSelective(OrderPackageImportLog record) {
        return orderPackageImportLogDao.insert(record);
    }

    @Override
    public OrderPackageImportLog selectByOrderInfo(String storeName, String platOrderName, String trackingCode) {
        return orderPackageImportLogDao.selectByOrderInfo(storeName, platOrderName, trackingCode);
    }

    /**
     *
     */
    public OrderPackageImportLog selectByPrimaryKey(Integer id){
        OrderPackageImportLog record = new OrderPackageImportLog();
        record.setId(id);
        return orderPackageImportLogDao.selectByPrimaryKey(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKeySelective(OrderPackageImportLog record) {
        return orderPackageImportLogDao.updateByPrimaryKeySelective(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKey(OrderPackageImportLog record) {
        return orderPackageImportLogDao.updateByPrimaryKey(record);
    }

    /**
    *
    */
    public List<OrderPackageImportLog> select(Page<OrderPackageImportLog> record){
        record.initFromNum();
        return orderPackageImportLogDao.select(record);
    }

    /**
    *
    */
    public long count(Page<OrderPackageImportLog> record){
        return orderPackageImportLogDao.count(record);
    }

}