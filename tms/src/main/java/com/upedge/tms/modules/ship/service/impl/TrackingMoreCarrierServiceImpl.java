package com.upedge.tms.modules.ship.service.impl;

import com.upedge.common.base.Page;
import com.upedge.common.constant.Constant;
import com.upedge.common.constant.ResultCode;
import com.upedge.tms.modules.ship.dao.TrackingMoreCarrierDao;
import com.upedge.tms.modules.ship.entity.TrackingMoreCarrier;
import com.upedge.tms.modules.ship.response.TrackingMoreCarrierListResponse;
import com.upedge.tms.modules.ship.service.TrackingMoreCarrierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class TrackingMoreCarrierServiceImpl implements TrackingMoreCarrierService {

    @Autowired
    private TrackingMoreCarrierDao trackingMoreCarrierDao;



    /**
     *
     */
    @Transactional
    public int deleteByPrimaryKey(String code) {
        TrackingMoreCarrier record = new TrackingMoreCarrier();
        record.setCode(code);
        return trackingMoreCarrierDao.deleteByPrimaryKey(record);
    }

    /**
     *
     */
    @Transactional
    public int insert(TrackingMoreCarrier record) {
        return trackingMoreCarrierDao.insert(record);
    }

    /**
     *
     */
    @Transactional
    public int insertSelective(TrackingMoreCarrier record) {
        return trackingMoreCarrierDao.insert(record);
    }

    /**
     *
     */
    public TrackingMoreCarrier selectByPrimaryKey(String code){
        TrackingMoreCarrier record = new TrackingMoreCarrier();
        record.setCode(code);
        return trackingMoreCarrierDao.selectByPrimaryKey(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKeySelective(TrackingMoreCarrier record) {
        return trackingMoreCarrierDao.updateByPrimaryKeySelective(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKey(TrackingMoreCarrier record) {
        return trackingMoreCarrierDao.updateByPrimaryKey(record);
    }

    /**
    *
    */
    public List<TrackingMoreCarrier> select(Page<TrackingMoreCarrier> record){
        record.initFromNum();
        return trackingMoreCarrierDao.select(record);
    }

    /**
    *
    */
    public long count(Page<TrackingMoreCarrier> record){
        return trackingMoreCarrierDao.count(record);
    }

    @Override
    public TrackingMoreCarrierListResponse allTrackingMoreCarrier() {
        List<TrackingMoreCarrier> trackingMoreCarrierList=trackingMoreCarrierDao.allTrackingMoreCarrier();
        return new TrackingMoreCarrierListResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS,trackingMoreCarrierList);
    }

}