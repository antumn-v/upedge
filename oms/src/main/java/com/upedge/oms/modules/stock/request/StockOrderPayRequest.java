package com.upedge.oms.modules.stock.request;

import lombok.Data;

import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.List;

@Data
public class StockOrderPayRequest {
    @Size(min = 1)
    List<Long> orderIds;

    BigDecimal payAmount;
}
