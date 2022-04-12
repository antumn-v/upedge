package com.upedge.sms.modules.overseaWarehouse.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import com.upedge.common.base.Page;
import org.springframework.beans.factory.annotation.Autowired;
import com.upedge.sms.modules.overseaWarehouse.dao.OverseaWarehouseServiceOrderDao;
import com.upedge.sms.modules.overseaWarehouse.entity.OverseaWarehouseServiceOrder;
import com.upedge.sms.modules.overseaWarehouse.service.OverseaWarehouseServiceOrderService;


@Service
public class OverseaWarehouseServiceOrderServiceImpl implements OverseaWarehouseServiceOrderService {

    @Autowired
    private OverseaWarehouseServiceOrderDao overseaWarehouseServiceOrderDao;



    /**
     *
     */
    @Transactional
    public int deleteByPrimaryKey(Long id) {
        OverseaWarehouseServiceOrder record = new OverseaWarehouseServiceOrder();
        record.setId(id);
        return overseaWarehouseServiceOrderDao.deleteByPrimaryKey(record);
    }

    /**
     *
     */
    @Transactional
    public int insert(OverseaWarehouseServiceOrder record) {
        return overseaWarehouseServiceOrderDao.insert(record);
    }

    /**
     *
     */
    @Transactional
    public int insertSelective(OverseaWarehouseServiceOrder record) {
        return overseaWarehouseServiceOrderDao.insert(record);
    }

    /**
     *
     */
    public OverseaWarehouseServiceOrder selectByPrimaryKey(Long id){
        OverseaWarehouseServiceOrder record = new OverseaWarehouseServiceOrder();
        record.setId(id);
        return overseaWarehouseServiceOrderDao.selectByPrimaryKey(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKeySelective(OverseaWarehouseServiceOrder record) {
        return overseaWarehouseServiceOrderDao.updateByPrimaryKeySelective(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKey(OverseaWarehouseServiceOrder record) {
        return overseaWarehouseServiceOrderDao.updateByPrimaryKey(record);
    }

    /**
    *
    */
    public List<OverseaWarehouseServiceOrder> select(Page<OverseaWarehouseServiceOrder> record){
        record.initFromNum();
        return overseaWarehouseServiceOrderDao.select(record);
    }

    /**
    *
    */
    public long count(Page<OverseaWarehouseServiceOrder> record){
        return overseaWarehouseServiceOrderDao.count(record);
    }

}