package com.upedge.ums.modules.account.service;

import com.upedge.ums.modules.account.entity.Currency;

import java.math.BigDecimal;

public interface CurrencyService {

    Currency refreshCurrencyRate(String code);

    BigDecimal refreshUsdRate(String code);

    BigDecimal selectCnyRateByCode(String code);
}
