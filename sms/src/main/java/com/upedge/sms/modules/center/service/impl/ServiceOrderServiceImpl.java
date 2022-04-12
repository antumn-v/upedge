package com.upedge.sms.modules.center.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import com.upedge.common.base.Page;
import org.springframework.beans.factory.annotation.Autowired;
import com.upedge.sms.modules.center.dao.ServiceOrderDao;
import com.upedge.sms.modules.center.entity.ServiceOrder;
import com.upedge.sms.modules.center.service.ServiceOrderService;


@Service
public class ServiceOrderServiceImpl implements ServiceOrderService {

    @Autowired
    private ServiceOrderDao serviceOrderDao;



    /**
     *
     */
    @Transactional
    public int deleteByPrimaryKey(Long id) {
        ServiceOrder record = new ServiceOrder();
        record.setId(id);
        return serviceOrderDao.deleteByPrimaryKey(record);
    }

    /**
     *
     */
    @Transactional
    public int insert(ServiceOrder record) {
        return serviceOrderDao.insert(record);
    }

    /**
     *
     */
    @Transactional
    public int insertSelective(ServiceOrder record) {
        return serviceOrderDao.insert(record);
    }

    /**
     *
     */
    public ServiceOrder selectByPrimaryKey(Long id){
        ServiceOrder record = new ServiceOrder();
        record.setId(id);
        return serviceOrderDao.selectByPrimaryKey(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKeySelective(ServiceOrder record) {
        return serviceOrderDao.updateByPrimaryKeySelective(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKey(ServiceOrder record) {
        return serviceOrderDao.updateByPrimaryKey(record);
    }

    /**
    *
    */
    public List<ServiceOrder> select(Page<ServiceOrder> record){
        record.initFromNum();
        return serviceOrderDao.select(record);
    }

    /**
    *
    */
    public long count(Page<ServiceOrder> record){
        return serviceOrderDao.count(record);
    }

}