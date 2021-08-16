package com.upedge.ums.modules.affiliate.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import com.upedge.common.base.Page;
import org.springframework.beans.factory.annotation.Autowired;
import com.upedge.ums.modules.affiliate.dao.AffiliateCommissionWithdrawalDao;
import com.upedge.ums.modules.affiliate.entity.AffiliateCommissionWithdrawal;
import com.upedge.ums.modules.affiliate.service.AffiliateCommissionWithdrawalService;


@Service
public class AffiliateCommissionWithdrawalServiceImpl implements AffiliateCommissionWithdrawalService {

    @Autowired
    private AffiliateCommissionWithdrawalDao affiliateCommissionWithdrawalDao;



    /**
     *
     */
    @Transactional
    public int deleteByPrimaryKey(Long id) {
        AffiliateCommissionWithdrawal record = new AffiliateCommissionWithdrawal();
        record.setId(id);
        return affiliateCommissionWithdrawalDao.deleteByPrimaryKey(record);
    }

    /**
     *
     */
    @Transactional
    public int insert(AffiliateCommissionWithdrawal record) {
        return affiliateCommissionWithdrawalDao.insert(record);
    }

    /**
     *
     */
    @Transactional
    public int insertSelective(AffiliateCommissionWithdrawal record) {
        return affiliateCommissionWithdrawalDao.insert(record);
    }

    /**
     *
     */
    public AffiliateCommissionWithdrawal selectByPrimaryKey(Long id){
        AffiliateCommissionWithdrawal record = new AffiliateCommissionWithdrawal();
        record.setId(id);
        return affiliateCommissionWithdrawalDao.selectByPrimaryKey(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKeySelective(AffiliateCommissionWithdrawal record) {
        return affiliateCommissionWithdrawalDao.updateByPrimaryKeySelective(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKey(AffiliateCommissionWithdrawal record) {
        return affiliateCommissionWithdrawalDao.updateByPrimaryKey(record);
    }

    /**
    *
    */
    public List<AffiliateCommissionWithdrawal> select(Page<AffiliateCommissionWithdrawal> record){
        record.initFromNum();
        return affiliateCommissionWithdrawalDao.select(record);
    }

    /**
    *
    */
    public long count(Page<AffiliateCommissionWithdrawal> record){
        return affiliateCommissionWithdrawalDao.count(record);
    }

}