package com.upedge.oms.modules.wholesale.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class WholesaleOrderShipUpdateRequest {

    Long id;

    BigDecimal shipPrice;

    String shipMethodName;

    BigDecimal serviceFee;

}
