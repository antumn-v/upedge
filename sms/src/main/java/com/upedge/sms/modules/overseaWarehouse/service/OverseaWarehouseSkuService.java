package com.upedge.sms.modules.overseaWarehouse.service;

import com.upedge.sms.modules.overseaWarehouse.entity.OverseaWarehouseSku;
import com.upedge.common.base.Page;
import java.util.List;

/**
 * @author gx
 */
public interface OverseaWarehouseSkuService{

    OverseaWarehouseSku selectByPrimaryKey(Long variantId);

    int deleteByPrimaryKey(Long variantId);

    int updateByPrimaryKey(OverseaWarehouseSku record);

    int updateByPrimaryKeySelective(OverseaWarehouseSku record);

    int insert(OverseaWarehouseSku record);

    int insertSelective(OverseaWarehouseSku record);

    List<OverseaWarehouseSku> select(Page<OverseaWarehouseSku> record);

    long count(Page<OverseaWarehouseSku> record);
}

