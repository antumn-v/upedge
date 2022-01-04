package com.upedge.tms.modules.ship.vo;

import lombok.Data;

import java.util.List;
@Data
public class ShipMethodCountryVo {


    Long methodId;

    String methodName;

    List<CountryShipUnitVo> countryShipUnitVos;
}
