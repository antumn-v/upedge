package com.upedge.sms.modules.wholesale.service.impl;

import com.upedge.common.base.Page;
import com.upedge.common.utils.ListUtils;
import com.upedge.sms.modules.wholesale.dao.WholesaleOrderItemDao;
import com.upedge.sms.modules.wholesale.entity.WholesaleOrderItem;
import com.upedge.sms.modules.wholesale.service.WholesaleOrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class WholesaleOrderItemServiceImpl implements WholesaleOrderItemService {

    @Autowired
    private WholesaleOrderItemDao wholesaleOrderItemDao;



    /**
     *
     */
    @Transactional
    public int deleteByPrimaryKey(Long id) {
        WholesaleOrderItem record = new WholesaleOrderItem();
        record.setId(id);
        return wholesaleOrderItemDao.deleteByPrimaryKey(record);
    }

    /**
     *
     */
    @Transactional
    public int insert(WholesaleOrderItem record) {
        return wholesaleOrderItemDao.insert(record);
    }

    @Override
    public int insertByBatch(List<WholesaleOrderItem> wholesaleOrderItems) {
        if (ListUtils.isNotEmpty(wholesaleOrderItems)){
            return wholesaleOrderItemDao.insertByBatch(wholesaleOrderItems);
        }
        return 0;
    }

    /**
     *
     */
    @Transactional
    public int insertSelective(WholesaleOrderItem record) {
        return wholesaleOrderItemDao.insert(record);
    }

    @Override
    public List<WholesaleOrderItem> selectByOrderId(Long orderId) {
        return wholesaleOrderItemDao.selectByOrderId(orderId);
    }

    /**
     *
     */
    public WholesaleOrderItem selectByPrimaryKey(Long id){
        WholesaleOrderItem record = new WholesaleOrderItem();
        record.setId(id);
        return wholesaleOrderItemDao.selectByPrimaryKey(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKeySelective(WholesaleOrderItem record) {
        return wholesaleOrderItemDao.updateByPrimaryKeySelective(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKey(WholesaleOrderItem record) {
        return wholesaleOrderItemDao.updateByPrimaryKey(record);
    }

    /**
    *
    */
    public List<WholesaleOrderItem> select(Page<WholesaleOrderItem> record){
        record.initFromNum();
        return wholesaleOrderItemDao.select(record);
    }

    /**
    *
    */
    public long count(Page<WholesaleOrderItem> record){
        return wholesaleOrderItemDao.count(record);
    }

}