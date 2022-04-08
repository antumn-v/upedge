package com.upedge.oms.modules.order.vo;

import com.upedge.common.model.ship.vo.ShipDetail;
import lombok.Data;

import java.util.List;

@Data
public class OrderShipMethodVo {

    Integer warehouseType;

    List<ShipDetail> shipDetails;

    public OrderShipMethodVo() {
    }

    public OrderShipMethodVo(Integer warehouseType, List<ShipDetail> shipDetails) {
        this.warehouseType = warehouseType;
        this.shipDetails = shipDetails;
    }
}
