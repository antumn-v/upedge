package com.upedge.sms.modules.overseaWarehouse.dao;

import com.upedge.common.base.Page;
import com.upedge.sms.modules.overseaWarehouse.entity.OverseaWarehouseServiceOrder;
import com.upedge.sms.modules.overseaWarehouse.vo.OverseaWarehouseServiceOrderVo;

import java.util.List;

/**
 * @author gx
 */
public interface OverseaWarehouseServiceOrderDao {

    List<OverseaWarehouseServiceOrderVo> selectAllUnPaidList();

    OverseaWarehouseServiceOrder selectByPrimaryKey(OverseaWarehouseServiceOrder record);

    int updateOrderAsPaid(OverseaWarehouseServiceOrder overseaWarehouseServiceOrder);

    int deleteByPrimaryKey(OverseaWarehouseServiceOrder record);

    int updateByPrimaryKey(OverseaWarehouseServiceOrder record);

    int updateByPrimaryKeySelective(OverseaWarehouseServiceOrder record);

    int insert(OverseaWarehouseServiceOrder record);

    int insertSelective(OverseaWarehouseServiceOrder record);

    int insertByBatch(List<OverseaWarehouseServiceOrder> list);

    List<OverseaWarehouseServiceOrder> select(Page<OverseaWarehouseServiceOrder> record);

    long count(Page<OverseaWarehouseServiceOrder> record);

}
