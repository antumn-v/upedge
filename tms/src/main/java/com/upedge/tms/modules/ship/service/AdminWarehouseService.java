package com.upedge.tms.modules.ship.service;

import com.upedge.common.base.Page;
import com.upedge.tms.modules.ship.entity.AdminWarehouse;
import com.upedge.tms.modules.ship.response.AdminWarehouseListResponse;
import com.upedge.tms.modules.ship.response.AdminWarehouseUpdateResponse;

import java.util.List;

/**
 * @author author
 */
public interface AdminWarehouseService{

    AdminWarehouse selectByPrimaryKey(Integer id);

    int deleteByPrimaryKey(Integer id);

    int updateByPrimaryKey(AdminWarehouse record);

    int updateByPrimaryKeySelective(AdminWarehouse record);

    int insert(AdminWarehouse record);

    int insertSelective(AdminWarehouse record);

    List<AdminWarehouse> select(Page<AdminWarehouse> record);

    long count(Page<AdminWarehouse> record);

    AdminWarehouseUpdateResponse enableWarehouse(Integer id);

    AdminWarehouseUpdateResponse disableWarehouse(Integer id);

    AdminWarehouseListResponse allUseWarehouses();

    AdminWarehouseUpdateResponse refreshSaihe();
}

