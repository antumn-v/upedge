package com.upedge.tms.modules.warehouse.service;

import com.upedge.tms.modules.warehouse.entity.Warehouse;
import com.upedge.common.base.Page;
import java.util.List;

/**
 * @author gx
 */
public interface WarehouseService{

    Warehouse selectByPrimaryKey(Long id);

    int deleteByPrimaryKey(Long id);

    int updateByPrimaryKey(Warehouse record);

    int updateByPrimaryKeySelective(Warehouse record);

    int insert(Warehouse record);

    int insertSelective(Warehouse record);

    List<Warehouse> select(Page<Warehouse> record);

    long count(Page<Warehouse> record);
}

