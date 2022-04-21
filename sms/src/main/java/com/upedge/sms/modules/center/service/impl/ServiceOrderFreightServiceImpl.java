package com.upedge.sms.modules.center.service.impl;

import com.upedge.common.base.Page;
import com.upedge.common.utils.ListUtils;
import com.upedge.sms.modules.center.dao.ServiceOrderFreightDao;
import com.upedge.sms.modules.center.entity.ServiceOrderFreight;
import com.upedge.sms.modules.center.service.ServiceOrderFreightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class ServiceOrderFreightServiceImpl implements ServiceOrderFreightService {

    @Autowired
    private ServiceOrderFreightDao ServiceOrderFreightDao;



    /**
     *
     */
    @Transactional
    public int deleteByPrimaryKey(Long orderId) {
        ServiceOrderFreight record = new ServiceOrderFreight();
        record.setOrderId(orderId);
        return ServiceOrderFreightDao.deleteByPrimaryKey(record);
    }

    /**
     *
     */
    @Transactional
    public int insert(ServiceOrderFreight record) {
        return ServiceOrderFreightDao.insert(record);
    }

    @Override
    public int insertByBatch(List<ServiceOrderFreight> records) {
        if (ListUtils.isNotEmpty(records)){
            return ServiceOrderFreightDao.insertByBatch(records);
        }
        return 0;
    }

    /**
     *
     */
    @Transactional
    public int insertSelective(ServiceOrderFreight record) {
        return ServiceOrderFreightDao.insert(record);
    }

    @Override
    public ServiceOrderFreight selectByOrderIdAndShipType(Long orderId, Integer shipType) {
        return ServiceOrderFreightDao.selectByOrderIdAndShipType(orderId, shipType);
    }

    @Override
    public List<ServiceOrderFreight> selectByOrderId(Long orderId) {
        return ServiceOrderFreightDao.selectByOrderId(orderId);
    }

    /**
     *
     */
    public ServiceOrderFreight selectByPrimaryKey(Long orderId){
        ServiceOrderFreight record = new ServiceOrderFreight();
        record.setOrderId(orderId);
        return ServiceOrderFreightDao.selectByPrimaryKey(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKeySelective(ServiceOrderFreight record) {
        return ServiceOrderFreightDao.updateByPrimaryKeySelective(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKey(ServiceOrderFreight record) {
        return ServiceOrderFreightDao.updateByPrimaryKey(record);
    }

    /**
    *
    */
    public List<ServiceOrderFreight> select(Page<ServiceOrderFreight> record){
        record.initFromNum();
        return ServiceOrderFreightDao.select(record);
    }

    /**
    *
    */
    public long count(Page<ServiceOrderFreight> record){
        return ServiceOrderFreightDao.count(record);
    }

}