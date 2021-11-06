package com.upedge.oms.modules.reason.service.impl;

import com.upedge.common.base.Page;
import com.upedge.common.constant.Constant;
import com.upedge.common.constant.ResultCode;
import com.upedge.common.utils.IdGenerate;
import com.upedge.oms.modules.reason.dao.RefundReasonDao;
import com.upedge.oms.modules.reason.entity.RefundReason;
import com.upedge.oms.modules.reason.response.RefundReasonListResponse;
import com.upedge.oms.modules.reason.service.RefundReasonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class RefundReasonServiceImpl implements RefundReasonService {

    @Autowired
    private RefundReasonDao refundReasonDao;



    /**
     *
     */
    @Transactional
    public int deleteByPrimaryKey(Long id) {
        RefundReason record = new RefundReason();
        record.setId(id);
        return refundReasonDao.deleteByPrimaryKey(record);
    }

    /**
     *
     */
    @Transactional
    public int insert(RefundReason record) {
        record.setId(IdGenerate.nextId());
        return refundReasonDao.insert(record);
    }

    /**
     *
     */
    @Transactional
    public int insertSelective(RefundReason record) {
        record.setId(IdGenerate.nextId());
        return refundReasonDao.insert(record);
    }

    /**
     *
     */
    public RefundReason selectByPrimaryKey(Long id){
        RefundReason record = new RefundReason();
        record.setId(id);
        return refundReasonDao.selectByPrimaryKey(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKeySelective(RefundReason record) {
        return refundReasonDao.updateByPrimaryKeySelective(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKey(RefundReason record) {
        return refundReasonDao.updateByPrimaryKey(record);
    }

    /**
    *
    */
    public List<RefundReason> select(Page<RefundReason> record){
        record.initFromNum();
        return refundReasonDao.select(record);
    }

    /**
    *
    */
    public long count(Page<RefundReason> record){
        return refundReasonDao.count(record);
    }

    @Override
    public RefundReasonListResponse all() {
        List<RefundReason> results = refundReasonDao.all();
        return new RefundReasonListResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS,results,null);
    }
}