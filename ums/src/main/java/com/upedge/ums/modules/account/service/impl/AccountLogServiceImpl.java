package com.upedge.ums.modules.account.service.impl;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.base.Page;
import com.upedge.common.constant.Constant;
import com.upedge.common.constant.ResultCode;
import com.upedge.common.enums.TransactionConstant;
import com.upedge.common.model.order.TransactionDetail;
import com.upedge.common.model.user.request.OrderAccountLogRequest;
import com.upedge.common.model.user.vo.OrderAccountLogVo;
import com.upedge.ums.modules.account.dao.AccountLogDao;
import com.upedge.ums.modules.account.dao.RechargeRecordDao;
import com.upedge.ums.modules.account.dao.RefundRecordDao;
import com.upedge.ums.modules.account.entity.AccountLog;
import com.upedge.ums.modules.account.request.AccountLogListRequest;
import com.upedge.ums.modules.account.service.AccountLogService;
import com.upedge.ums.modules.account.vo.AccountLogVo;
import com.upedge.ums.modules.account.vo.RecordVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class AccountLogServiceImpl implements AccountLogService {

    @Autowired
    private AccountLogDao accountLogDao;
    @Autowired
    private RechargeRecordDao rechargeRecordDao;
    @Autowired
    private RefundRecordDao refundRecordDao;

    @Autowired
    RedisTemplate<String, Object> redisTemplate;


    /**
     *
     */
    @Override
    @Transactional
    public int deleteByPrimaryKey(Long id) {
        AccountLog record = new AccountLog();
        record.setId(id);
        return accountLogDao.deleteByPrimaryKey(record);
    }

    /**
     *
     */
    @Override
    @Transactional
    public int insert(AccountLog record) {
        return accountLogDao.insert(record);
    }

    /**
     *
     */
    @Override
    @Transactional
    public int insertSelective(AccountLog record) {
        return accountLogDao.insert(record);
    }

    /**
     *
     */
    @Override
    public AccountLog selectByPrimaryKey(Long id){
        AccountLog record = new AccountLog();
        record.setId(id);
        return accountLogDao.selectByPrimaryKey(record);
    }

    /**
    *
    */
    @Override
    @Transactional
    public int updateByPrimaryKeySelective(AccountLog record) {
        return accountLogDao.updateByPrimaryKeySelective(record);
    }

    /**
    *
    */
    @Override
    @Transactional
    public int updateByPrimaryKey(AccountLog record) {
        return accountLogDao.updateByPrimaryKey(record);
    }

    /**
    *
    */
    @Override
    public List<AccountLog> select(Page<AccountLog> record){
        record.initFromNum();

        return accountLogDao.select(record);
    }

    /**
    *
    */
    @Override
    public long count(Page<AccountLog> record){


        return accountLogDao.count(record);
    }

    /**
     * 订单流水详情
     * @param transactionId
     * @return
     */
    @Override
    public BaseResponse orderInfoAccountFlow(Long transactionId) {
        List<AccountLog> accountLogList=accountLogDao.listAccountLogByTransactionId(transactionId);
        Map<String, Object> map=new HashMap<>();
        map.put("orderType",null);
        if(accountLogList!=null&&accountLogList.size()>0){
            AccountLog accountLog=accountLogList.get(0);
            if(accountLog!=null){
                map.put("orderType",accountLog.getOrderType());
            }
        }
        List<AccountLogVo> accountLogVoList=new ArrayList<>();
        accountLogList.forEach(accountLog -> {
            AccountLogVo accountLogVo=new AccountLogVo();
            String transactionDesc=TransactionConstant.transactionDesc(accountLog.getTransactionType(),accountLog.getOrderType(),accountLog.getPayMethod());
            accountLogVo.setTransactionDesc(transactionDesc);
            BeanUtils.copyProperties(accountLog,accountLogVo);
            List<RecordVo> recordVoList=new ArrayList<>();
            //支付
            if(accountLog.getTransactionType()==0){
                recordVoList=rechargeRecordDao.listRechargeRecordByOrderId(transactionId);
            }
            //退款
            if(accountLog.getTransactionType()==1){
                recordVoList=refundRecordDao.listRefundRecordByOrderId(transactionId);
            }
            accountLogVo.setRecordList(recordVoList);
            accountLogVoList.add(accountLogVo);
        });
        map.put("accountLogList",accountLogVoList);
        map.put("orderId",transactionId);
        return new BaseResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS,map);
    }

    /**
     * 订单支付日志信息
     * @param transactionId
     * @return
     */
    @Override
    public BaseResponse accountLogPayInfo(String transactionId) {
        //交易类型 transaction_type  支付/扣款 = 0，退款/收款 = 1，还款 = 2
        log.error("订单支付日志信息:{}",transactionId);
        AccountLog accountLog= accountLogDao.selectPayedAccountLogByTransactionId(Long.parseLong(transactionId),0);
        return new BaseResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS,accountLog.getRebate());
    }

    @Override
    public List<AccountLog> selectAllByTime(AccountLogListRequest request) {
        request.setPageNum(null);
        request.setPageSize(null);
        return accountLogDao.select(request);
    }

    @Override
    public OrderAccountLogVo selectAccountLogByOrder(OrderAccountLogRequest accountLogQuery) {
        OrderAccountLogVo orderAccountLogVo = new OrderAccountLogVo();
        for (Long transactionId : accountLogQuery.getTransactionIds()) {
            OrderAccountLogVo date = accountLogDao.selectAccountLogByOrder(accountLogQuery.getOrderType(), transactionId);
            orderAccountLogVo.setBalance(orderAccountLogVo.getBalance().add(date.getBalance()));
            orderAccountLogVo.setCredit(orderAccountLogVo.getCredit().add(date.getCredit()));
            orderAccountLogVo.setRebate(orderAccountLogVo.getRebate().add(date.getRebate()));
        }
        return   orderAccountLogVo;
    }

    @Override
    public OrderAccountLogVo accountLogPayInfoByTransactionDetail(TransactionDetail transactionDetail) {
        return   accountLogDao.selectAccountLogPayInfoByTransactionDetail(transactionDetail.getOrderId(),transactionDetail.getOrderType(),0);
    }
}