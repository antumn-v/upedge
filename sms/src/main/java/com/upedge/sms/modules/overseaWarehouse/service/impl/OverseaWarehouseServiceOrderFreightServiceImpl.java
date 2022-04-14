package com.upedge.sms.modules.overseaWarehouse.service.impl;

import com.upedge.common.base.Page;
import com.upedge.common.utils.ListUtils;
import com.upedge.sms.modules.overseaWarehouse.dao.OverseaWarehouseServiceOrderFreightDao;
import com.upedge.sms.modules.overseaWarehouse.entity.OverseaWarehouseServiceOrderFreight;
import com.upedge.sms.modules.overseaWarehouse.service.OverseaWarehouseServiceOrderFreightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class OverseaWarehouseServiceOrderFreightServiceImpl implements OverseaWarehouseServiceOrderFreightService {

    @Autowired
    private OverseaWarehouseServiceOrderFreightDao overseaWarehouseServiceOrderFreightDao;



    /**
     *
     */
    @Transactional
    public int deleteByPrimaryKey(Long orderId) {
        OverseaWarehouseServiceOrderFreight record = new OverseaWarehouseServiceOrderFreight();
        record.setOrderId(orderId);
        return overseaWarehouseServiceOrderFreightDao.deleteByPrimaryKey(record);
    }

    /**
     *
     */
    @Transactional
    public int insert(OverseaWarehouseServiceOrderFreight record) {
        return overseaWarehouseServiceOrderFreightDao.insert(record);
    }

    @Override
    public int insertByBatch(List<OverseaWarehouseServiceOrderFreight> records) {
        if (ListUtils.isNotEmpty(records)){
            return overseaWarehouseServiceOrderFreightDao.insertByBatch(records);
        }
        return 0;
    }

    /**
     *
     */
    @Transactional
    public int insertSelective(OverseaWarehouseServiceOrderFreight record) {
        return overseaWarehouseServiceOrderFreightDao.insert(record);
    }

    /**
     *
     */
    public OverseaWarehouseServiceOrderFreight selectByPrimaryKey(Long orderId){
        OverseaWarehouseServiceOrderFreight record = new OverseaWarehouseServiceOrderFreight();
        record.setOrderId(orderId);
        return overseaWarehouseServiceOrderFreightDao.selectByPrimaryKey(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKeySelective(OverseaWarehouseServiceOrderFreight record) {
        return overseaWarehouseServiceOrderFreightDao.updateByPrimaryKeySelective(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKey(OverseaWarehouseServiceOrderFreight record) {
        return overseaWarehouseServiceOrderFreightDao.updateByPrimaryKey(record);
    }

    /**
    *
    */
    public List<OverseaWarehouseServiceOrderFreight> select(Page<OverseaWarehouseServiceOrderFreight> record){
        record.initFromNum();
        return overseaWarehouseServiceOrderFreightDao.select(record);
    }

    /**
    *
    */
    public long count(Page<OverseaWarehouseServiceOrderFreight> record){
        return overseaWarehouseServiceOrderFreightDao.count(record);
    }

}