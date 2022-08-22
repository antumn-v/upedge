package com.upedge.oms.modules.pack.service.impl;

import com.upedge.common.base.Page;
import com.upedge.oms.modules.pack.dao.OrderLabelPrintLogDao;
import com.upedge.oms.modules.pack.entity.OrderLabelPrintLog;
import com.upedge.oms.modules.pack.service.OrderLabelPrintLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class OrderLabelPrintLogServiceImpl implements OrderLabelPrintLogService {

    @Autowired
    private OrderLabelPrintLogDao orderLabelPrintLogDao;



    /**
     *
     */
    @Transactional
    public int deleteByPrimaryKey(Long id) {
        OrderLabelPrintLog record = new OrderLabelPrintLog();
        record.setId(id);
        return orderLabelPrintLogDao.deleteByPrimaryKey(record);
    }

    /**
     *
     */
    @Transactional
    public int insert(OrderLabelPrintLog record) {
        return orderLabelPrintLogDao.insert(record);
    }

    /**
     *
     */
    @Transactional
    public int insertSelective(OrderLabelPrintLog record) {
        return orderLabelPrintLogDao.insert(record);
    }

    @Override
    public List<OrderLabelPrintLog> selectByPackNo(Long packNo) {
        return orderLabelPrintLogDao.selectByPackNo(packNo);
    }

    /**
     *
     */
    public OrderLabelPrintLog selectByPrimaryKey(Long id){
        OrderLabelPrintLog record = new OrderLabelPrintLog();
        record.setId(id);
        return orderLabelPrintLogDao.selectByPrimaryKey(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKeySelective(OrderLabelPrintLog record) {
        return orderLabelPrintLogDao.updateByPrimaryKeySelective(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKey(OrderLabelPrintLog record) {
        return orderLabelPrintLogDao.updateByPrimaryKey(record);
    }

    /**
    *
    */
    public List<OrderLabelPrintLog> select(Page<OrderLabelPrintLog> record){
        record.initFromNum();
        return orderLabelPrintLogDao.select(record);
    }

    /**
    *
    */
    public long count(Page<OrderLabelPrintLog> record){
        return orderLabelPrintLogDao.count(record);
    }

}