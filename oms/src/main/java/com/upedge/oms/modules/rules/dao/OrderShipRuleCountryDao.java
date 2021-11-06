package com.upedge.oms.modules.rules.dao;

import com.upedge.common.base.Page;
import com.upedge.oms.modules.rules.entity.OrderShipRuleCountry;

import java.util.List;

/**
 * @author author
 */
public interface OrderShipRuleCountryDao{

    void deleteByShipRuleId(Long shipRuleId);

    OrderShipRuleCountry selectByPrimaryKey(OrderShipRuleCountry record);

    int deleteByPrimaryKey(OrderShipRuleCountry record);

    int updateByPrimaryKey(OrderShipRuleCountry record);

    int updateByPrimaryKeySelective(OrderShipRuleCountry record);

    int insert(OrderShipRuleCountry record);

    int insertSelective(OrderShipRuleCountry record);

    int insertByBatch(List<OrderShipRuleCountry> list);

    List<OrderShipRuleCountry> select(Page<OrderShipRuleCountry> record);

    long count(Page<OrderShipRuleCountry> record);

}
