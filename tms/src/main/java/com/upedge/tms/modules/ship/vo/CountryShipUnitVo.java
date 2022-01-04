package com.upedge.tms.modules.ship.vo;

import com.upedge.tms.modules.ship.entity.ShippingUnit;
import lombok.Data;

import java.util.List;

@Data
public class CountryShipUnitVo {

    String countryName;

    Long areaId;

    List<ShippingUnit> shippingUnits;
}
