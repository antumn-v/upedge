package com.upedge.tms.modules.ship.request;

import lombok.Data;

import java.util.List;

@Data
public class ShipUnitDelRequest {

    Long methodId;

    List<Long> unitIds;
}
