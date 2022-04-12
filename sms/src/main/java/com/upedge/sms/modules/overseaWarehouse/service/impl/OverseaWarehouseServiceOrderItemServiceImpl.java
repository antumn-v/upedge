package com.upedge.sms.modules.overseaWarehouse.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import com.upedge.common.base.Page;
import org.springframework.beans.factory.annotation.Autowired;
import com.upedge.sms.modules.overseaWarehouse.dao.OverseaWarehouseServiceOrderItemDao;
import com.upedge.sms.modules.overseaWarehouse.entity.OverseaWarehouseServiceOrderItem;
import com.upedge.sms.modules.overseaWarehouse.service.OverseaWarehouseServiceOrderItemService;


@Service
public class OverseaWarehouseServiceOrderItemServiceImpl implements OverseaWarehouseServiceOrderItemService {

    @Autowired
    private OverseaWarehouseServiceOrderItemDao overseaWarehouseServiceOrderItemDao;



    /**
     *
     */
    @Transactional
    public int deleteByPrimaryKey(Long id) {
        OverseaWarehouseServiceOrderItem record = new OverseaWarehouseServiceOrderItem();
        record.setId(id);
        return overseaWarehouseServiceOrderItemDao.deleteByPrimaryKey(record);
    }

    /**
     *
     */
    @Transactional
    public int insert(OverseaWarehouseServiceOrderItem record) {
        return overseaWarehouseServiceOrderItemDao.insert(record);
    }

    /**
     *
     */
    @Transactional
    public int insertSelective(OverseaWarehouseServiceOrderItem record) {
        return overseaWarehouseServiceOrderItemDao.insert(record);
    }

    /**
     *
     */
    public OverseaWarehouseServiceOrderItem selectByPrimaryKey(Long id){
        OverseaWarehouseServiceOrderItem record = new OverseaWarehouseServiceOrderItem();
        record.setId(id);
        return overseaWarehouseServiceOrderItemDao.selectByPrimaryKey(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKeySelective(OverseaWarehouseServiceOrderItem record) {
        return overseaWarehouseServiceOrderItemDao.updateByPrimaryKeySelective(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKey(OverseaWarehouseServiceOrderItem record) {
        return overseaWarehouseServiceOrderItemDao.updateByPrimaryKey(record);
    }

    /**
    *
    */
    public List<OverseaWarehouseServiceOrderItem> select(Page<OverseaWarehouseServiceOrderItem> record){
        record.initFromNum();
        return overseaWarehouseServiceOrderItemDao.select(record);
    }

    /**
    *
    */
    public long count(Page<OverseaWarehouseServiceOrderItem> record){
        return overseaWarehouseServiceOrderItemDao.count(record);
    }

}