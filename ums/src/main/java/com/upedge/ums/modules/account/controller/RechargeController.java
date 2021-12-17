package com.upedge.ums.modules.account.controller;

import com.alibaba.fastjson.JSONObject;
import com.upedge.common.base.BaseResponse;
import com.upedge.common.constant.BaseCode;
import com.upedge.common.constant.Constant;
import com.upedge.common.constant.ResultCode;
import com.upedge.common.constant.key.RedisKey;
import com.upedge.common.exception.CustomerException;
import com.upedge.common.model.account.PaypalOrder;
import com.upedge.common.model.account.PaypalPayment;
import com.upedge.common.model.account.payoneer.DebitResponse;
import com.upedge.common.model.account.request.PaypalExecuteRequest;
import com.upedge.common.model.user.vo.Session;
import com.upedge.common.utils.IdGenerate;
import com.upedge.common.web.util.RequestUtil;
import com.upedge.common.web.util.UserUtil;
import com.upedge.ums.modules.account.entity.RechargeRequestLog;
import com.upedge.ums.modules.account.entity.payoneer.PayoneerPayment;
import com.upedge.ums.modules.account.request.*;
import com.upedge.ums.modules.account.response.*;
import com.upedge.ums.modules.account.service.PayoneerService;
import com.upedge.ums.modules.account.service.PaypalService;
import com.upedge.ums.modules.account.service.RechargeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;

/**
 * @author 海桐
 */
@Api("账户充值")
@RestController
@RequestMapping("/recharge")
public class RechargeController {


    private String moudle = "recharge";

    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    @Autowired
    RechargeService rechargeService;

    @Autowired
    PayoneerService payoneerService;

    @Autowired
    PaypalService paypalService;

    String url;


    /**
     * 充值列表
     *
     * @param request
     * @return
     */
    @ApiOperation("充值列表历史")
    @PostMapping("/list")
    public BaseResponse rechargeHistoryList(@RequestBody RechargeHistoryListRequest request) {
        return rechargeService.rechargeHistoryList(request);
    }



    /**
     * 充值申请列表
     *
     * @param request
     * @return
     */
    @ApiOperation("充值申请列表")
    @PostMapping("/request/list")
    public ApplyRechargeListResponse rechargeRequestList(@RequestBody ApplyRechargeListRequest request) {
        Session session = UserUtil.getSession(redisTemplate);
        if(session.getUserType() == 2 && session.getAccountId() == null){
            return new ApplyRechargeListResponse(ResultCode.FAIL_CODE,"User does not have an account");
        }
        if(request.getT() == null){
            request.setT(new RechargeRequestLog());
        }
        if (session.getApplicationId().equals(Constant.APP_APPLICATION_ID)) {
            request.getT().setCustomerId(session.getCustomerId());
            request.getT().setAccountId(session.getAccountId());
        }
        if(session.getApplicationId().equals(Constant.ADMIN_APPLICATION_ID)){
            if(session.getUserType() != BaseCode.USER_ROLE_SUPERADMIN){
                return new ApplyRechargeListResponse(ResultCode.FAIL_CODE,"无权限");
            }
            request.getT().setStatus(0);
        }
        return rechargeService.rechargeRequestList(request);
    }

    /**
     * admin历史充值列表
     *
     * @param request
     * @return
     */
    @ApiOperation("admin历史充值列表")
    @PostMapping("/request/history")
    public ApplyRechargeListResponse rechargeRequestHistory(@RequestBody ApplyRechargeListRequest request) {
        return rechargeService.rechargeRequestHistory(request);
    }

    /**
     * 提交充值申请
     *
     * @param request
     * @return
     */
//    @ApiOperation("提交充值申请")
//    @PostMapping("/request/create")
//    public ApplyRechargeResponse createRechargeRequest(@RequestBody @Valid ApplyRechargeRequest request) {
//        return rechargeService.addRechargeRequest(request);
//    }

    @ApiOperation("银行转帐充值申请")
    @PostMapping("/request/transfer")
    public BaseResponse transferRechargeRequest(@RequestBody @Valid TransferRechargeRequest request) {
        String image = request.getAttr().getImage();
        if (StringUtils.isBlank(image) ||
                request.getAmount().compareTo(new BigDecimal("100")) == -1){
            return BaseResponse.failed();
        }
        return rechargeService.createTransferRequest(request);
    }

//    @ApiOperation("payoneer充值")
//    @PostMapping("/request/create/payoneer")
    public PayoneerRechargeResponse payoneerRechargeRequest(@RequestBody PayoneerRechargeRequest request) {
        JSONObject jsonObject = payoneerService.createPayoneerPayment(request);
        if (null == jsonObject) {
            return new PayoneerRechargeResponse(ResultCode.FAIL_CODE, "payoneer request failed");
        }

        if (jsonObject.containsKey("result")) {
            DebitResponse debitResponse = jsonObject.getJSONObject("result").toJavaObject(DebitResponse.class);
            PayoneerPayment payment = new PayoneerPayment(debitResponse);
            payment.setAccountId(request.getAccountId());
            rechargeService.payoneerRecharge(payment);
            return new PayoneerRechargeResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS);
        } else if (jsonObject.containsKey("challenge")) {
            JSONObject challenge = jsonObject.getJSONObject("challenge");
            String session_id = challenge.getString("session_id");
            String challengeUrl = challenge.getString("url");
            redisTemplate.opsForValue().set(session_id, jsonObject.getString("id"));
            return new PayoneerRechargeResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS, challengeUrl);
        }
        return new PayoneerRechargeResponse(ResultCode.FAIL_CODE, "payoneer request failed");
    }

    @ApiOperation("paypal充值")
    @PostMapping("/request/createPaypal")
    public ApplyRechargeResponse paypalRechargeRequest(@RequestBody ApplyRechargeRequest request) {
        String origin = RequestUtil.getRequest().getHeader("Origin") + "/wallet/financeblank";
        Session session = UserUtil.getSession(redisTemplate);
        if (null == request.getAccountId()) {
            request.setAccountId(session.getAccountId());
        }
        Long id = IdGenerate.nextId();
        PaypalOrder paypalOrder = new PaypalOrder();
        paypalOrder.setSession(session);
        paypalOrder.setRemarks(request.getRemarks());
        paypalOrder.setAmount(request.getAmount());
        paypalOrder.setAccountPaymethodId(request.getAccountPaymethodId());
        paypalOrder.setOrderType(0);
        paypalOrder.setAccountId(request.getAccountId());
        paypalOrder.setId(id);
        paypalOrder.setSuccessUrl(origin);
        paypalOrder.setFailedUrl(origin);
        String url = paypalService.getPaypalOrderUrl(paypalOrder);
        if (StringUtils.isBlank(url)) {
            return new ApplyRechargeResponse(ResultCode.FAIL_CODE, Constant.MESSAGE_FAIL);
        }
        return new ApplyRechargeResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS, url);
    }


    /**
     * 确认充值申请
     * @param requestId
     * @return
     */
    @ApiOperation("确认充值申请")
    @PostMapping("/request/{requestId}/confirm")
    public ApplyRechargeConfirmResponse confirmRechargeRequest(@PathVariable Long requestId) {
        Session session = UserUtil.getSession(redisTemplate);
        try {
            return rechargeService.confirmRechargeRequest(requestId, session);
        } catch (CustomerException e) {
            e.printStackTrace();
            return new ApplyRechargeConfirmResponse(ResultCode.FAIL_CODE, e.getMessage());
        }
    }

    /**
     * admin充值审核前更新到账金额
     *
     * @param requestId
     * @param request
     * @return
     */
    @ApiOperation("admin充值审核前更新到账金额")
    @PostMapping("/request/{requestId}/update")
    public ApplyRechargeUpdateResponse updateRechargeRequest(@PathVariable Long requestId, @RequestBody @Valid ApplyRechargeUpdateRequest request) {
        Session session = UserUtil.getSession(redisTemplate);
        return rechargeService.updateRechargeRequest(requestId, request, session);
    }

    /**
     * admin驳回申请
     *
     * @param requestId
     * @return
     */
    @ApiOperation("驳回申请")
    @PostMapping("/request/{requestId}/cancel")
    public ApplyRechargeCancelResponse cancelRechargeRequest(@PathVariable Long requestId, @RequestBody @Valid ApplyRechargeCancelRequest request) {
        Session session = UserUtil.getSession(redisTemplate);
        return rechargeService.cancelRechargeRequest(requestId, request, session);
    }

    /**
     * 余额充值
     * @param paymentId
     * @param payerId
     * @param token
     * @return
     */
    @GetMapping("/paypalExecute")
    public ApplyRechargeResponse paypalExecute( String paymentId, @RequestParam("PayerID") String payerId, @RequestParam("token") String token) {
        String key = RedisKey.HASH_PAYPAL_TOKEN + token;
        PaypalOrder order = (PaypalOrder) redisTemplate.opsForHash().get(key, "order");
        redisTemplate.delete(key);
        if (StringUtils.isBlank(paymentId)){
            return new ApplyRechargeResponse(ResultCode.FAIL_CODE, Constant.MESSAGE_FAIL);
        }
        if (null == order) {
            return new ApplyRechargeResponse(ResultCode.FAIL_CODE, Constant.MESSAGE_FAIL);
        }
        PaypalExecuteRequest request = new PaypalExecuteRequest(paymentId, payerId, token, order);
        PaypalPayment payment = paypalService.executePayment(request);
        if (payment == null){
            return new ApplyRechargeResponse(ResultCode.FAIL_CODE, Constant.MESSAGE_FAIL);
        }
        rechargeService.savePaypalRechargeRequest(order);
        if (payment.getState().equals("completed")) {
            rechargeService.paypalRecharge(payment);
        }
        return new ApplyRechargeResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS, order.getAmount());
    }

    /**
     * 撤销申请
     *
     * @param id
     * @return
     */
    @ApiOperation("撤销申请")
    @PostMapping("/{id}/revoke")
    public RechargeRevokeResponse revokeRechargeLog(@PathVariable Long id) {
        return rechargeService.revokeRecharge(id);
    }

}
