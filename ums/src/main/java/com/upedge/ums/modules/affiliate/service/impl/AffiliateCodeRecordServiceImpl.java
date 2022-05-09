package com.upedge.ums.modules.affiliate.service.impl;

import com.upedge.common.base.Page;
import com.upedge.common.utils.EncryptUtil;
import com.upedge.ums.modules.affiliate.dao.AffiliateCodeRecordDao;
import com.upedge.ums.modules.affiliate.entity.AffiliateCodeRecord;
import com.upedge.ums.modules.affiliate.service.AffiliateCodeRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;


@Service
public class AffiliateCodeRecordServiceImpl implements AffiliateCodeRecordService {

    @Autowired
    private AffiliateCodeRecordDao affiliateCodeRecordDao;

    public static String key = "f167105ef452466f80c97c3b355658a4";

    /**
     *
     */
    @Transactional
    public int deleteByPrimaryKey(String affiliateCode) {
        AffiliateCodeRecord record = new AffiliateCodeRecord();
        record.setAffiliateCode(affiliateCode);
        return affiliateCodeRecordDao.deleteByPrimaryKey(record);
    }

    /**
     *
     */
    @Transactional
    public int insert(AffiliateCodeRecord record) {
        return affiliateCodeRecordDao.insert(record);
    }

    /**
     *
     */
    @Transactional
    public int insertSelective(AffiliateCodeRecord record) {
        return affiliateCodeRecordDao.insert(record);
    }

    @Override
    public AffiliateCodeRecord selectCustomerLatestAffiliateCode(Long customerId) {
        AffiliateCodeRecord affiliateCodeRecord = affiliateCodeRecordDao.selectCustomerLatestAffiliateCode(customerId);
        if (null == affiliateCodeRecord){
            affiliateCodeRecord = new AffiliateCodeRecord();
            affiliateCodeRecord.setCustomerId(customerId);
            String token = EncryptUtil.XORencode(String.valueOf(customerId), key);
            affiliateCodeRecord.setAffiliateCode(token);
            affiliateCodeRecord.setCreateTime(new Date());
            insert(affiliateCodeRecord);
        }
        return affiliateCodeRecord;
    }

    /**
     *
     */
    public AffiliateCodeRecord selectByPrimaryKey(String affiliateCode){
        AffiliateCodeRecord record = new AffiliateCodeRecord();
        record.setAffiliateCode(affiliateCode);
        return affiliateCodeRecordDao.selectByPrimaryKey(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKeySelective(AffiliateCodeRecord record) {
        return affiliateCodeRecordDao.updateByPrimaryKeySelective(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKey(AffiliateCodeRecord record) {
        return affiliateCodeRecordDao.updateByPrimaryKey(record);
    }

    /**
    *
    */
    public List<AffiliateCodeRecord> select(Page<AffiliateCodeRecord> record){
        record.initFromNum();
        return affiliateCodeRecordDao.select(record);
    }

    /**
    *
    */
    public long count(Page<AffiliateCodeRecord> record){
        return affiliateCodeRecordDao.count(record);
    }

}