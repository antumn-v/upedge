package com.upedge.ums.modules.account.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.upedge.common.constant.PayOrderMethod;
import com.upedge.common.model.account.payoneer.DebitCreateParam;
import com.upedge.common.model.account.payoneer.DebitResponse;
import com.upedge.common.model.user.vo.Session;
import com.upedge.common.utils.IdGenerate;
import com.upedge.common.utils.ListUtils;
import com.upedge.common.web.util.UserUtil;
import com.upedge.ums.enums.PayoneerAttrEnum;
import com.upedge.ums.modules.account.dao.*;
import com.upedge.ums.modules.account.entity.AccountPayoneer;
import com.upedge.ums.modules.account.entity.Payoneer;
import com.upedge.ums.modules.account.entity.RechargeRequestLog;
import com.upedge.ums.modules.account.entity.payoneer.*;
import com.upedge.ums.modules.account.request.PayoneerRechargeRequest;
import com.upedge.ums.modules.account.service.PayoneerService;
import com.upedge.ums.modules.account.vo.PayoneerBalanceVo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

@Service
public class PayoneerServiceImpl implements PayoneerService {

    @Autowired
    AccountPayMethodAttrMapper accountPayMethodAttrMapper;

    @Autowired
    PayoneerPaymentDao payoneerPaymentDao;

    @Autowired
    RechargeRequestLogMapper rechargeRequestLogMapper;

    @Autowired
    RechargeBenefitsMapper rechargeBenefitsMapper;

    @Autowired
    PayoneerDao payoneerDao;

    @Autowired
    AccountPayoneerDao accountPayoneerDao;

    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    String partnerId;

    @Override
    public List<PayoneerBalanceVo> getAccountPayoneerUsdBalance(Long accountId) {

        List<PayoneerBalanceVo> payoneerBalanceVos = new ArrayList<>();
        List<Payoneer> payoneers = payoneerDao.selectAccountPayoneers(accountId);
        if (ListUtils.isEmpty(payoneers)) {
            return payoneerBalanceVos;
        }
        payoneers.forEach(payoneer -> {
            Map<String, String> param = new HashMap<>();
            param.put("token", payoneer.getAccessToken());
            param.put("accountId", payoneer.getPayonneerAccountId());

            PayoneerBalanceVo payoneerBalanceVo = new PayoneerBalanceVo();
            payoneerBalanceVo.setPayoneerAccountId(payoneer.getPayonneerAccountId());
            payoneerBalanceVo.setUsdBalance("0");
            payoneerBalanceVo.setPayoneerId(payoneer.getId());
            JSONObject jsonObject = new JSONObject();
            if (jsonObject.getBoolean("success")) {
                JSONArray array = jsonObject.getJSONObject("result").getJSONObject("balances").getJSONArray("items");
                for (int i = 0; i < array.size(); i++) {
                    JSONObject object = array.getJSONObject(i);
                    if (object.getString("currency").equals("USD")) {
                        payoneerBalanceVo.setBalanceId(object.getString("id"));
                        payoneerBalanceVo.setUsdBalance(object.getString("available_balance"));
                    }
                }
            }

            payoneerBalanceVos.add(payoneerBalanceVo);
        });

        return payoneerBalanceVos;
    }

    @Override
    public int insertByBatch(List<PayoneerPayment> list) {
        return payoneerPaymentDao.insertByBatch(list);
    }

    @Transactional
    @Override
    public Payoneer savePayoneer(PayoneerToken payoneerToken, Long accountId) {
        if (null == payoneerToken
                || null == accountId) {
            return null;
        }
        Date date = new Date();
        Payoneer payoneer = new Payoneer(payoneerToken);
        payoneer.setUpdateTime(date);

        Payoneer p = payoneerDao.selectByPayoneerAccountId(payoneer.getPayonneerAccountId());
        if (null == p) {
            payoneer.setCreateTime(date);
            payoneerDao.insert(payoneer);
        } else {
            payoneer.setId(p.getId());
            payoneerDao.updateByPrimaryKey(payoneer);
        }

        AccountPayoneer accountPayoneer = accountPayoneerDao.selectByAccountIdAndPayoneerId(accountId, payoneer.getId());
        if (null == accountPayoneer) {
            accountPayoneer = new AccountPayoneer();
            accountPayoneer.setAccountId(accountId);
            accountPayoneer.setPayoneerId(payoneer.getId());
            accountPayoneerDao.insert(accountPayoneer);
        }

        return payoneer;
    }

    @Transactional
    @Override
    public JSONObject createPayoneerPayment(PayoneerRechargeRequest request) {

        Payoneer payoneer = payoneerDao.selectByPrimaryKey(request.getPayoneerId());
        Session session = UserUtil.getSession(redisTemplate);
        Long applicationId = session.getApplicationId();

        RechargeRequestLog requestLog = request.toRechargeRequest(session);
        requestLog.setRechargeType(PayOrderMethod.PAYONEER);

        BigDecimal benefits = rechargeBenefitsMapper.selectBenefitsByAppAndAmount(applicationId, requestLog.getAmount());
        if (null == benefits) {
            benefits = BigDecimal.ZERO;
        }
        requestLog.setBenefits(benefits);
        Long id = IdGenerate.nextId();

        requestLog.setId(id);
        rechargeRequestLogMapper.insert(requestLog);
        Integer accountPaymethodId = request.getAccountPaymethodId();

        String token = payoneer.getAccessToken();

        String accountId = payoneer.getPayonneerAccountId();

        String balanceId = request.getBalanceId();
        String description = "Pay recharge order amount: " + request.getAmount() + ", Transaction ID: " + id;
        DebitCreateParam debitParam = new DebitCreateParam();
        debitParam.setAmount(request.getAmount().doubleValue());
        debitParam.setClient_reference_id(id.toString());
        debitParam.setDescription(description);
        debitParam.setTo(new DebitCreateParam.ToBean());
        debitParam.getTo().setId(partnerId);
        debitParam.setCurrency("USD");
        JSONObject jsonObject = new JSONObject();
        if (!jsonObject.getBoolean("success")){
            return null;
        }
        Date date = new Date();
        DebitResponse debitResponse = jsonObject.getJSONObject("result").toJavaObject(DebitResponse.class);
        PayoneerPayment payment = new PayoneerPayment(debitResponse);
        payment.setCreateTime(date);
        payment.setUpdateTime(date);
        payment.setAccountId(request.getAccountId());
        payment.setAccountPaymethodId(accountPaymethodId);
        payment.setRequestAmount(request.getAmount().doubleValue());
        payment.setUserId(session.getId());
        payoneerPaymentDao.insert(payment);
//        jsonObject = PayoneerApiController.commitPayment(accountId, token, payment.getCommitId());
        jsonObject.put("id", id);
        return jsonObject;

    }


    @Override
    public PayoneerPayment payoneerChanllenge(String session_id, String response_path) {
        Long id = (Long) redisTemplate.opsForValue().get(session_id);
        PayoneerPayment payment = new PayoneerPayment();
        payment.setId(id);
        payment = payoneerPaymentDao.selectByPrimaryKey(payment);
        Payoneer payoneer = payoneerDao.selectByPayoneerAccountId(payment.getPayoneerAccountId());
        JSONObject jsonObject = new JSONObject();
        if (jsonObject.getBoolean("success")) {
            DebitResponse debitResponse = jsonObject.getJSONObject("result").toJavaObject(DebitResponse.class);
            payment.setStatus(debitResponse.getStatus());
            payment.setStatusDescription(debitResponse.getStatus_description());
            return payment;
        }
        return null;
    }

    @Override
    public Map<String, String> getPayoneerAuthParam(Integer accountPaymethodId) {
        String token = accountPayMethodAttrMapper.selectAccountPaymethodAttrValueByKey(accountPaymethodId, PayoneerAttrEnum.access_token);

        String accountId = accountPayMethodAttrMapper.selectAccountPaymethodAttrValueByKey(accountPaymethodId, PayoneerAttrEnum.account_id);

        Map<String, String> param = new HashMap<>();
        param.put("token", token);
        param.put("accountId", accountId);

        return param;
    }

}
