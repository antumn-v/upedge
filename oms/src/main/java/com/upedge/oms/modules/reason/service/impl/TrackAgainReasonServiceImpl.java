package com.upedge.oms.modules.reason.service.impl;

import com.upedge.common.base.Page;
import com.upedge.common.constant.Constant;
import com.upedge.common.constant.ResultCode;
import com.upedge.common.utils.IdGenerate;
import com.upedge.oms.modules.reason.dao.TrackAgainReasonDao;
import com.upedge.oms.modules.reason.entity.TrackAgainReason;
import com.upedge.oms.modules.reason.response.TrackAgainReasonListResponse;
import com.upedge.oms.modules.reason.service.TrackAgainReasonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class TrackAgainReasonServiceImpl implements TrackAgainReasonService {

    @Autowired
    private TrackAgainReasonDao trackAgainReasonDao;

    @Override
    public TrackAgainReasonListResponse all() {
        List<TrackAgainReason> results = trackAgainReasonDao.all();
        return new TrackAgainReasonListResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS,results,null);
    }

    /**
     *
     */
    @Transactional
    public int deleteByPrimaryKey(Long id) {
        TrackAgainReason record = new TrackAgainReason();
        record.setId(id);
        return trackAgainReasonDao.deleteByPrimaryKey(record);
    }

    /**
     *
     */
    @Transactional
    public int insert(TrackAgainReason record) {
        record.setId(IdGenerate.nextId());
        return trackAgainReasonDao.insert(record);
    }

    /**
     *
     */
    @Transactional
    public int insertSelective(TrackAgainReason record) {
        record.setId(IdGenerate.nextId());
        return trackAgainReasonDao.insert(record);
    }

    /**
     *
     */
    public TrackAgainReason selectByPrimaryKey(Long id){
        TrackAgainReason record = new TrackAgainReason();
        record.setId(id);
        return trackAgainReasonDao.selectByPrimaryKey(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKeySelective(TrackAgainReason record) {
        return trackAgainReasonDao.updateByPrimaryKeySelective(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKey(TrackAgainReason record) {
        return trackAgainReasonDao.updateByPrimaryKey(record);
    }

    /**
    *
    */
    public List<TrackAgainReason> select(Page<TrackAgainReason> record){
        record.initFromNum();
        return trackAgainReasonDao.select(record);
    }

    /**
    *
    */
    public long count(Page<TrackAgainReason> record){
        return trackAgainReasonDao.count(record);
    }

}