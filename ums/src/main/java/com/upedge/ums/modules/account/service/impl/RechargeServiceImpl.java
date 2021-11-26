package com.upedge.ums.modules.account.service.impl;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.config.HostConfig;
import com.upedge.common.constant.Constant;
import com.upedge.common.constant.PayOrderMethod;
import com.upedge.common.constant.ResultCode;
import com.upedge.common.enums.TransactionType;
import com.upedge.common.exception.CustomerException;
import com.upedge.common.model.account.PaypalOrder;
import com.upedge.common.model.account.PaypalPayment;
import com.upedge.common.model.user.vo.Session;
import com.upedge.common.utils.FileUtil;
import com.upedge.common.utils.IdGenerate;
import com.upedge.common.web.util.UserUtil;
import com.upedge.ums.modules.account.dao.*;
import com.upedge.ums.modules.account.entity.*;
import com.upedge.ums.modules.account.entity.payoneer.PayoneerPayment;
import com.upedge.ums.modules.account.request.*;
import com.upedge.ums.modules.account.response.*;
import com.upedge.ums.modules.account.service.RechargeService;
import com.upedge.ums.modules.account.vo.RechargeHistoryListVo;
import com.upedge.ums.modules.account.vo.RechargeRequestLogVo;
import com.upedge.ums.modules.user.dao.UserInfoDao;
import com.upedge.ums.modules.user.dto.UserInfoDto;
import jodd.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * @author 海桐
 */
@Slf4j
@Service
public class RechargeServiceImpl implements RechargeService {

    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    @Autowired
    RechargeLogMapper rechargeLogMapper;

    @Autowired
    RechargeRequestAttrMapper rechargeRequestAttrMapper;

    @Autowired
    RechargeRequestLogMapper rechargeRequestLogMapper;

    @Autowired
    RechargeRecordDao rechargeRecordDao;

    @Autowired
    AccountMapper accountMapper;

    @Autowired
    RechargeBenefitsMapper rechargeBenefitsMapper;

    @Autowired
    AccountLogDao accountLogDao;

    @Autowired
    PayoneerPaymentDao payoneerPaymentDao;

    @Autowired
    UserInfoDao UserInfoDao;

    @Transactional
    @Override
    public ApplyRechargeResponse addRechargeRequest(ApplyRechargeRequest request) {
        Session session = UserUtil.getSession(redisTemplate);

        RechargeRequestLog requestLog = request.toRechargeRequest(session);

        BigDecimal benefits = rechargeBenefitsMapper.selectBenefitsByAppAndAmount(session.getApplicationId(), requestLog.getAmount());

        if (null == benefits) {
            benefits = BigDecimal.ZERO;
        }

        requestLog.setBenefits(benefits);

        rechargeRequestLogMapper.insert(requestLog);

        return new ApplyRechargeResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS);
    }

    @Override
    public ApplyRechargeListResponse rechargeRequestList(ApplyRechargeListRequest request) {
        if (null == request.getT()) {
            request.setT(new RechargeRequestLog());
        }
        request.initFromNum();
        RechargeRequestLog rechargeRequestLog = request.getT();
//        //0申请中
//        rechargeRequestLog.setStatus(0);
        List<RechargeRequestLog> requestLogs = rechargeRequestLogMapper.selectRechargeRequestList(request);
        Long total = rechargeRequestLogMapper.getRechargeRequestTotal(request);
        request.setTotal(total);
        List<RechargeRequestLogVo> rechargeRequestLogVoList = new ArrayList<>();
        requestLogs.forEach(log -> {
            RechargeRequestLogVo rechargeRequestLogVo = new RechargeRequestLogVo();
            BeanUtils.copyProperties(log, rechargeRequestLogVo);
            UserInfoDto userInfoDto = new UserInfoDto();
            rechargeRequestLogVo.setLoginName(userInfoDto.getLoginName());
            rechargeRequestLogVo.setUsername(userInfoDto.getUsername());
            List<RechargeRequestAttr> attrList = rechargeRequestAttrMapper.listAttrByRechargeRequestId(log.getId());
            rechargeRequestLogVo.setAttrs(attrList);
            rechargeRequestLogVoList.add(rechargeRequestLogVo);
        });
        return new ApplyRechargeListResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS, rechargeRequestLogVoList, request);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public RechargeRequestLog savePaypalRechargeRequest(PaypalOrder order) {
        RechargeRequestLog log = new RechargeRequestLog();
        log.setId(order.getId());
        log.setRequestUserId(order.getSession().getId());
        log.setAccountId(order.getAccountId());
        log.setCustomerId(order.getSession().getCustomerId());
        log.setAmount(order.getAmount());
        log.setCustomerMoney(order.getAmount());
        log.setStatus(0);
        log.setCreateTime(new Date());
        log.setUpdateTime(new Date());
        log.setRechargeType(1);
        BigDecimal benefits = rechargeBenefitsMapper.selectBenefitsByAppAndAmount(order.getSession().getApplicationId(), order.getAmount());
        if (null == benefits) {
            benefits = BigDecimal.ZERO;
        }
        log.setBenefits(benefits);
        rechargeRequestLogMapper.insert(log);

        RechargeRequestAttr attr = new RechargeRequestAttr();
        attr.setAttrName("paymentId");
        attr.setAttrValue(order.getId() + "");
        attr.setRechargeRequestId(log.getId());
        rechargeRequestAttrMapper.insert(attr);
        return log;
    }

    @Transactional
    @Override
    public ApplyRechargeResponse createTransferRequest(TransferRechargeRequest request) {
        RechargeRequestLog requestLog = saveRequestLog(request);
        requestLog.setRechargeType(0);
        if (request.getAttr() == null) {
            throw new NullPointerException("image can't be null!");
        }
        return new ApplyRechargeResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS);
    }

    @Override
    public RechargeListResponse rechargeList(RechargeListRequest request) {
        Session session = UserUtil.getSession(redisTemplate);
        List<RechargeLog> rechargeLogs = rechargeLogMapper.selectRechargeList(request);
        Long total = rechargeLogMapper.getRechargeCount(request);
        request.setTotal(total);
        return new RechargeListResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS, rechargeLogs, request);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ApplyRechargeConfirmResponse confirmRechargeRequest(Long requestId, Session session) throws CustomerException {
        RechargeRequestLog requestLog = rechargeRequestLogMapper.selectByPrimaryKey(requestId);
        if (null == requestLog || requestLog.getStatus() != 0) {
            return new ApplyRechargeConfirmResponse(ResultCode.FAIL_CODE, Constant.MESSAGE_FAIL);
        }
        rechargeConfirm(requestLog, 0, PayOrderMethod.RECHARGE);
        log.debug(requestId + "--->修改状态为已完成");
        requestLog.setStatus(1);
        requestLog.setUpdateTime(new Date());
        requestLog.setHandleUserId(session.getId());
        int res = rechargeRequestLogMapper.confirmRechargeRequest(requestLog);
        if (res == 0) {
            throw new CustomerException(ResultCode.FAIL_CODE, "确认失败!");
        }
        return new ApplyRechargeConfirmResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ApplyRechargeUpdateResponse updateRechargeRequest(Long requestId, ApplyRechargeUpdateRequest request, Session session) {
        RechargeRequestLog requestLog = rechargeRequestLogMapper.selectByPrimaryKey(requestId);
        if (null == requestLog || requestLog.getStatus() != 0) {
            return new ApplyRechargeUpdateResponse(ResultCode.FAIL_CODE, "状态异常");
        }
        requestLog.setHandleUserId(session.getId());
        requestLog.setUpdateTime(new Date());
        requestLog.setAmount(request.getAmount());
        requestLog.setTransferFlow(request.getTransferFlow());
        int res = rechargeRequestLogMapper.updateRechargeRequest(requestLog);
        if (res == 0) {
            return new ApplyRechargeUpdateResponse(ResultCode.FAIL_CODE, "更新失败!");
        }
        rechargeRequestAttrMapper.updateRequestAttr(requestId, "receivingAccount", request.getReceivingAccount());
        return new ApplyRechargeUpdateResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS);
    }

    /**
     * 充值历史分页
     *
     * @param request
     * @return
     */
    @Override
    public BaseResponse rechargeHistoryList(RechargeHistoryListRequest request) {
        List<RechargeHistoryListVo> resultList =   rechargeRequestLogMapper.rechargeHistoryList(request);
        Long resultCount = rechargeRequestLogMapper.rechargeHistoryListCount(request);
        request.setTotal(resultCount);
        return BaseResponse.success(resultList,request);
    }

    @Override
    public RechargeRequestLog selectMaxNewRechargeRequestLog(Long customerId) {

        return   rechargeRequestLogMapper.selectMaxNewRechargeRequestLog(customerId);
    }

    @Override
    public RechargeRequestLog selectRechargeRequestLogByCustomerIdAndTime(Long customerId, Date createTime) {
        return rechargeRequestLogMapper.selectRechargeRequestLogByCustomerIdAndTime(customerId,createTime);
    }

    @Override
    public void updateZoneByNull() {
        rechargeRequestLogMapper.updateZoneByNull();
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public ApplyRechargeCancelResponse cancelRechargeRequest(Long requestId, ApplyRechargeCancelRequest request, Session session) {
        RechargeRequestLog requestLog = rechargeRequestLogMapper.selectByPrimaryKey(requestId);
        if (null == requestLog || requestLog.getStatus() != 0) {
            return new ApplyRechargeCancelResponse(ResultCode.FAIL_CODE, Constant.MESSAGE_FAIL);
        }
        requestLog.setId(requestId);
        requestLog.setHandleUserId(session.getId());
        requestLog.setStatus(2);
        requestLog.setUpdateTime(new Date());
        int res = rechargeRequestLogMapper.cancelRechargeRequest(requestLog);
        if (res == 0) {
            return new ApplyRechargeCancelResponse(ResultCode.FAIL_CODE, "驳回失败!");
        }
        rechargeRequestAttrMapper.updateRequestAttr(requestId, "rejectReason", request.getRejectReason());
        return new ApplyRechargeCancelResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS);
    }

    @Transactional
    @Override
    public RechargeRevokeResponse revokeRecharge(Long rechargeId) {
        RechargeLog rechargeLog = rechargeLogMapper.selectByPrimaryKey(rechargeId);
        if (null == rechargeLog || rechargeLog.getRechargeStatus() > 1) {
            return new RechargeRevokeResponse(ResultCode.FAIL_CODE, Constant.MESSAGE_FAIL);
        }
        rechargeLogMapper.revokeRecharge(rechargeId);
        accountMapper.deductAccountBalance(rechargeLog);
        return new RechargeRevokeResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS);
    }

    @Transactional
    @Override
    public PayoneerRechargeResponse payoneerRecharge(PayoneerPayment payment) {

        RechargeRequestLog requestLog = rechargeRequestLogMapper.selectByPrimaryKey(Long.parseLong(payment.getClientReferenceId()));

        if (payment.getStatus() == 2) {
            requestLog.setStatus(1);
            requestLog.setUpdateTime(new Date());
            rechargeRequestLogMapper.updateByPrimaryKey(requestLog);
            rechargeConfirm(requestLog, 0, PayOrderMethod.PAYONEER);
        }
        payment.setUpdateTime(new Date());
        payoneerPaymentDao.updateByPrimaryKeySelective(payment);
        return new PayoneerRechargeResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS);
    }

    @Transactional
    @Override
    public ApplyRechargeResponse paypalRecharge(PaypalPayment payment) {
        Integer rechargeType = 0;
        if (payment.getOrderType() != 0) {
            rechargeType = 1;
        }
        RechargeRequestAttr attr = rechargeRequestAttrMapper.selectByValueAndName("paymentId", payment.getId() + "");
        if (attr == null) {
            return new ApplyRechargeResponse(ResultCode.FAIL_CODE, Constant.MESSAGE_SUCCESS);
        }
        RechargeRequestLog requestLog = rechargeRequestLogMapper.selectByPrimaryKey(attr.getRechargeRequestId());
        if (payment.getState().equals("completed")) {
            requestLog.setStatus(1);
            requestLog.setUpdateTime(new Date());
            rechargeRequestLogMapper.updateByPrimaryKey(requestLog);
            rechargeConfirm(requestLog, rechargeType, PayOrderMethod.PAYPAL);
        }

        return new ApplyRechargeResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS);
    }

    @Override
    public ApplyRechargeListResponse rechargeRequestHistory(ApplyRechargeListRequest request) {
        if (null == request.getT()) {
            request.setT(new RechargeRequestLog());
        }
        request.initFromNum();
        RechargeRequestLog rechargeRequestLog = request.getT();
        //1=成功，2=失败
        rechargeRequestLog.setGteStatus(1);
        List<RechargeRequestLog> requestLogs = rechargeRequestLogMapper.selectRechargeRequestList(request);
        Long total = rechargeRequestLogMapper.getRechargeRequestTotal(request);
        request.setTotal(total);
        List<RechargeRequestLogVo> rechargeRequestLogVoList = new ArrayList<>();
        requestLogs.forEach(log -> {
            RechargeRequestLogVo rechargeRequestLogVo = new RechargeRequestLogVo();
            BeanUtils.copyProperties(log, rechargeRequestLogVo);
            rechargeRequestLogVo.setAdminUserId(log.getHandleUserId());
            UserInfoDto userInfoDto = new UserInfoDto();
            rechargeRequestLogVo.setLoginName(userInfoDto.getLoginName());
            rechargeRequestLogVo.setUsername(userInfoDto.getUsername());
            List<RechargeRequestAttr> attrList = rechargeRequestAttrMapper.listAttrByRechargeRequestId(log.getId());
            rechargeRequestLogVo.setAttrs(attrList);
            rechargeRequestLogVoList.add(rechargeRequestLogVo);
        });
        return new ApplyRechargeListResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS, rechargeRequestLogVoList, request);
    }


    public RechargeRequestLog saveRequestLog(TransferRechargeRequest request) {

        Session session = UserUtil.getSession(redisTemplate);
        Long applicationId = UserUtil.getSession(redisTemplate).getApplicationId();

        RechargeRequestLog requestLog = request.toRechargeRequest(session);
        String image = request.getAttr().getImage();
        if (StringUtil.isBlank(image)){
            image = FileUtil.uploadImage(image, HostConfig.HOST +"/image/transfer/","/root/files/image/transfer/");
            requestLog.setTransferFlow(image);
        }
        BigDecimal benefits = rechargeBenefitsMapper.selectBenefitsByAppAndAmount(applicationId, requestLog.getAmount());

        if (null == benefits) {
            benefits = BigDecimal.ZERO;
        }
        requestLog.setRechargeType(0);
        requestLog.setBenefits(benefits);
        requestLog.setId(IdGenerate.nextId());
        rechargeRequestLogMapper.insert(requestLog);
        return requestLog;
    }

    @Transactional(rollbackFor = Exception.class)
    public RechargeLog rechargeConfirm(RechargeRequestLog requestLog, Integer rechargeType, Integer rechargeMethod) {
        RechargeLog r = requestLog.toRechargeLog();
        r.setRechargeType(rechargeType);
        BigDecimal amount = r.getAmount();
        BigDecimal rebate = r.getRebate();

        Account account = accountMapper.selectByPrimaryKey(requestLog.getAccountId());
        BigDecimal payed = BigDecimal.ZERO;

        r.setId(requestLog.getId());

        rechargeLogMapper.insert(r);

        AccountLog rechargeLog = saveRechargeAccountLog(r, rechargeMethod, account.getCustomerId());
        accountLogDao.insert(rechargeLog);
        accountMapper.accountIncreaseBalance(account.getId(), amount, rebate, payed);

        return rechargeRepayment(r);
    }

    public RechargeLog rechargeRepayment(RechargeLog rechargeLog) {
        Account account = accountMapper.selectByPrimaryKey(rechargeLog.getAccountId());
        BigDecimal credit = account.getCredit();
        if (credit.compareTo(BigDecimal.ZERO) == 0) {
            return rechargeLog;
        }
        BigDecimal payed = BigDecimal.ZERO;
        AccountLog repaymentLog = new AccountLog();
        repaymentLog.setAccountId(account.getId());
        repaymentLog.setCustomerId(account.getCustomerId());
        repaymentLog.setCreateTime(new Date());
        repaymentLog.setCredit(BigDecimal.ZERO);
        repaymentLog.setRebate(BigDecimal.ZERO);
        repaymentLog.setFixFee(BigDecimal.ZERO);
        TransactionType transactionType = null;
        switch (rechargeLog.getRechargeType()) {
            case PayOrderMethod
                    .RECHARGE:
                transactionType = TransactionType.TRANSFER_RECHARGE_REPAYMENT;
                break;
            case PayOrderMethod.PAYPAL:
                transactionType = TransactionType.PAYPAY_RECHARGE_REPAYMENT;
                break;
            case PayOrderMethod.PAYONEER:
                transactionType = TransactionType.PAYONEER_RECHARGE_REPAYMENT;
                break;
            default:
                return rechargeLog;
        }
        repaymentLog.setOrderType(transactionType.getOrderType());
        repaymentLog.setPayMethod(transactionType.getPayMethod());
        repaymentLog.setTransactionType(transactionType.getTransactionType());
        BigDecimal negOne = new BigDecimal("-1");
        BigDecimal amount = rechargeLog.getAmount();
        BigDecimal repaymentAmount = BigDecimal.ZERO;
        if (credit.compareTo(amount) > -1) {
            payed = amount;
            repaymentAmount = repaymentAmount.add(amount);
            credit = credit.subtract(amount);
            amount = BigDecimal.ZERO;
            if (rechargeLog.getRebate().compareTo(BigDecimal.ZERO) == 0) {
                rechargeLog.setRechargeStatus(2);
            } else {
                rechargeLog.setRechargeStatus(1);
            }
        } else {
            payed = credit;
            amount = amount.subtract(credit);
            repaymentAmount = repaymentAmount.add(credit);
            credit = BigDecimal.ZERO;
            rechargeLog.setRechargeStatus(1);
        }
        account.setCredit(credit);
        repaymentLog.setBalance(payed.multiply(negOne));
        rechargeLog.setAmount(amount);
        rechargeLog.setUpdateTime(new Date());
        rechargeRequestLogMapper.updateRepaymentAmountById(repaymentAmount, rechargeLog.getId());
        accountMapper.accountRepaymentCredit(account.getId(), payed);
        rechargeLogMapper.updateByPrimaryKeySelective(rechargeLog);
        return rechargeLog;
    }

    public AccountLog saveRechargeAccountLog(RechargeLog rechargeLog, Integer rechargeMethod, Long customerId) {
        AccountLog accountLog = new AccountLog();
        accountLog.setAccountId(rechargeLog.getAccountId());
        accountLog.setBalance(rechargeLog.getAmount());
        accountLog.setRebate(rechargeLog.getRebate());
        TransactionType transactionType = null;
        switch (rechargeLog.getRechargeType()) {
            case PayOrderMethod
                    .RECHARGE:
                transactionType = TransactionType.TRANSFER_RECHARGE;
                break;
            case PayOrderMethod.PAYPAL:
                transactionType = TransactionType.PAYPAY_RECHARGE;
                break;
            case PayOrderMethod.PAYONEER:
                transactionType = TransactionType.PAYONEER_RECHARGE;
                break;
            default:
                return null;
        }
        accountLog.setOrderType(transactionType.getOrderType());
        accountLog.setPayMethod(transactionType.getPayMethod());
        accountLog.setTransactionType(transactionType.getTransactionType());
        accountLog.setTransactionId(rechargeLog.getId());
        accountLog.setCustomerId(customerId);
        accountLog.setCreateTime(new Date());
        return accountLog;
    }

}
