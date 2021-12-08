package com.upedge.oms.modules.order.vo;

import com.upedge.common.model.ship.vo.ShipDetail;
import lombok.Data;

@Data
public class OrderShipRuleDetail {

    Long orderId;

    Long shipRuleId;

    String shipRuleName;

    ShipDetail shipDetail;
}
