package com.upedge.oms.modules.rules.service;

import com.upedge.common.base.Page;
import com.upedge.oms.modules.rules.dto.ShipRuleConditionDto;
import com.upedge.oms.modules.rules.entity.OrderShipRule;
import com.upedge.oms.modules.rules.request.OrderShipRuleAddRequest;
import com.upedge.oms.modules.rules.request.OrderShipRuleUpdateRequest;
import com.upedge.oms.modules.rules.vo.OrderShipRuleVo;

import java.util.List;

/**
 * @author author
 */
public interface OrderShipRuleService{

    OrderShipRuleVo selectShipRuleById(Long id);

    OrderShipRuleVo addShipRules(OrderShipRuleAddRequest request);

    void updateShipRules(OrderShipRuleUpdateRequest request, Long ruleId);

    List<OrderShipRule> selectShipRulesByCondition(ShipRuleConditionDto shipRuleConditionDto);

    List<OrderShipRuleVo> selectCustomerShipRules(Long customerId);

    OrderShipRule selectByPrimaryKey(Long id);

    int deleteByPrimaryKey(Long id);

    int updateByPrimaryKey(OrderShipRule record);

    int updateByPrimaryKeySelective(OrderShipRule record);

    int insert(OrderShipRule record);

    int insertSelective(OrderShipRule record);

    List<OrderShipRule> select(Page<OrderShipRule> record);

    long count(Page<OrderShipRule> record);
}

