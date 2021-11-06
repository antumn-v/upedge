package com.upedge.oms.modules.order.vo;

import com.upedge.common.model.ship.vo.ShipDetail;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderShipRuleDetail {

    Long orderId;

    Long shipRuleId;

    String shipRuleName;

    BigDecimal vatAmount;

    ShipDetail shipDetail;
}
