package com.upedge.tms.modules.warehouse.service;

import com.upedge.tms.modules.warehouse.entity.CountryAvailableWarehouse;
import com.upedge.common.base.Page;
import java.util.List;

/**
 * @author gx
 */
public interface CountryAvailableWarehouseService{

    void redisInit();

    CountryAvailableWarehouse selectByPrimaryKey(String warehouseCode);

    int deleteByPrimaryKey(String warehouseCode);

    int updateByPrimaryKey(CountryAvailableWarehouse record);

    int updateByPrimaryKeySelective(CountryAvailableWarehouse record);

    int insert(CountryAvailableWarehouse record);

    int insertSelective(CountryAvailableWarehouse record);

    List<CountryAvailableWarehouse> select(Page<CountryAvailableWarehouse> record);

    long count(Page<CountryAvailableWarehouse> record);
}

