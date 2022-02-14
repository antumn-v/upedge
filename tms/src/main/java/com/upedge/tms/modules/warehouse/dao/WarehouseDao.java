package com.upedge.tms.modules.warehouse.dao;

import com.upedge.tms.modules.warehouse.entity.Warehouse;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import com.upedge.common.base.Page;

/**
 * @author gx
 */
public interface WarehouseDao{

    Warehouse selectByPrimaryKey(Warehouse record);

    int deleteByPrimaryKey(Warehouse record);

    int updateByPrimaryKey(Warehouse record);

    int updateByPrimaryKeySelective(Warehouse record);

    int insert(Warehouse record);

    int insertSelective(Warehouse record);

    int insertByBatch(List<Warehouse> list);

    List<Warehouse> select(Page<Warehouse> record);

    long count(Page<Warehouse> record);

}
