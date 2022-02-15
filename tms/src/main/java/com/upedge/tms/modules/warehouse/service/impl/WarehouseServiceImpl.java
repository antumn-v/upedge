package com.upedge.tms.modules.warehouse.service.impl;

import com.upedge.common.base.Page;
import com.upedge.common.constant.key.RedisKey;
import com.upedge.common.model.tms.WarehouseVo;
import com.upedge.common.utils.ListUtils;
import com.upedge.tms.modules.warehouse.dao.WarehouseDao;
import com.upedge.tms.modules.warehouse.entity.Warehouse;
import com.upedge.tms.modules.warehouse.service.WarehouseService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class WarehouseServiceImpl implements WarehouseService {

    @Autowired
    private WarehouseDao warehouseDao;

    @Autowired
    RedisTemplate redisTemplate;



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

    @Override
    public void redisInit() {
        Page<Warehouse> page = new Page<>();
        page.setPageSize(-1);
        List<Warehouse> warehouses = select(page);
        if (ListUtils.isNotEmpty(warehouses)){
            warehouses.forEach(warehouse -> {
                String key = RedisKey.STRING_WAREHOUSE + warehouse.getWarehouseCode();
                WarehouseVo warehouseVo = new WarehouseVo();
                BeanUtils.copyProperties(warehouse,warehouseVo);
                redisTemplate.opsForValue().set(key,warehouseVo);
            });
        }
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