package com.upedge.ums.modules.account.service;

import com.alibaba.fastjson.JSONObject;
import com.upedge.ums.modules.account.entity.Payoneer;
import com.upedge.ums.modules.account.entity.payoneer.PayoneerPayment;
import com.upedge.ums.modules.account.entity.payoneer.PayoneerToken;
import com.upedge.ums.modules.account.request.PayoneerRechargeRequest;
import com.upedge.ums.modules.account.vo.PayoneerBalanceVo;

import java.util.List;
import java.util.Map;

/**
 * @author 海桐
 */
public interface PayoneerService {

    Payoneer savePayoneer(PayoneerToken payoneerToken, Long accountId);

    JSONObject createPayoneerPayment(PayoneerRechargeRequest request);

    PayoneerPayment payoneerChanllenge(String session_id, String response_path);

    Map<String, String> getPayoneerAuthParam(Integer accountPaymethodId);

    List<PayoneerBalanceVo> getAccountPayoneerUsdBalance(Long accountId);

    int insertByBatch(List<PayoneerPayment> list);

}
