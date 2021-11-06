package com.upedge.oms.modules.stock.service.impl;

import com.upedge.common.base.Page;
import com.upedge.oms.modules.stock.dao.StockOrderRefundDao;
import com.upedge.oms.modules.stock.entity.StockOrderRefund;
import com.upedge.oms.modules.stock.service.StockOrderRefundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class StockOrderRefundServiceImpl implements StockOrderRefundService {

    @Autowired
    private StockOrderRefundDao stockOrderRefundDao;



    /**
     *
     */
    @Transactional
    public int deleteByPrimaryKey(Long id) {
        StockOrderRefund record = new StockOrderRefund();
        record.setId(id);
        return stockOrderRefundDao.deleteByPrimaryKey(record);
    }

    /**
     *
     */
    @Transactional
    public int insert(StockOrderRefund record) {
        return stockOrderRefundDao.insert(record);
    }

    /**
     *
     */
    @Transactional
    public int insertSelective(StockOrderRefund record) {
        return stockOrderRefundDao.insert(record);
    }

    /**
     *
     */
    public StockOrderRefund selectByPrimaryKey(Long id){
        return stockOrderRefundDao.selectByPrimaryKey(id);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKeySelective(StockOrderRefund record) {
        return stockOrderRefundDao.updateByPrimaryKeySelective(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKey(StockOrderRefund record) {
        return stockOrderRefundDao.updateByPrimaryKey(record);
    }

    /**
    *
    */
    public List<StockOrderRefund> select(Page<StockOrderRefund> record){
        record.initFromNum();
        return stockOrderRefundDao.select(record);
    }

    /**
    *
    */
    public long count(Page<StockOrderRefund> record){
        return stockOrderRefundDao.count(record);
    }

}