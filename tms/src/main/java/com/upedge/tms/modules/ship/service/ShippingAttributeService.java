package com.upedge.tms.modules.ship.service;

import com.upedge.common.base.Page;
import com.upedge.tms.modules.ship.entity.ShippingAttribute;
import com.upedge.tms.modules.ship.response.ShippingAttributeListResponse;

import java.util.List;

/**
 * @author author
 */
public interface ShippingAttributeService{

    ShippingAttribute selectByPrimaryKey(Long id);

    int deleteByPrimaryKey(Long id);

    int updateByPrimaryKey(ShippingAttribute record);

    int updateByPrimaryKeySelective(ShippingAttribute record);

    int insert(ShippingAttribute record);

    int insertSelective(ShippingAttribute record);

    List<ShippingAttribute> select(Page<ShippingAttribute> record);

    long count(Page<ShippingAttribute> record);

    ShippingAttributeListResponse allShippingAttribute();
}

