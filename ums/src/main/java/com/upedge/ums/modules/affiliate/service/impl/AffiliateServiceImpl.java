package com.upedge.ums.modules.affiliate.service.impl;

import com.upedge.common.base.Page;
import com.upedge.common.constant.Constant;
import com.upedge.common.constant.ResultCode;
import com.upedge.common.model.user.vo.CommissionRecordVo;
import com.upedge.common.model.user.vo.Session;
import com.upedge.common.web.util.RequestUtil;
import com.upedge.common.web.util.UserUtil;
import com.upedge.ums.modules.account.dao.AccountMapper;
import com.upedge.ums.modules.account.dao.AccountUserMapper;
import com.upedge.ums.modules.account.entity.Account;
import com.upedge.ums.modules.affiliate.dao.AffiliateCommissionRecordDao;
import com.upedge.ums.modules.affiliate.dao.AffiliateCommissionWithdrawalDao;
import com.upedge.ums.modules.affiliate.dao.AffiliateDao;
import com.upedge.ums.modules.affiliate.entity.Affiliate;
import com.upedge.ums.modules.affiliate.entity.AffiliateCommissionRecord;
import com.upedge.ums.modules.affiliate.entity.AffiliateCommissionWithdrawal;
import com.upedge.ums.modules.affiliate.request.AffiliateCommissionWithdrawalAddRequest;
import com.upedge.ums.modules.affiliate.response.AffiliateCommissionRecordListResponse;
import com.upedge.ums.modules.affiliate.response.AffiliateCommissionWithdrawalAddResponse;
import com.upedge.ums.modules.affiliate.response.AffiliateCommissionWithdrawalListResponse;
import com.upedge.ums.modules.affiliate.response.AffiliateListResponse;
import com.upedge.ums.modules.affiliate.service.AffiliateService;
import com.upedge.ums.modules.affiliate.vo.RefereeCommissionVo;
import com.upedge.ums.modules.affiliate.vo.RefereeMonthCommissionVo;
import com.upedge.ums.modules.affiliate.vo.WithdrawalVo;
import com.upedge.ums.modules.user.dao.UserInfoDao;
import com.upedge.ums.modules.user.entity.Customer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class AffiliateServiceImpl implements AffiliateService {

    @Autowired
    private AffiliateDao affiliateDao;

    @Autowired
    AffiliateCommissionRecordDao affiliateCommissionRecordDao;

    @Autowired
    AffiliateCommissionWithdrawalDao affiliateCommissionWithdrawalDao;

    @Autowired
    AccountUserMapper accountUserMapper;

    @Autowired
    AccountMapper accountMapper;

    @Autowired
    UserInfoDao userInfoMapper;

    @Autowired
    RedisTemplate<String, Object> redisTemplate;




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
    public AffiliateCommissionWithdrawalListResponse withdrawalList(Long customerId) {
        List<WithdrawalVo> withdrawalVos = affiliateCommissionWithdrawalDao.selectWithdrawalList(customerId);
        return new AffiliateCommissionWithdrawalListResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,withdrawalVos);
    }

    @Override
    public AffiliateCommissionRecordListResponse commissionMonthRecord(Long customerId) {
        List<RefereeMonthCommissionVo> refereeMonthCommissionVos = affiliateCommissionRecordDao.searchCommissionByMonth(customerId);

        return new AffiliateCommissionRecordListResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,refereeMonthCommissionVos);
    }

    @Override
    public AffiliateListResponse refereeCommissionList(Long customerId) {

        List<RefereeCommissionVo> refereeCommissionVos = affiliateDao.searchCommissionByReferee(customerId);

        return new AffiliateListResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,refereeCommissionVos);
    }

    @Override
    public void affiliateBind(Customer customer) {
        HttpServletRequest request = RequestUtil.getRequest();

        Long referrerId = (Long) request.getSession().getAttribute("referrer");

        if(null == referrerId){
            return;
        }

        Affiliate affiliate = new Affiliate();
        affiliate.setReferrerId(referrerId);
        affiliate.setRefereeId(customer.getId());
        affiliate.setCreateTime(new Date());
        affiliate.setRefereeCommission(BigDecimal.ZERO);
        affiliate.setSource(0);
        affiliateDao.insert(affiliate);
    }

    /**
     *
     */
    public Affiliate selectByPrimaryKey(Long id){
        Affiliate record = new Affiliate();
        record.setId(id);
        return affiliateDao.selectByPrimaryKey(record);
    }

    @Override
    public AffiliateCommissionWithdrawalAddResponse CommissionWithdrawRequest(AffiliateCommissionWithdrawalAddRequest request) {
        Session session = UserUtil.getSession(redisTemplate);

        Account account = accountUserMapper.selectAccountByUser(session.getId());

        if(account.getCommission().compareTo(request.getAmount()) == 0){
            return new AffiliateCommissionWithdrawalAddResponse(ResultCode.FAIL_CODE,"Insufficient account commission");
        }

        AffiliateCommissionWithdrawal withdrawal = request.toAffiliateCommissionWithdrawal();
        withdrawal.setCustomerId(session.getCustomerId());
        withdrawal.setPaymentAccount(String.valueOf(account.getId()));

        affiliateCommissionWithdrawalDao.insert(withdrawal);

        return new AffiliateCommissionWithdrawalAddResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void addAffiliateCommissionRecord(CommissionRecordVo commissionRecordVo) {

        AffiliateCommissionRecord commissionRecord = toAffiliateCommissionRecord(commissionRecordVo);

        affiliateCommissionRecordDao.insert(commissionRecord);

        if(1 != commissionRecord.getState()){
            affiliateDao.updateRefereeCommission(commissionRecord.getRefereeId(),commissionRecord.getCommission().multiply(new BigDecimal("-1")));
        }else {
            affiliateDao.updateRefereeCommission(commissionRecord.getRefereeId(),commissionRecord.getCommission());
        }

    }

    public AffiliateCommissionRecord toAffiliateCommissionRecord(CommissionRecordVo commissionRecordVo){
        AffiliateCommissionRecord affiliateCommissionRecord=new AffiliateCommissionRecord();
        affiliateCommissionRecord.setRefereeId(commissionRecordVo.getRefereeId());
        affiliateCommissionRecord.setReferrerId(commissionRecordVo.getReferrerId());
        affiliateCommissionRecord.setOrderId(commissionRecordVo.getOrderId());
        affiliateCommissionRecord.setOrderType(commissionRecordVo.getOrderType());
        affiliateCommissionRecord.setCommission(commissionRecordVo.getOrderAmount().multiply(new BigDecimal("0.01")));
        affiliateCommissionRecord.setState(commissionRecordVo.getState());
        affiliateCommissionRecord.setCreateTime(new Date());
        return affiliateCommissionRecord;
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


    @Override
    public Affiliate queryAffiliateByReferee(Long refereeId) {
        return affiliateDao.queryAffiliateByReferee(refereeId);
    }

    @Override
    public Affiliate selectAffiliateVoByrefereeId(Long customerId) {
        return affiliateDao.selectAffiliateVoByrefereeId(customerId);
    }
}