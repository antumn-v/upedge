package com.upedge.sms.modules.overseaWarehouse.service;

import com.upedge.sms.modules.overseaWarehouse.entity.OverseaWarehouseServiceOrderItem;
import com.upedge.common.base.Page;
import java.util.List;

/**
 * @author gx
 */
public interface OverseaWarehouseServiceOrderItemService{

    OverseaWarehouseServiceOrderItem selectByPrimaryKey(Long id);

    int deleteByPrimaryKey(Long id);

    int updateByPrimaryKey(OverseaWarehouseServiceOrderItem record);

    int updateByPrimaryKeySelective(OverseaWarehouseServiceOrderItem record);

    int insert(OverseaWarehouseServiceOrderItem record);

    int insertByBatch(List<OverseaWarehouseServiceOrderItem> orderItems);

    int insertSelective(OverseaWarehouseServiceOrderItem record);

    List<OverseaWarehouseServiceOrderItem> select(Page<OverseaWarehouseServiceOrderItem> record);

    long count(Page<OverseaWarehouseServiceOrderItem> record);
}

