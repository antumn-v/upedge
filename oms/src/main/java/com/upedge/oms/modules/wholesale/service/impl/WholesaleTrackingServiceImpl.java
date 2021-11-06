package com.upedge.oms.modules.wholesale.service.impl;

import com.upedge.common.base.Page;
import com.upedge.oms.modules.wholesale.dao.WholesaleTrackingDao;
import com.upedge.oms.modules.wholesale.entity.WholesaleTracking;
import com.upedge.oms.modules.wholesale.service.WholesaleTrackingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class WholesaleTrackingServiceImpl implements WholesaleTrackingService {

    @Autowired
    private WholesaleTrackingDao wholesaleTrackingDao;



    /**
     *
     */
    @Transactional
    public int deleteByPrimaryKey(Long id) {
        WholesaleTracking record = new WholesaleTracking();
        record.setId(id);
        return wholesaleTrackingDao.deleteByPrimaryKey(record);
    }

    /**
     *
     */
    @Transactional
    public int insert(WholesaleTracking record) {
        return wholesaleTrackingDao.insert(record);
    }

    /**
     *
     */
    @Transactional
    public int insertSelective(WholesaleTracking record) {
        return wholesaleTrackingDao.insert(record);
    }

    /**
     *
     */
    public WholesaleTracking selectByPrimaryKey(Long id){
        WholesaleTracking record = new WholesaleTracking();
        record.setId(id);
        return wholesaleTrackingDao.selectByPrimaryKey(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKeySelective(WholesaleTracking record) {
        return wholesaleTrackingDao.updateByPrimaryKeySelective(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKey(WholesaleTracking record) {
        return wholesaleTrackingDao.updateByPrimaryKey(record);
    }

    /**
    *
    */
    public List<WholesaleTracking> select(Page<WholesaleTracking> record){
        record.initFromNum();
        return wholesaleTrackingDao.select(record);
    }

    /**
    *
    */
    public long count(Page<WholesaleTracking> record){
        return wholesaleTrackingDao.count(record);
    }

}