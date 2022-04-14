package com.upedge.sms.modules.overseaWarehouse.service;

import com.upedge.sms.modules.overseaWarehouse.entity.OverseaWarehouseServiceOrderFreight;
import com.upedge.common.base.Page;
import java.util.List;

/**
 * @author gx
 */
public interface OverseaWarehouseServiceOrderFreightService{

    OverseaWarehouseServiceOrderFreight selectByPrimaryKey(Long orderId);

    int deleteByPrimaryKey(Long orderId);

    int updateByPrimaryKey(OverseaWarehouseServiceOrderFreight record);

    int updateByPrimaryKeySelective(OverseaWarehouseServiceOrderFreight record);

    int insert(OverseaWarehouseServiceOrderFreight record);

    int insertByBatch(List<OverseaWarehouseServiceOrderFreight> records);

    int insertSelective(OverseaWarehouseServiceOrderFreight record);

    List<OverseaWarehouseServiceOrderFreight> select(Page<OverseaWarehouseServiceOrderFreight> record);

    long count(Page<OverseaWarehouseServiceOrderFreight> record);
}

