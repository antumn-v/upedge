package com.upedge.tms.modules.warehouse.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import com.upedge.common.base.Page;
import org.springframework.beans.factory.annotation.Autowired;
import com.upedge.tms.modules.warehouse.dao.WarehouseDao;
import com.upedge.tms.modules.warehouse.entity.Warehouse;
import com.upedge.tms.modules.warehouse.service.WarehouseService;


@Service
public class WarehouseServiceImpl implements WarehouseService {

    @Autowired
    private WarehouseDao warehouseDao;



    /**
     *
     */
    @Transactional
    public int deleteByPrimaryKey(Long id) {
        Warehouse record = new Warehouse();
        record.setId(id);
        return warehouseDao.deleteByPrimaryKey(record);
    }

    /**
     *
     */
    @Transactional
    public int insert(Warehouse record) {
        return warehouseDao.insert(record);
    }

    /**
     *
     */
    @Transactional
    public int insertSelective(Warehouse record) {
        return warehouseDao.insert(record);
    }

    /**
     *
     */
    public Warehouse selectByPrimaryKey(Long id){
        Warehouse record = new Warehouse();
        record.setId(id);
        return warehouseDao.selectByPrimaryKey(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKeySelective(Warehouse record) {
        return warehouseDao.updateByPrimaryKeySelective(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKey(Warehouse record) {
        return warehouseDao.updateByPrimaryKey(record);
    }

    /**
    *
    */
    public List<Warehouse> select(Page<Warehouse> record){
        record.initFromNum();
        return warehouseDao.select(record);
    }

    /**
    *
    */
    public long count(Page<Warehouse> record){
        return warehouseDao.count(record);
    }

}