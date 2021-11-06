package com.upedge.ums.modules.affiliate.service.impl;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.base.Page;
import com.upedge.common.constant.key.RedisKey;
import com.upedge.common.enums.CustomerExceptionEnum;
import com.upedge.common.exception.CustomerException;
import com.upedge.common.feign.UmsFeignClient;
import com.upedge.common.model.manager.vo.ManagerInfoVo;
import com.upedge.common.model.user.request.UserInfoSelectRequest;
import com.upedge.common.model.user.vo.Session;
import com.upedge.common.utils.IdGenerate;
import com.upedge.common.web.util.RedisUtil;
import com.upedge.common.web.util.UserUtil;
import com.upedge.ums.modules.account.dao.AccountMapper;
import com.upedge.ums.modules.account.dao.AccountUserMapper;
import com.upedge.ums.modules.account.entity.Account;
import com.upedge.ums.modules.affiliate.dao.AffiliateCommissionWithdrawalDao;
import com.upedge.ums.modules.affiliate.entity.AffiliateCommissionWithdrawal;
import com.upedge.ums.modules.affiliate.request.AffiliateWithdrawalConfirmRequest;
import com.upedge.ums.modules.affiliate.request.AffiliateWithdrawalRejectRequest;
import com.upedge.ums.modules.affiliate.request.AffiliateWithdrawalRequest;
import com.upedge.ums.modules.affiliate.service.AffiliateCommissionWithdrawalService;

import com.upedge.ums.modules.user.dao.UserInfoDao;
import com.upedge.ums.modules.user.entity.UserInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


@Slf4j
@Service
public class AffiliateCommissionWithdrawalServiceImpl implements AffiliateCommissionWithdrawalService {

    @Autowired
    private AffiliateCommissionWithdrawalDao affiliateCommissionWithdrawalDao;

    @Autowired
    AccountUserMapper accountUserMapper;

    @Autowired
    AccountMapper accountMapper;

    @Autowired
    UserInfoDao userInfoMapper;

    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private UmsFeignClient umsFeignClient;

    /**
     * 佣金提现申请
     * @param request
     * @param session
     * @return
     */
    @Transactional
    @Override
    public BaseResponse affiliateWithdrawalRequest(AffiliateWithdrawalRequest request, Session session) throws CustomerException {
        // 根据账户查找  是否存在 存在则可以提出申请
        UserInfoSelectRequest userInfoSelectRequest = new UserInfoSelectRequest();
        userInfoSelectRequest.setLoginName(request.getAccount());
        BaseResponse baseResponse = umsFeignClient.userInfoSelect(userInfoSelectRequest);
        if (0 ==  baseResponse.getCode() || baseResponse.getData() == null){
            throw new CustomerException(CustomerExceptionEnum.VALIDATION_AILED_PLEASE_CHECK);
        }


        BigDecimal money = request.getMoney();
        BigDecimal oneHundred = new BigDecimal("100");
        Account account =accountMapper.selectByPrimaryKey(session.getAccountId());
        if(account.getCommission().compareTo(oneHundred) == -1
                || account.getCommission().compareTo(money) == -1 ){
            return BaseResponse.failed("Insufficient account commission");
        }
        if(money.compareTo(new BigDecimal("100")) == -1){
            return BaseResponse.failed("A single withdrawal amount is at least 100");
        }
        Long id = IdGenerate.nextId();
        AffiliateCommissionWithdrawal withdrawal =  new AffiliateCommissionWithdrawal().getAffiliateCommissionWithdrawal(session,request);
        withdrawal.setId(id);
        if(request.getType() == 0){
            UserInfo userInfo = userInfoMapper.selectByEmail(request.getAccount());
            if(userInfo == null){
                return BaseResponse.failed("User does not exist");
            }else {
                //防止用户修改邮箱造成提现错误
                Account receiveAccount = accountUserMapper.selectAccountByUser(userInfo.getId());
                withdrawal.setReceiveAccount(String.valueOf(receiveAccount.getId()));
            }
        }
        int i = accountMapper.deductAccountCommission(account.getId(),money);
        if(i == 0){
            return BaseResponse.failed("Insufficient account commission");
        }
        affiliateCommissionWithdrawalDao.insert(withdrawal);
        log.warn("佣金提现ID：{}，请求信息：{}",id,request);
        return BaseResponse.success();
    }

    @Transactional
    @Override
    public BaseResponse affiliateWithdrawalConfirm(Long withdrawalId, Session session, AffiliateWithdrawalConfirmRequest confirm) {

        String key = RedisKey.WITHDRAWAL_APPLICATION_PASSED + withdrawalId;
        boolean b = RedisUtil.lock(redisTemplate,key,5L,10*1000L);
        if(!b){
            return BaseResponse.failed();
        }
        try {

            AffiliateCommissionWithdrawal withdrawal = affiliateCommissionWithdrawalDao.selectByPrimaryKey(withdrawalId);
            if(withdrawal.getState() != 0){
                return BaseResponse.failed("该提现请求已经处理过了");
            }
            if(withdrawal.getPath() == 0){
                Account receiveAccount = accountMapper.selectByPrimaryKey(Long.parseLong(withdrawal.getReceiveAccount()));
                if(null == receiveAccount || receiveAccount.getStatus() == 0){
                    return BaseResponse.failed("收款账户不存在或已停用");
                }
                accountMapper.increaseAccountCommission(receiveAccount.getId(),withdrawal.getAmount());
            }
//            ManagerInfoVo manager = UserUtil.getManager(redisTemplate);
//            withdrawal.setManagerCode(manager.getManagerCode());

            withdrawal.setUpdateTime(new Date());
            withdrawal.setState(1);
            withdrawal.setPaymentAccount(confirm.getPaymentAccount());

            affiliateCommissionWithdrawalDao.updateByPrimaryKey(withdrawal);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }finally {
            RedisUtil.unLock(redisTemplate,key);
        }
        return BaseResponse.success();
    }

    /**
     * 拒绝提现请求。恢复佣金到初始账户
     * @param request
     * @param session
     * @return
     */
    @Transactional
    @Override
    public BaseResponse affiliateWithdrawalReject(AffiliateWithdrawalRejectRequest request,Session session) {
        AffiliateCommissionWithdrawal withdrawal = affiliateCommissionWithdrawalDao.selectByPrimaryKey(request.getWithdrawalId());
        if(null == withdrawal || withdrawal.getState() != 0){
            return BaseResponse.failed("请求信息错误或该提现请求已经被处理");
        }
        withdrawal.setReason(request.getRejectReason());
        withdrawal.setState(2);
        withdrawal.setUpdateTime(new Date());
//        ManagerInfoVo manager = UserUtil.getManager(redisTemplate);
//        withdrawal.setManagerCode(manager.getManagerCode());
        affiliateCommissionWithdrawalDao.updateByPrimaryKey(withdrawal);
        accountMapper.increaseAccountCommission(withdrawal.getWithdrawalAccountId(),withdrawal.getAmount());
        return BaseResponse.success();
    }


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

        return affiliateCommissionWithdrawalDao.selectByPrimaryKey(id);
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