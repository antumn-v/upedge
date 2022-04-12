package com.upedge.sms.modules.overseaWarehouse.dao;

import com.upedge.sms.modules.overseaWarehouse.entity.OverseaWarehouseServiceOrderItem;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import com.upedge.common.base.Page;

/**
 * @author gx
 */
public interface OverseaWarehouseServiceOrderItemDao{

    OverseaWarehouseServiceOrderItem selectByPrimaryKey(OverseaWarehouseServiceOrderItem record);

    int deleteByPrimaryKey(OverseaWarehouseServiceOrderItem record);

    int updateByPrimaryKey(OverseaWarehouseServiceOrderItem record);

    int updateByPrimaryKeySelective(OverseaWarehouseServiceOrderItem record);

    int insert(OverseaWarehouseServiceOrderItem record);

    int insertSelective(OverseaWarehouseServiceOrderItem record);

    int insertByBatch(List<OverseaWarehouseServiceOrderItem> list);

    List<OverseaWarehouseServiceOrderItem> select(Page<OverseaWarehouseServiceOrderItem> record);

    long count(Page<OverseaWarehouseServiceOrderItem> record);

}
