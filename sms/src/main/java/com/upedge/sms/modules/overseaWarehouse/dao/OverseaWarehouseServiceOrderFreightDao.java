package com.upedge.sms.modules.overseaWarehouse.dao;

import com.upedge.common.base.Page;
import com.upedge.sms.modules.overseaWarehouse.entity.OverseaWarehouseServiceOrderFreight;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author gx
 */
public interface OverseaWarehouseServiceOrderFreightDao {

    OverseaWarehouseServiceOrderFreight selectByOrderIdAndShipType(@Param("orderId") Long orderId, @Param("shipType") Integer shipType);

    List<OverseaWarehouseServiceOrderFreight> selectByOrderId(Long orderId);

    OverseaWarehouseServiceOrderFreight selectByPrimaryKey(OverseaWarehouseServiceOrderFreight record);

    int deleteByPrimaryKey(OverseaWarehouseServiceOrderFreight record);

    int updateByPrimaryKey(OverseaWarehouseServiceOrderFreight record);

    int updateByPrimaryKeySelective(OverseaWarehouseServiceOrderFreight record);

    int insert(OverseaWarehouseServiceOrderFreight record);

    int insertSelective(OverseaWarehouseServiceOrderFreight record);

    int insertByBatch(List<OverseaWarehouseServiceOrderFreight> list);

    List<OverseaWarehouseServiceOrderFreight> select(Page<OverseaWarehouseServiceOrderFreight> record);

    long count(Page<OverseaWarehouseServiceOrderFreight> record);

}
