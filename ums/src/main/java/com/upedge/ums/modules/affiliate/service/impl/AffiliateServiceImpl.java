package com.upedge.ums.modules.affiliate.service.impl;

import com.upedge.common.utils.IdGenerate;
import com.upedge.ums.modules.user.entity.Customer;
import com.upedge.ums.modules.user.entity.CustomerSetting;
import com.upedge.ums.modules.user.service.CustomerSettingService;
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

    @Autowired
    CustomerSettingService customerSettingService;




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

    @Override
    public String customerReferralCode(Long customerId) {
        CustomerSetting customerSetting = customerSettingService.selectByCustomerAndSettingName(customerId,CustomerSetting.AFFILIATE_REFERRAL_CODE);
        if (null == customerSetting){
            String code = IdGenerate.generateShortUuid();
            customerSetting = new CustomerSetting();
            customerSetting.setCustomerId(customerId);
            customerSetting.setSettingValue(code);
            customerSetting.setSettingName(CustomerSetting.AFFILIATE_REFERRAL_CODE);
            customerSettingService.insert(customerSetting);
            return code;
        }
        return customerSetting.getSettingValue();
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