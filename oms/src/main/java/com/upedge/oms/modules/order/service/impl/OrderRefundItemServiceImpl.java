package com.upedge.oms.modules.order.service.impl;

import com.upedge.common.base.Page;
import com.upedge.oms.modules.order.dao.OrderRefundItemDao;
import com.upedge.oms.modules.order.entity.OrderRefundItem;
import com.upedge.oms.modules.order.service.OrderRefundItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class OrderRefundItemServiceImpl implements OrderRefundItemService {

    @Autowired
    private OrderRefundItemDao orderRefundItemDao;



    /**
     *
     */
    @Transactional
    public int deleteByPrimaryKey(Integer id) {
        OrderRefundItem record = new OrderRefundItem();
        record.setId(id);
        return orderRefundItemDao.deleteByPrimaryKey(record);
    }

    /**
     *
     */
    @Transactional
    public int insert(OrderRefundItem record) {
        return orderRefundItemDao.insert(record);
    }

    /**
     *
     */
    @Transactional
    public int insertSelective(OrderRefundItem record) {
        return orderRefundItemDao.insert(record);
    }

    /**
     *
     */
    public OrderRefundItem selectByPrimaryKey(Integer id){
        OrderRefundItem record = new OrderRefundItem();
        record.setId(id);
        return orderRefundItemDao.selectByPrimaryKey(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKeySelective(OrderRefundItem record) {
        return orderRefundItemDao.updateByPrimaryKeySelective(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKey(OrderRefundItem record) {
        return orderRefundItemDao.updateByPrimaryKey(record);
    }

    /**
    *
    */
    public List<OrderRefundItem> select(Page<OrderRefundItem> record){
        record.initFromNum();
        return orderRefundItemDao.select(record);
    }

    /**
    *
    */
    public long count(Page<OrderRefundItem> record){
        return orderRefundItemDao.count(record);
    }

    /**
     * 根据RefundId查询list
     * @param id
     * @return
     */
    @Override
    public List<OrderRefundItem> selectOrderRefundItemListbByRefundId(Long refundId) {

        return orderRefundItemDao.selectOrderRefundItemListbByRefundId(refundId);

    }

}