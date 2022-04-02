package com.upedge.tms.modules.warehouse.service.impl;

import com.upedge.common.base.Page;
import com.upedge.common.constant.key.RedisKey;
import com.upedge.common.utils.ListUtils;
import com.upedge.tms.modules.warehouse.dao.CountryAvailableWarehouseDao;
import com.upedge.tms.modules.warehouse.entity.CountryAvailableWarehouse;
import com.upedge.tms.modules.warehouse.service.CountryAvailableWarehouseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class CountryAvailableWarehouseServiceImpl implements CountryAvailableWarehouseService {

    @Autowired
    private CountryAvailableWarehouseDao countryAvailableWarehouseDao;

    @Autowired
    RedisTemplate redisTemplate;



    /**
     *
     */
    @Transactional
    public int deleteByPrimaryKey(String warehouseCode) {
        CountryAvailableWarehouse record = new CountryAvailableWarehouse();
        record.setWarehouseCode(warehouseCode);
        return countryAvailableWarehouseDao.deleteByPrimaryKey(record);
    }

    /**
     *
     */
    @Transactional
    public int insert(CountryAvailableWarehouse record) {
        if (record.getAreaId() == null
        || null == record.getWarehouseCode()){
            return 0;
        }
        redisTemplate.opsForHash().put(RedisKey.HASH_COUNTRY_AVAILABLE_OVERSEA_WAREHOUSE,String.valueOf(record.getAreaId()),record.getWarehouseCode());
        return countryAvailableWarehouseDao.insert(record);
    }

    /**
     *
     */
    @Transactional
    public int insertSelective(CountryAvailableWarehouse record) {
        return countryAvailableWarehouseDao.insert(record);
    }

    @Override
    public void redisInit() {
        Page<CountryAvailableWarehouse> page = new Page<>();
        page.setPageSize(-1);
        page.initFromNum();
        List<CountryAvailableWarehouse> countryAvailableWarehouses = select(page);
        if (ListUtils.isNotEmpty(countryAvailableWarehouses)){
            Map<String,String> map = new HashMap<>();
            for (CountryAvailableWarehouse countryAvailableWarehouse : countryAvailableWarehouses) {
                map.put(countryAvailableWarehouse.getAreaId().toString(),countryAvailableWarehouse.getWarehouseCode());
            }
            redisTemplate.delete(RedisKey.HASH_COUNTRY_AVAILABLE_OVERSEA_WAREHOUSE);
            redisTemplate.opsForHash().putAll(RedisKey.HASH_COUNTRY_AVAILABLE_OVERSEA_WAREHOUSE,map);
        }else {
            redisTemplate.delete(RedisKey.HASH_COUNTRY_AVAILABLE_OVERSEA_WAREHOUSE);
        }
        log.warn("国家可用海外仓初始化完成............................");
    }

    /**
     *
     */
    public CountryAvailableWarehouse selectByPrimaryKey(String warehouseCode){
        CountryAvailableWarehouse record = new CountryAvailableWarehouse();
        record.setWarehouseCode(warehouseCode);
        return countryAvailableWarehouseDao.selectByPrimaryKey(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKeySelective(CountryAvailableWarehouse record) {
        return countryAvailableWarehouseDao.updateByPrimaryKeySelective(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKey(CountryAvailableWarehouse record) {
        return countryAvailableWarehouseDao.updateByPrimaryKey(record);
    }

    /**
    *
    */
    public List<CountryAvailableWarehouse> select(Page<CountryAvailableWarehouse> record){
        record.initFromNum();
        return countryAvailableWarehouseDao.select(record);
    }

    /**
    *
    */
    public long count(Page<CountryAvailableWarehouse> record){
        return countryAvailableWarehouseDao.count(record);
    }

}