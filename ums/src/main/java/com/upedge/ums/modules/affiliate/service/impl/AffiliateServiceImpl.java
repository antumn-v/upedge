package com.upedge.ums.modules.affiliate.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import com.upedge.common.base.Page;
import org.springframework.beans.factory.annotation.Autowired;
import com.upedge.ums.modules.affiliate.dao.AffiliateDao;
import com.upedge.ums.modules.affiliate.entity.Affiliate;
import com.upedge.ums.modules.affiliate.service.AffiliateService;


@Service
public class AffiliateServiceImpl implements AffiliateService {

    @Autowired
    private AffiliateDao affiliateDao;



    /**
     *
     */
    @Transactional
    public int deleteByPrimaryKey(Long id) {
        Affiliate record = new Affiliate();
        record.setId(id);
        return affiliateDao.deleteByPrimaryKey(record);
    }

    /**
     *
     */
    @Transactional
    public int insert(Affiliate record) {
        return affiliateDao.insert(record);
    }

    /**
     *
     */
    @Transactional
    public int insertSelective(Affiliate record) {
        return affiliateDao.insert(record);
    }

    /**
     *
     */
    public Affiliate selectByPrimaryKey(Long id){
        Affiliate record = new Affiliate();
        record.setId(id);
        return affiliateDao.selectByPrimaryKey(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKeySelective(Affiliate record) {
        return affiliateDao.updateByPrimaryKeySelective(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKey(Affiliate record) {
        return affiliateDao.updateByPrimaryKey(record);
    }

    /**
    *
    */
    public List<Affiliate> select(Page<Affiliate> record){
        record.initFromNum();
        return affiliateDao.select(record);
    }

    /**
    *
    */
    public long count(Page<Affiliate> record){
        return affiliateDao.count(record);
    }

}