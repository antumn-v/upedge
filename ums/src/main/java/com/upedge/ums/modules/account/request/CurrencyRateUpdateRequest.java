package com.upedge.ums.modules.account.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CurrencyRateUpdateRequest {

    BigDecimal rate;
}
