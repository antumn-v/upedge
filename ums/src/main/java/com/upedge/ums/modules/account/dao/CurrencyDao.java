package com.upedge.ums.modules.account.dao;

import com.upedge.common.base.Page;
import com.upedge.ums.modules.account.entity.Currency;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author author
 */
public interface CurrencyDao{

    Currency selectByCurrencyCode(String code);

    BigDecimal selectCnyRateByCode(String code);

    Currency selectByPrimaryKey(Currency record);

    int deleteByPrimaryKey(Currency record);

    int updateByPrimaryKey(Currency record);

    int updateByPrimaryKeySelective(Currency record);

    int updateByCurrencyCodeSelective(Currency record);

    int insert(Currency record);

    int insertSelective(Currency record);

    int insertByBatch(List<Currency> list);

    List<Currency> select(Page<Currency> record);

    long count(Page<Currency> record);

}
