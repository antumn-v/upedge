package com.upedge.oms.modules.wholesale.service.impl;

import com.upedge.common.base.Page;
import com.upedge.oms.modules.wholesale.dao.WholesaleShipReviewRecordDao;
import com.upedge.oms.modules.wholesale.entity.WholesaleShipReviewRecord;
import com.upedge.oms.modules.wholesale.service.WholesaleShipReviewRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class WholesaleShipReviewRecordServiceImpl implements WholesaleShipReviewRecordService {

    @Autowired
    private WholesaleShipReviewRecordDao wholesaleShipReviewRecordDao;



    /**
     *
     */
    @Transactional
    public int deleteByPrimaryKey(Long id) {
        WholesaleShipReviewRecord record = new WholesaleShipReviewRecord();
        record.setId(id);
        return wholesaleShipReviewRecordDao.deleteByPrimaryKey(record);
    }

    /**
     *
     */
    @Transactional
    public int insert(WholesaleShipReviewRecord record) {
        return wholesaleShipReviewRecordDao.insert(record);
    }

    /**
     *
     */
    @Transactional
    public int insertSelective(WholesaleShipReviewRecord record) {
        return wholesaleShipReviewRecordDao.insert(record);
    }

    /**
     *
     */
    public WholesaleShipReviewRecord selectByPrimaryKey(Long id){
        WholesaleShipReviewRecord record = new WholesaleShipReviewRecord();
        record.setId(id);
        return wholesaleShipReviewRecordDao.selectByPrimaryKey(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKeySelective(WholesaleShipReviewRecord record) {
        return wholesaleShipReviewRecordDao.updateByPrimaryKeySelective(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKey(WholesaleShipReviewRecord record) {
        return wholesaleShipReviewRecordDao.updateByPrimaryKey(record);
    }

    /**
    *
    */
    public List<WholesaleShipReviewRecord> select(Page<WholesaleShipReviewRecord> record){
        record.initFromNum();
        return wholesaleShipReviewRecordDao.select(record);
    }

    /**
    *
    */
    public long count(Page<WholesaleShipReviewRecord> record){
        return wholesaleShipReviewRecordDao.count(record);
    }

}