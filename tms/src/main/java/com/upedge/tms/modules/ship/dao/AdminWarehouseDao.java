package com.upedge.tms.modules.ship.dao;

import com.upedge.common.base.Page;
import com.upedge.tms.modules.ship.entity.AdminWarehouse;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author author
 */
public interface AdminWarehouseDao{

    AdminWarehouse selectByPrimaryKey(Integer id);

    int deleteByPrimaryKey(AdminWarehouse record);

    int updateByPrimaryKey(AdminWarehouse record);

    int updateByPrimaryKeySelective(AdminWarehouse record);

    int insert(AdminWarehouse record);

    int insertSelective(AdminWarehouse record);

    int saveByBatch(List<AdminWarehouse> list);

    List<AdminWarehouse> select(Page<AdminWarehouse> record);

    long count(Page<AdminWarehouse> record);

    void updateWarehouseState(@Param("id") Integer id, @Param("state") Integer state);

    List<AdminWarehouse> allUseWarehouses();
}
