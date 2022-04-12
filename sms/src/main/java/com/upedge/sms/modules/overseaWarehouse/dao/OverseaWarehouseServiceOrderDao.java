package com.upedge.sms.modules.overseaWarehouse.dao;

import com.upedge.sms.modules.overseaWarehouse.entity.OverseaWarehouseServiceOrder;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import com.upedge.common.base.Page;

/**
 * @author gx
 */
public interface OverseaWarehouseServiceOrderDao{

    OverseaWarehouseServiceOrder selectByPrimaryKey(OverseaWarehouseServiceOrder record);

    int deleteByPrimaryKey(OverseaWarehouseServiceOrder record);

    int updateByPrimaryKey(OverseaWarehouseServiceOrder record);

    int updateByPrimaryKeySelective(OverseaWarehouseServiceOrder record);

    int insert(OverseaWarehouseServiceOrder record);

    int insertSelective(OverseaWarehouseServiceOrder record);

    int insertByBatch(List<OverseaWarehouseServiceOrder> list);

    List<OverseaWarehouseServiceOrder> select(Page<OverseaWarehouseServiceOrder> record);

    long count(Page<OverseaWarehouseServiceOrder> record);

}
