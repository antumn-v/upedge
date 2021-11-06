package com.upedge.oms.modules.statistics.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class StockOrderDailyCountUpdateDto {

    Long customerId;

    BigDecimal productAmount;

    BigDecimal fixFee;

    BigDecimal payAmount;

    BigDecimal creditAmount;

    Date payTime;
}
