package com.upedge.oms.modules.stock.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class StockOrderPayRequest {
    Long id;

    BigDecimal payAmount;
}
