package com.upedge.oms.modules.stock.dao;

import com.upedge.common.base.Page;
import com.upedge.oms.modules.stock.entity.AdminSaiheInventory;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author author
 */
public interface AdminSaiheInventoryDao{

    AdminSaiheInventory selectByPrimaryKey(AdminSaiheInventory record);

    int deleteByPrimaryKey(AdminSaiheInventory record);

    int updateByPrimaryKey(AdminSaiheInventory record);

    int updateByPrimaryKeySelective(AdminSaiheInventory record);

    int insert(AdminSaiheInventory record);

    int insertSelective(AdminSaiheInventory record);

    int insertByBatch(List<AdminSaiheInventory> list);

    List<AdminSaiheInventory> select(Page<AdminSaiheInventory> record);

    long count(Page<AdminSaiheInventory> record);

    AdminSaiheInventory queryAdminSaiheInventory(
            @Param("variantSku") String variantSku, @Param("warehouseCode") int warehouseCode);
}
