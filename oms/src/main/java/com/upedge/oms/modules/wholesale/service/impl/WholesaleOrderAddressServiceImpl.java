package com.upedge.oms.modules.wholesale.service.impl;

import com.upedge.common.base.Page;
import com.upedge.oms.modules.wholesale.dao.WholesaleOrderAddressDao;
import com.upedge.oms.modules.wholesale.entity.WholesaleOrderAddress;
import com.upedge.oms.modules.wholesale.service.WholesaleOrderAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class WholesaleOrderAddressServiceImpl implements WholesaleOrderAddressService {

    @Autowired
    private WholesaleOrderAddressDao wholesaleOrderAddressDao;


    @Override
    public WholesaleOrderAddress selectOrderAddressById(Long id) {
        return wholesaleOrderAddressDao.queryWholesaleOrderAddressByOrderId(id);
    }

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
    @Transactional
    public int updateByPrimaryKeySelective(WholesaleOrderAddress record) {
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