package com.upedge.ums.modules.affiliate.service.impl;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.base.Page;
import com.upedge.common.constant.Constant;
import com.upedge.common.constant.ResultCode;
import com.upedge.common.constant.key.RedisKey;
import com.upedge.common.model.user.vo.AffiliateVo;
import com.upedge.common.model.user.vo.CommissionRecordVo;
import com.upedge.common.model.user.vo.Session;
import com.upedge.common.web.util.UserUtil;
import com.upedge.ums.modules.account.dao.AccountUserMapper;
import com.upedge.ums.modules.account.entity.Account;
import com.upedge.ums.modules.account.service.AccountService;
import com.upedge.ums.modules.affiliate.dao.AffiliateCommissionRecordDao;
import com.upedge.ums.modules.affiliate.dao.AffiliateCommissionWithdrawalDao;
import com.upedge.ums.modules.affiliate.dao.AffiliateDao;
import com.upedge.ums.modules.affiliate.entity.Affiliate;
import com.upedge.ums.modules.affiliate.entity.AffiliateCodeRecord;
import com.upedge.ums.modules.affiliate.entity.AffiliateCommissionRecord;
import com.upedge.ums.modules.affiliate.entity.AffiliateCommissionWithdrawal;
import com.upedge.ums.modules.affiliate.request.AffiliateAddRequest;
import com.upedge.ums.modules.affiliate.request.AffiliateCommissionWithdrawalAddRequest;
import com.upedge.ums.modules.affiliate.request.DisableAffiliateRebateRequest;
import com.upedge.ums.modules.affiliate.response.*;
import com.upedge.ums.modules.affiliate.service.AffiliateCodeRecordService;
import com.upedge.ums.modules.affiliate.service.AffiliateService;
import com.upedge.ums.modules.affiliate.vo.RefereeCommissionVo;
import com.upedge.ums.modules.affiliate.vo.RefereeMonthCommissionVo;
import com.upedge.ums.modules.affiliate.vo.WithdrawalVo;
import com.upedge.ums.modules.user.dao.UserInfoDao;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    AccountService accountService;

    @Autowired
    UserInfoDao userInfoMapper;

    @Autowired
    AffiliateCodeRecordService affiliateCodeRecordService;

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
    public BaseResponse disableAffiliateRebate(DisableAffiliateRebateRequest request, Session session) {
        Long refereeId = request.getRefereeId();
        Affiliate affiliate = affiliateDao.queryAffiliateByReferee(refereeId);
        if (affiliate == null){
            return BaseResponse.failed();
        }
        if (!affiliate.getRebateState()){
            return BaseResponse.success();
        }
        redisTemplate.opsForHash().delete(RedisKey.HASH_AFFILIATE_REFEREE,refereeId.toString());
        affiliateDao.updateRebateStateByRefereeId(refereeId,false);
        return BaseResponse.success();
    }

    @Override
    public BaseResponse enableAffiliateRebate(DisableAffiliateRebateRequest request, Session session) {
        Long refereeId = request.getRefereeId();
        Affiliate affiliate = affiliateDao.queryAffiliateByReferee(refereeId);
        if (affiliate == null){
            return BaseResponse.failed();
        }
        AffiliateVo affiliateVo = new AffiliateVo();
        BeanUtils.copyProperties(affiliate,affiliateVo);
        redisTemplate.opsForHash().put(RedisKey.HASH_AFFILIATE_REFEREE,refereeId.toString(),affiliateVo);
        affiliateDao.updateRebateStateByRefereeId(refereeId,true);
        return BaseResponse.success();
    }

    @Override
    public BigDecimal selectTotalByReferrerId(Long customerId) {
        return affiliateDao.selectTotalByReferrerId(customerId);
    }

    @Override
    public String customerReferrerToken(Long customerId) {
        AffiliateCodeRecord affiliateCodeRecord = affiliateCodeRecordService.selectCustomerLatestAffiliateCode(customerId);
        return affiliateCodeRecord.getAffiliateCode();
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
    public void affiliateBind(String referrerToken,Long refereeId) {
        if (StringUtils.isBlank(referrerToken)
        || null == refereeId){
            return;
        }
        AffiliateCodeRecord affiliateCodeRecord = affiliateCodeRecordService.selectByPrimaryKey(referrerToken);

        if(null == affiliateCodeRecord){
            return;
        }

        AffiliateAddRequest request = new AffiliateAddRequest();
        request.setRefereeId(refereeId);
        request.setReferrerId(affiliateCodeRecord.getCustomerId());
        request.setSource(0);
        addAffiliate(request);



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

        AffiliateCommissionRecord commissionRecord = affiliateCommissionRecordDao.selectByOrderIdAndState(commissionRecordVo.getOrderId(),AffiliateCommissionRecord.RECEIVE_REBATE);
        if (null != commissionRecord){
            return;
        }

        commissionRecord = toAffiliateCommissionRecord(commissionRecordVo);

        affiliateCommissionRecordDao.insert(commissionRecord);

        if(AffiliateCommissionRecord.RECEIVE_REBATE != commissionRecord.getState()){
//            affiliateDao.updateRefereeCommission(commissionRecord.getRefereeId(),commissionRecord.getCommission().multiply(new BigDecimal("-1")));
        }else {
            Account account = accountService.selectCustomerDefaultAccount(commissionRecordVo.getReferrerId());
            affiliateDao.updateRefereeCommission(commissionRecord.getRefereeId(),commissionRecord.getCommission());
            accountService.addAccountAffiliateRebate(account.getId(),commissionRecord.getCommission());
        }
    }

    public AffiliateCommissionRecord toAffiliateCommissionRecord(CommissionRecordVo commissionRecordVo){
        AffiliateCommissionRecord affiliateCommissionRecord=new AffiliateCommissionRecord();
        BeanUtils.copyProperties(commissionRecordVo,affiliateCommissionRecord);
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
    public Affiliate selectAffiliateVoByRefereeId(Long customerId) {
        return affiliateDao.selectAffiliateVoByRefereeId(customerId);
    }

    @Override
    public AffiliateAddResponse addAffiliate(AffiliateAddRequest request) {
        if(request.getReferrerId().equals(request.getRefereeId())){
            return new AffiliateAddResponse(ResultCode.FAIL_CODE,"推荐人和被推荐人相同！");
        }
        //检查被推荐人是否已经加入了联盟
        Affiliate a=queryAffiliateByReferee(request.getRefereeId());
        if(a!=null){
            return new AffiliateAddResponse(ResultCode.SUCCESS_CODE,"被推荐人已经加入了联盟！");
        }
        Affiliate affiliate=request.toAffiliate();
        if (affiliate.getRefereeCommission() == null){
            affiliate.setRefereeCommission(new BigDecimal("0.2"));
        }
        insert(affiliate);

        AffiliateVo affiliateVo = new AffiliateVo();
        BeanUtils.copyProperties(affiliate,affiliateVo);
        redisTemplate.opsForHash().put(RedisKey.HASH_AFFILIATE_REFEREE,affiliateVo.getRefereeId().toString(),affiliateVo);

        return new AffiliateAddResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
    }

    @Override
    public List<Affiliate> allAffiliates() {
        return affiliateDao.allAffiliates();
    }


}