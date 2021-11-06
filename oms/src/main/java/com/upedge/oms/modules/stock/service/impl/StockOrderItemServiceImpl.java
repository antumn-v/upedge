package com.upedge.oms.modules.stock.service.impl;

import com.upedge.common.base.Page;
import com.upedge.oms.modules.stock.dao.StockOrderItemDao;
import com.upedge.oms.modules.stock.entity.StockOrderItem;
import com.upedge.oms.modules.stock.service.StockOrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class StockOrderItemServiceImpl implements StockOrderItemService {

    @Autowired
    private StockOrderItemDao stockOrderItemDao;



    /**
     *
     */
    @Transactional
    public int deleteByPrimaryKey(Long id) {
        StockOrderItem record = new StockOrderItem();
        record.setId(id);
        return stockOrderItemDao.deleteByPrimaryKey(record);
    }

    /**
     *
     */
    @Transactional
    public int insert(StockOrderItem record) {
        return stockOrderItemDao.insert(record);
    }

    /**
     *
     */
    @Transactional
    public int insertSelective(StockOrderItem record) {
        return stockOrderItemDao.insert(record);
    }

    /**
     *
     */
    public StockOrderItem selectByPrimaryKey(Long id){
        StockOrderItem record = new StockOrderItem();
        record.setId(id);
        return stockOrderItemDao.selectByPrimaryKey(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKeySelective(StockOrderItem record) {
        return stockOrderItemDao.updateByPrimaryKeySelective(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKey(StockOrderItem record) {
        return stockOrderItemDao.updateByPrimaryKey(record);
    }

    /**
    *
    */
    public List<StockOrderItem> select(Page<StockOrderItem> record){
        record.initFromNum();
        return stockOrderItemDao.select(record);
    }

    /**
    *
    */
    public long count(Page<StockOrderItem> record){
        return stockOrderItemDao.count(record);
    }

}