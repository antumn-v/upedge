package com.upedge.oms.modules.wholesale.service.impl;

import com.upedge.common.base.Page;
import com.upedge.oms.modules.wholesale.dao.WholesaleReshipInfoDao;
import com.upedge.oms.modules.wholesale.entity.WholesaleReshipInfo;
import com.upedge.oms.modules.wholesale.service.WholesaleReshipInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class WholesaleReshipInfoServiceImpl implements WholesaleReshipInfoService {

    @Autowired
    private WholesaleReshipInfoDao wholesaleReshipInfoDao;



    /**
     *
     */
    @Transactional
    public int deleteByPrimaryKey(Long orderId) {
        WholesaleReshipInfo record = new WholesaleReshipInfo();
        record.setOrderId(orderId);
        return wholesaleReshipInfoDao.deleteByPrimaryKey(record);
    }

    /**
     *
     */
    @Transactional
    public int insert(WholesaleReshipInfo record) {
        return wholesaleReshipInfoDao.insert(record);
    }

    /**
     *
     */
    @Transactional
    public int insertSelective(WholesaleReshipInfo record) {
        return wholesaleReshipInfoDao.insert(record);
    }

    /**
     *
     */
    public WholesaleReshipInfo selectByPrimaryKey(Long orderId){
        return wholesaleReshipInfoDao.selectByPrimaryKey(orderId);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKeySelective(WholesaleReshipInfo record) {
        return wholesaleReshipInfoDao.updateByPrimaryKeySelective(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKey(WholesaleReshipInfo record) {
        return wholesaleReshipInfoDao.updateByPrimaryKey(record);
    }

    /**
    *
    */
    public List<WholesaleReshipInfo> select(Page<WholesaleReshipInfo> record){
        record.initFromNum();
        return wholesaleReshipInfoDao.select(record);
    }

    /**
    *
    */
    public long count(Page<WholesaleReshipInfo> record){
        return wholesaleReshipInfoDao.count(record);
    }

}