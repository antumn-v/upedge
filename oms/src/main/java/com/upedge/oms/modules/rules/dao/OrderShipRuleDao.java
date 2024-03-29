package com.upedge.oms.modules.rules.dao;

import com.upedge.common.base.Page;
import com.upedge.oms.modules.rules.dto.ShipRuleConditionDto;
import com.upedge.oms.modules.rules.entity.OrderShipRule;
import com.upedge.oms.modules.rules.vo.OrderShipRuleVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author author
 */
public interface OrderShipRuleDao {


    OrderShipRule selectCountryTopRule(@Param("customerId") Long customerId, @Param("areaId") Long areaId, @Param("methodId") Long methodId, @Param("seq") Integer seq);

    List<OrderShipRule> selectByCondition(ShipRuleConditionDto shipRuleConditionDto);

    List<OrderShipRuleVo> selectCustomerShipRules(Long customerId);

    OrderShipRuleVo selectShipRuleById(Long id);

    OrderShipRule selectByPrimaryKey(OrderShipRule record);

    int deleteByPrimaryKey(OrderShipRule record);

    int updateByPrimaryKey(OrderShipRule record);

    int updateByPrimaryKeySelective(OrderShipRule record);

    int insert(OrderShipRule record);

    int insertSelective(OrderShipRule record);

    int insertByBatch(List<OrderShipRule> list);

    List<OrderShipRule> select(Page<OrderShipRule> record);

    long count(Page<OrderShipRule> record);

}
