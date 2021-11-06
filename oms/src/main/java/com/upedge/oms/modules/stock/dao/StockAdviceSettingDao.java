package com.upedge.oms.modules.stock.dao;

import com.upedge.common.base.Page;
import com.upedge.oms.modules.stock.entity.StockAdviceSetting;

import java.util.List;

/**
 * @author author
 */
public interface StockAdviceSettingDao{

    int updateByCustomerId(StockAdviceSetting setting);

    StockAdviceSetting selectByCustomerId(Long customerId);

    StockAdviceSetting selectByPrimaryKey(StockAdviceSetting record);

    int deleteByPrimaryKey(StockAdviceSetting record);

    int updateByPrimaryKey(StockAdviceSetting record);

    int updateByPrimaryKeySelective(StockAdviceSetting record);

    int insert(StockAdviceSetting record);

    int insertSelective(StockAdviceSetting record);

    int insertByBatch(List<StockAdviceSetting> list);

    List<StockAdviceSetting> select(Page<StockAdviceSetting> record);

    long count(Page<StockAdviceSetting> record);

}
