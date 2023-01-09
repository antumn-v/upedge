package com.upedge.oms.modules.common.service.impl;

import com.upedge.common.base.Page;
import com.upedge.common.constant.key.RedisKey;
import com.upedge.oms.modules.common.dao.OrderErrorMessageDao;
import com.upedge.oms.modules.common.entity.OrderErrorMessage;
import com.upedge.oms.modules.common.service.OrderErrorMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class OrderErrorMessageServiceImpl implements OrderErrorMessageService {

    @Autowired
    private OrderErrorMessageDao orderErrorMessageDao;

    @Autowired
    RedisTemplate redisTemplate;



    /**
     *
     */
    @Transactional
    public int deleteByPrimaryKey(Integer id) {
        OrderErrorMessage record = new OrderErrorMessage();
        record.setId(id);
        return orderErrorMessageDao.deleteByPrimaryKey(record);
    }

    /**
     *
     */
    @Transactional
    public int insert(OrderErrorMessage record) {
        return orderErrorMessageDao.insert(record);
    }

    /**
     *
     */
    @Transactional
    public int insertSelective(OrderErrorMessage record) {
        orderErrorMessageDao.insert(record);
        redisTemplate.opsForHash().put(RedisKey.HASH_ORDER_ERROR_MESSAGE,record.getId().toString(),record);
        return 1;
    }

    /**
     *
     */
    public OrderErrorMessage selectByPrimaryKey(Integer id){
        OrderErrorMessage record = new OrderErrorMessage();
        record.setId(id);
        return orderErrorMessageDao.selectByPrimaryKey(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKeySelective(OrderErrorMessage record) {
        return orderErrorMessageDao.updateByPrimaryKeySelective(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKey(OrderErrorMessage record) {
        return orderErrorMessageDao.updateByPrimaryKey(record);
    }

    /**
    *
    */
    public List<OrderErrorMessage> select(Page<OrderErrorMessage> record){
        record.initFromNum();
        return orderErrorMessageDao.select(record);
    }

    /**
    *
    */
    public long count(Page<OrderErrorMessage> record){
        return orderErrorMessageDao.count(record);
    }

}