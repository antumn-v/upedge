package com.upedge.sms.modules.overseaWarehouse.dao;

import com.upedge.sms.modules.overseaWarehouse.entity.OverseaWarehouseSku;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import com.upedge.common.base.Page;

/**
 * @author gx
 */
public interface OverseaWarehouseSkuDao{

    OverseaWarehouseSku selectByPrimaryKey(OverseaWarehouseSku record);

    int deleteByPrimaryKey(OverseaWarehouseSku record);

    int updateByPrimaryKey(OverseaWarehouseSku record);

    int updateByPrimaryKeySelective(OverseaWarehouseSku record);

    int insert(OverseaWarehouseSku record);

    int insertSelective(OverseaWarehouseSku record);

    int insertByBatch(List<OverseaWarehouseSku> list);

    List<OverseaWarehouseSku> select(Page<OverseaWarehouseSku> record);

    long count(Page<OverseaWarehouseSku> record);

}
