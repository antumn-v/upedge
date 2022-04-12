package com.upedge.sms.modules.overseaWarehouse.service;

import com.upedge.sms.modules.overseaWarehouse.entity.OverseaWarehouseServiceOrder;
import com.upedge.common.base.Page;
import java.util.List;

/**
 * @author gx
 */
public interface OverseaWarehouseServiceOrderService{

    OverseaWarehouseServiceOrder selectByPrimaryKey(Long id);

    int deleteByPrimaryKey(Long id);

    int updateByPrimaryKey(OverseaWarehouseServiceOrder record);

    int updateByPrimaryKeySelective(OverseaWarehouseServiceOrder record);

    int insert(OverseaWarehouseServiceOrder record);

    int insertSelective(OverseaWarehouseServiceOrder record);

    List<OverseaWarehouseServiceOrder> select(Page<OverseaWarehouseServiceOrder> record);

    long count(Page<OverseaWarehouseServiceOrder> record);
}

