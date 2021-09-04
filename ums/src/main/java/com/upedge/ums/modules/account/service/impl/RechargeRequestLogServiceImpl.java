package com.upedge.ums.modules.account.service.impl;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.constant.Constant;
import com.upedge.common.constant.ResultCode;
import com.upedge.common.constant.key.RedisKey;
import com.upedge.common.model.user.vo.Session;
import com.upedge.common.web.util.RedisUtil;
import com.upedge.ums.modules.account.request.RejectRechargeRequest;
import com.upedge.ums.modules.account.request.TransferRechargeRequest;
import com.upedge.ums.modules.account.service.AccountService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.upedge.common.base.Page;
import org.springframework.beans.factory.annotation.Autowired;
import com.upedge.ums.modules.account.dao.RechargeRequestLogDao;
import com.upedge.ums.modules.account.entity.RechargeRequestLog;
import com.upedge.ums.modules.account.service.RechargeRequestLogService;


@Service
public class RechargeRequestLogServiceImpl implements RechargeRequestLogService {

    @Autowired
    private RechargeRequestLogDao rechargeRequestLogDao;


    @Autowired
    AccountService accountService;

    @Autowired
    RedisTemplate redisTemplate;


    /**
     *
     */
    @Transactional
    public int deleteByPrimaryKey(Long id) {
        RechargeRequestLog record = new RechargeRequestLog();
        record.setId(id);
        return rechargeRequestLogDao.deleteByPrimaryKey(record);
    }

    /**
     *
     */
    @Transactional
    public int insert(RechargeRequestLog record) {
        return rechargeRequestLogDao.insert(record);
    }

    /**
     *
     */
    @Transactional
    public int insertSelective(RechargeRequestLog record) {
        return rechargeRequestLogDao.insert(record);
    }


    @Override
    public BaseResponse transferRechargeRequest(TransferRechargeRequest rechargeRequest, Session session) {
        RechargeRequestLog log = rechargeRequest.toRechargeRequestLog(session);
        rechargeRequestLogDao.insert(log);
        return new BaseResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS);
    }

    @Override
    @Transactional
    public BaseResponse confirmRechargeRequest(Long id, Session session) {
        String key = RedisKey.KEY_RECHARGE_REQUEST_PROCESS + id;
        boolean i = RedisUtil.lock(redisTemplate, key, 5L, 1000L);
        RechargeRequestLog log = selectByPrimaryKey(id);
        if (null == log) {
            RedisUtil.unLock(redisTemplate, key);
            return BaseResponse.failed();
        }
        if (!log.getStatus().equals(RechargeRequestLog.PENDING)
                || !log.getRechargeType().equals(RechargeRequestLog.TRANSFER_REQUEST_TYPE)) {
            RedisUtil.unLock(redisTemplate, key);
            return BaseResponse.failed();
        }
        Long accountId = log.getAccountId();
        BigDecimal amount = log.getAmount();
        log = new RechargeRequestLog();
        log.setStatus(RechargeRequestLog.SUCCESS);
        log.setId(id);
        log.setUpdateTime(new Date());
        rechargeRequestLogDao.updateByPrimaryKeySelective(log);
        accountService.addAccountBalance(accountId, amount);
        return BaseResponse.success();
    }

    @Override
    public BaseResponse rejectRechargeRequest(RejectRechargeRequest request, Session session) {
        String key = RedisKey.KEY_RECHARGE_REQUEST_PROCESS + request.getId();
        Long id = request.getId();
        boolean i = RedisUtil.lock(redisTemplate, key, 5L, 1000L);
        if (i) {
            RechargeRequestLog log = selectByPrimaryKey(id);
            if (null == log) {
                RedisUtil.unLock(redisTemplate, key);
                return BaseResponse.failed();
            }
            if (!log.getStatus().equals(RechargeRequestLog.PENDING)
                    || !log.getRechargeType().equals(RechargeRequestLog.TRANSFER_REQUEST_TYPE)) {
                RedisUtil.unLock(redisTemplate, key);
                return BaseResponse.failed();
            }
            log = new RechargeRequestLog();
            log.setId(id);
            log.setStatus(RechargeRequestLog.FAILED);
            log.setUpdateTime(new Date());
            rechargeRequestLogDao.updateByPrimaryKeySelective(log);
            RedisUtil.unLock(redisTemplate, key);
            return BaseResponse.success();
        }
        return BaseResponse.failed();
    }

    /**
     *
     */
    public RechargeRequestLog selectByPrimaryKey(Long id) {
        RechargeRequestLog record = new RechargeRequestLog();
        record.setId(id);
        return rechargeRequestLogDao.selectByPrimaryKey(record);
    }

    /**
     *
     */
    @Transactional
    public int updateByPrimaryKeySelective(RechargeRequestLog record) {
        return rechargeRequestLogDao.updateByPrimaryKeySelective(record);
    }

    /**
     *
     */
    @Transactional
    public int updateByPrimaryKey(RechargeRequestLog record) {
        return rechargeRequestLogDao.updateByPrimaryKey(record);
    }

    /**
     *
     */
    public List<RechargeRequestLog> select(Page<RechargeRequestLog> record) {
        record.initFromNum();
        return rechargeRequestLogDao.select(record);
    }

    /**
     *
     */
    public long count(Page<RechargeRequestLog> record) {
        return rechargeRequestLogDao.count(record);
    }

}