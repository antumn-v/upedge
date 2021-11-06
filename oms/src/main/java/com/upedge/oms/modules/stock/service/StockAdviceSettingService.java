package com.upedge.oms.modules.stock.service;

import com.upedge.common.base.Page;
import com.upedge.oms.modules.stock.entity.StockAdviceSetting;

import java.util.List;

/**
 * @author author
 */
public interface StockAdviceSettingService{

    int updateByCustomerId(StockAdviceSetting setting);

    StockAdviceSetting selectByCustomerId(Long customerId);

    StockAdviceSetting selectByPrimaryKey(Integer id);

    int deleteByPrimaryKey(Integer id);

    int updateByPrimaryKey(StockAdviceSetting record);

    int updateByPrimaryKeySelective(StockAdviceSetting record);

    int insert(StockAdviceSetting record);

    int insertSelective(StockAdviceSetting record);

    List<StockAdviceSetting> select(Page<StockAdviceSetting> record);

    long count(Page<StockAdviceSetting> record);
}

