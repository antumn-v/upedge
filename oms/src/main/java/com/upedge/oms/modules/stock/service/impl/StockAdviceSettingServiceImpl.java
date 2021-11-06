package com.upedge.oms.modules.stock.service.impl;

import com.upedge.common.base.Page;
import com.upedge.common.model.product.*;
import com.upedge.oms.modules.stock.dao.StockAdviceSettingDao;
import com.upedge.oms.modules.stock.entity.StockAdviceSetting;
import com.upedge.oms.modules.stock.service.StockAdviceSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class StockAdviceSettingServiceImpl implements StockAdviceSettingService {

    @Autowired
    private StockAdviceSettingDao stockAdviceSettingDao;

    /**
     *
     */
    @Transactional
    public int deleteByPrimaryKey(Integer id) {
        StockAdviceSetting record = new StockAdviceSetting();
        record.setId(id);
        return stockAdviceSettingDao.deleteByPrimaryKey(record);
    }

    /**
     *
     */
    @Transactional
    public int insert(StockAdviceSetting record) {
        return stockAdviceSettingDao.insert(record);
    }

    /**
     *
     */
    @Transactional
    public int insertSelective(StockAdviceSetting record) {
        return stockAdviceSettingDao.insert(record);
    }

    @Override
    public int updateByCustomerId(StockAdviceSetting setting) {
        return stockAdviceSettingDao.updateByCustomerId(setting);
    }

    @Override
    public StockAdviceSetting selectByCustomerId(Long customerId) {
        return stockAdviceSettingDao.selectByCustomerId(customerId);
    }

    /**
     *
     */
    public StockAdviceSetting selectByPrimaryKey(Integer id){
        StockAdviceSetting record = new StockAdviceSetting();
        record.setId(id);
        return stockAdviceSettingDao.selectByPrimaryKey(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKeySelective(StockAdviceSetting record) {
        return stockAdviceSettingDao.updateByPrimaryKeySelective(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKey(StockAdviceSetting record) {
        return stockAdviceSettingDao.updateByPrimaryKey(record);
    }

    /**
    *
    */
    public List<StockAdviceSetting> select(Page<StockAdviceSetting> record){
        record.initFromNum();
        return stockAdviceSettingDao.select(record);
    }

    /**
    *
    */
    public long count(Page<StockAdviceSetting> record){
        return stockAdviceSettingDao.count(record);
    }

}