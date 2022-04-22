package com.upedge.sms.modules.wholesale.service.impl;

import com.upedge.common.base.Page;
import com.upedge.sms.modules.wholesale.dao.WholesaleOrderAddressDao;
import com.upedge.sms.modules.wholesale.entity.WholesaleOrder;
import com.upedge.sms.modules.wholesale.entity.WholesaleOrderAddress;
import com.upedge.sms.modules.wholesale.service.WholesaleOrderAddressService;
import com.upedge.sms.modules.wholesale.service.WholesaleOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class WholesaleOrderAddressServiceImpl implements WholesaleOrderAddressService {

    @Autowired
    private WholesaleOrderAddressDao wholesaleOrderAddressDao;

    @Autowired
    WholesaleOrderService wholesaleOrderService;



    /**
     *
     */
    @Transactional
    public int deleteByPrimaryKey(Long id) {
        WholesaleOrderAddress record = new WholesaleOrderAddress();
        record.setId(id);
        return wholesaleOrderAddressDao.deleteByPrimaryKey(record);
    }

    /**
     *
     */
    @Transactional
    public int insert(WholesaleOrderAddress record) {
        return wholesaleOrderAddressDao.insert(record);
    }

    /**
     *
     */
    @Transactional
    public int insertSelective(WholesaleOrderAddress record) {
        return wholesaleOrderAddressDao.insert(record);
    }

    @Override
    public WholesaleOrderAddress selectByOrderId(Long orderId) {
        return wholesaleOrderAddressDao.selectByOrderId(orderId);
    }

    /**
     *
     */
    public WholesaleOrderAddress selectByPrimaryKey(Long id){
        WholesaleOrderAddress record = new WholesaleOrderAddress();
        record.setId(id);
        return wholesaleOrderAddressDao.selectByPrimaryKey(record);
    }

    /**
    *
    */
    @Override
    public int updateByPrimaryKeySelective(WholesaleOrderAddress record) {
        WholesaleOrderAddress wholesaleOrderAddress = selectByPrimaryKey(record.getId());
        if (null == wholesaleOrderAddress){
            return 0;
        }
        WholesaleOrder wholesaleOrder = wholesaleOrderService.selectByPrimaryKey(record.getOrderId());
        if (wholesaleOrder.getPayState() == 1){
            return 0;
        }
        return wholesaleOrderAddressDao.updateByPrimaryKeySelective(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKey(WholesaleOrderAddress record) {
        return wholesaleOrderAddressDao.updateByPrimaryKey(record);
    }

    /**
    *
    */
    public List<WholesaleOrderAddress> select(Page<WholesaleOrderAddress> record){
        record.initFromNum();
        return wholesaleOrderAddressDao.select(record);
    }

    /**
    *
    */
    public long count(Page<WholesaleOrderAddress> record){
        return wholesaleOrderAddressDao.count(record);
    }

}