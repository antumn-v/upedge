package com.upedge.tms.modules.warehouse.dao;

import com.upedge.tms.modules.warehouse.entity.CountryAvailableWarehouse;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import com.upedge.common.base.Page;

/**
 * @author gx
 */
public interface CountryAvailableWarehouseDao{

    CountryAvailableWarehouse selectByPrimaryKey(CountryAvailableWarehouse record);

    int deleteByPrimaryKey(CountryAvailableWarehouse record);

    int updateByPrimaryKey(CountryAvailableWarehouse record);

    int updateByPrimaryKeySelective(CountryAvailableWarehouse record);

    int insert(CountryAvailableWarehouse record);

    int insertSelective(CountryAvailableWarehouse record);

    int insertByBatch(List<CountryAvailableWarehouse> list);

    List<CountryAvailableWarehouse> select(Page<CountryAvailableWarehouse> record);

    long count(Page<CountryAvailableWarehouse> record);

}
