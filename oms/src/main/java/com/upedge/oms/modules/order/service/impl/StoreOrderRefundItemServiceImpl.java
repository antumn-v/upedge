package com.upedge.oms.modules.order.service.impl;

import com.upedge.common.base.Page;
import com.upedge.oms.modules.order.dao.StoreOrderRefundItemDao;
import com.upedge.oms.modules.order.entity.StoreOrderRefundItem;
import com.upedge.oms.modules.order.service.StoreOrderRefundItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class StoreOrderRefundItemServiceImpl implements StoreOrderRefundItemService {

    @Autowired
    private StoreOrderRefundItemDao storeOrderRefundItemDao;



    /**
     *
     */
    @Transactional
    public int deleteByPrimaryKey(Long id) {
        StoreOrderRefundItem record = new StoreOrderRefundItem();
        record.setId(id);
        return storeOrderRefundItemDao.deleteByPrimaryKey(record);
    }

    /**
     *
     */
    @Transactional
    public int insert(StoreOrderRefundItem record) {
        return storeOrderRefundItemDao.insert(record);
    }

    /**
     *
     */
    @Transactional
    public int insertSelective(StoreOrderRefundItem record) {
        return storeOrderRefundItemDao.insert(record);
    }

    /**
     *
     */
    public StoreOrderRefundItem selectByPrimaryKey(Long id){
        StoreOrderRefundItem record = new StoreOrderRefundItem();
        record.setId(id);
        return storeOrderRefundItemDao.selectByPrimaryKey(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKeySelective(StoreOrderRefundItem record) {
        return storeOrderRefundItemDao.updateByPrimaryKeySelective(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKey(StoreOrderRefundItem record) {
        return storeOrderRefundItemDao.updateByPrimaryKey(record);
    }

    /**
    *
    */
    public List<StoreOrderRefundItem> select(Page<StoreOrderRefundItem> record){
        record.initFromNum();
        return storeOrderRefundItemDao.select(record);
    }

    /**
    *
    */
    public long count(Page<StoreOrderRefundItem> record){
        return storeOrderRefundItemDao.count(record);
    }

}