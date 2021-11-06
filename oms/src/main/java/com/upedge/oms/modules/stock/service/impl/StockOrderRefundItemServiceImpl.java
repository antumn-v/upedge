package com.upedge.oms.modules.stock.service.impl;

import com.upedge.common.base.Page;
import com.upedge.oms.modules.stock.dao.StockOrderRefundItemDao;
import com.upedge.oms.modules.stock.entity.StockOrderRefundItem;
import com.upedge.oms.modules.stock.service.StockOrderRefundItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class StockOrderRefundItemServiceImpl implements StockOrderRefundItemService {

    @Autowired
    private StockOrderRefundItemDao stockOrderRefundItemDao;



    /**
     *
     */
    @Transactional
    public int deleteByPrimaryKey(Long id) {
        StockOrderRefundItem record = new StockOrderRefundItem();
        record.setId(id);
        return stockOrderRefundItemDao.deleteByPrimaryKey(record);
    }

    /**
     *
     */
    @Transactional
    public int insert(StockOrderRefundItem record) {
        return stockOrderRefundItemDao.insert(record);
    }

    /**
     *
     */
    @Transactional
    public int insertSelective(StockOrderRefundItem record) {
        return stockOrderRefundItemDao.insert(record);
    }

    /**
     *
     */
    public StockOrderRefundItem selectByPrimaryKey(Long id){
        StockOrderRefundItem record = new StockOrderRefundItem();
        record.setId(id);
        return stockOrderRefundItemDao.selectByPrimaryKey(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKeySelective(StockOrderRefundItem record) {
        return stockOrderRefundItemDao.updateByPrimaryKeySelective(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKey(StockOrderRefundItem record) {
        return stockOrderRefundItemDao.updateByPrimaryKey(record);
    }

    /**
    *
    */
    public List<StockOrderRefundItem> select(Page<StockOrderRefundItem> record){
        record.initFromNum();
        return stockOrderRefundItemDao.select(record);
    }

    /**
    *
    */
    public long count(Page<StockOrderRefundItem> record){
        return stockOrderRefundItemDao.count(record);
    }

}