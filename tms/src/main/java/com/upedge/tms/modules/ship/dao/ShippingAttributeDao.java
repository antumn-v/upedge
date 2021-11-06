package com.upedge.tms.modules.ship.dao;

import com.upedge.common.base.Page;
import com.upedge.tms.modules.ship.entity.ShippingAttribute;

import java.util.List;

/**
 * @author author
 */
public interface ShippingAttributeDao{

    ShippingAttribute selectByPrimaryKey(ShippingAttribute record);

    int deleteByPrimaryKey(ShippingAttribute record);

    int updateByPrimaryKey(ShippingAttribute record);

    int updateByPrimaryKeySelective(ShippingAttribute record);

    int insert(ShippingAttribute record);

    int insertSelective(ShippingAttribute record);

    int insertByBatch(List<ShippingAttribute> list);

    List<ShippingAttribute> select(Page<ShippingAttribute> record);

    long count(Page<ShippingAttribute> record);

    List<ShippingAttribute> allShippingAttribute();
}
