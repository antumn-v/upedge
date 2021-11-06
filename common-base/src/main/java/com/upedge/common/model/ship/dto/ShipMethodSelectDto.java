package com.upedge.common.model.ship.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Data
public class ShipMethodSelectDto {

    List<Long> templateIds = new ArrayList<>();

    Long toAreaId;

    BigDecimal weight;

    BigDecimal volumeWeight;

    Set<Long> methodIds;

}
