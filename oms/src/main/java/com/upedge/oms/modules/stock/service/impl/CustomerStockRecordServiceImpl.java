package com.upedge.oms.modules.stock.service.impl;

import com.upedge.common.base.Page;
import com.upedge.common.constant.OrderType;
import com.upedge.common.utils.ListUtils;
import com.upedge.oms.modules.order.dao.OrderItemDao;
import com.upedge.oms.modules.stock.dao.CustomerStockRecordDao;
import com.upedge.oms.modules.stock.entity.CustomerStockRecord;
import com.upedge.oms.modules.stock.service.CustomerStockRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service
public class CustomerStockRecordServiceImpl implements CustomerStockRecordService {

    @Autowired
    CustomerStockRecordDao customerStockRecordDao;

    @Autowired
    OrderItemDao orderItemDao;



    /**
     *
     */
    @Transactional
    public int deleteByPrimaryKey(Long id) {
        CustomerStockRecord record = new CustomerStockRecord();
        record.setId(id);
        return customerStockRecordDao.deleteByPrimaryKey(record);
    }

    /**
     *
     */
    @Transactional
    public int insert(CustomerStockRecord record) {
        return customerStockRecordDao.insert(record);
    }

    /**
     *
     */
    @Transactional
    public int insertSelective(CustomerStockRecord record) {
        return customerStockRecordDao.insert(record);
    }

    /**
     * 保存库存抵扣记录
     * @param customerId
     * @param paymentId 订单交易ID
     * @param orderType 订单类型（普通订单或批发订单）
     * @return
     */
    @Override
    public int saveDischargeStockRecordByPaymentId(Long customerId, Long paymentId, Integer orderType) {
        List<CustomerStockRecord> records = new ArrayList<>();
        switch (orderType){
            case OrderType
                    .NORMAL:
                records = orderItemDao.selectStockRecordByPaymentId(paymentId,customerId);
                break;
            case OrderType.WHOLESALE:
                break;
        }
        if(ListUtils.isNotEmpty(records)){
            Date date = new Date();
            for (CustomerStockRecord record: records) {
                record.setCreateTime(date);
                record.setUpdateTime(date);
            }
            return customerStockRecordDao.insertByBatch(records);
        }
        return 0;
    }

    /**
     *
     */
    public CustomerStockRecord selectByPrimaryKey(Long id){
        return customerStockRecordDao.selectByPrimaryKey(id);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKeySelective(CustomerStockRecord record) {
        return customerStockRecordDao.updateByPrimaryKeySelective(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKey(CustomerStockRecord record) {
        return customerStockRecordDao.updateByPrimaryKey(record);
    }

    /**
    *
    */
    public List<CustomerStockRecord> select(Page<CustomerStockRecord> record){
        record.initFromNum();
        return customerStockRecordDao.select(record);
    }

    /**
    *
    */
    public long count(Page<CustomerStockRecord> record){
        return customerStockRecordDao.count(record);
    }

}