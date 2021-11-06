package com.upedge.ums.modules.account.dao;

import com.upedge.common.base.Page;
import com.upedge.ums.modules.account.entity.AdminCurrencyRate;

import java.util.List;

/**
 * @author author
 */
public interface AdminCurrencyRateDao{

    AdminCurrencyRate selectByPrimaryKey(Long id);

    int deleteByPrimaryKey(AdminCurrencyRate record);

    int updateByPrimaryKey(AdminCurrencyRate record);

    int updateByPrimaryKeySelective(AdminCurrencyRate record);

    int insert(AdminCurrencyRate record);

    int insertSelective(AdminCurrencyRate record);

    int insertByBatch(List<AdminCurrencyRate> list);

    List<AdminCurrencyRate> select(Page<AdminCurrencyRate> record);

    long count(Page<AdminCurrencyRate> record);

    AdminCurrencyRate queryEntityByCurrency(String currencyCode);
}
