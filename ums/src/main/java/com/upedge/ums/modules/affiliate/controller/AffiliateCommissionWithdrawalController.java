package com.upedge.ums.modules.affiliate.controller;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.component.annotation.Permission;
import com.upedge.common.constant.Constant;
import com.upedge.common.constant.ResultCode;
import com.upedge.common.constant.key.RedisKey;
import com.upedge.common.exception.CustomerException;
import com.upedge.common.model.user.vo.Session;
import com.upedge.common.web.util.RedisUtil;
import com.upedge.common.web.util.UserUtil;
import com.upedge.ums.modules.affiliate.entity.AffiliateCommissionWithdrawal;
import com.upedge.ums.modules.affiliate.request.*;
import com.upedge.ums.modules.affiliate.response.*;
import com.upedge.ums.modules.affiliate.service.AffiliateCommissionWithdrawalService;
import com.upedge.ums.modules.affiliate.service.AffiliateService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 
 *
 * @author author
 */
@Api(tags = "佣金提现")
@RestController
@RequestMapping("/affiliate/withdrawal")
public class AffiliateCommissionWithdrawalController {
    @Autowired
    private AffiliateCommissionWithdrawalService affiliateCommissionWithdrawalService;

    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    @Autowired
    AffiliateService affiliateService;

    /**
     * 提现记录
     * @return
     */
    @ApiOperation("提现记录")
    @GetMapping("/record")
    public AffiliateCommissionWithdrawalListResponse withdrawalList(){
        Session session = UserUtil.getSession(redisTemplate);
        return affiliateService.withdrawalList(session.getCustomerId());
    }

    @ApiOperation("提现申请")
    @PostMapping("/request")
    public BaseResponse commissionWithdrawalRequest(@RequestBody @Valid AffiliateWithdrawalRequest request) throws CustomerException {
        Session session = UserUtil.getSession(redisTemplate);
        String key = RedisKey.STRING_ACCOUNT_COMMISSION_WITHDRAWAL + session.getAccountId();
        boolean flag = RedisUtil.lock(redisTemplate,key,5L,10*1000L);
        if(flag){
            BaseResponse response = affiliateCommissionWithdrawalService.affiliateWithdrawalRequest(request,session);
            RedisUtil.unLock(redisTemplate,key);
            return response;
        }
        return BaseResponse.failed("Account processing, please try again later");
    }

    @ApiOperation("提现审核通过")
    @PostMapping("/confirm/{id}")
    public BaseResponse withdrawalConfirm(@PathVariable Long id, @RequestBody @Valid AffiliateWithdrawalConfirmRequest confirm){
        Session session = UserUtil.getSession(redisTemplate);
        return affiliateCommissionWithdrawalService.affiliateWithdrawalConfirm(id,session,confirm);
    }
    @ApiOperation("提现审核不通过")
    @PostMapping("/reject")
    public BaseResponse withdrawalReject(@RequestBody @Valid AffiliateWithdrawalRejectRequest reject){
        Session session = UserUtil.getSession(redisTemplate);
        return affiliateCommissionWithdrawalService.affiliateWithdrawalReject(reject,session);
    }

    @RequestMapping(value="/info/{id}", method=RequestMethod.GET)
    @Permission(permission = "affiliate:affiliatecommissionwithdrawal:info:id")
    public AffiliateCommissionWithdrawalInfoResponse info(@PathVariable Long id) {
        AffiliateCommissionWithdrawal result = affiliateCommissionWithdrawalService.selectByPrimaryKey(id);
        AffiliateCommissionWithdrawalInfoResponse res = new AffiliateCommissionWithdrawalInfoResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,result,id);
        return res;
    }

    @RequestMapping(value="/list", method=RequestMethod.POST)
    @Permission(permission = "affiliate:affiliatecommissionwithdrawal:list")
    public AffiliateCommissionWithdrawalListResponse list(@RequestBody @Valid AffiliateCommissionWithdrawalListRequest request) {
        List<AffiliateCommissionWithdrawal> results = affiliateCommissionWithdrawalService.select(request);
        Long total = affiliateCommissionWithdrawalService.count(request);
        request.setTotal(total);
        AffiliateCommissionWithdrawalListResponse res = new AffiliateCommissionWithdrawalListResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,results,request);
        return res;
    }

    @RequestMapping(value="/add", method=RequestMethod.POST)
    @Permission(permission = "affiliate:affiliatecommissionwithdrawal:add")
    public AffiliateCommissionWithdrawalAddResponse add(@RequestBody @Valid AffiliateCommissionWithdrawalAddRequest request) {
        AffiliateCommissionWithdrawal entity=request.toAffiliateCommissionWithdrawal();
        affiliateCommissionWithdrawalService.insertSelective(entity);
        AffiliateCommissionWithdrawalAddResponse res = new AffiliateCommissionWithdrawalAddResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,entity,request);
        return res;
    }

    @RequestMapping(value="/del/{id}", method=RequestMethod.POST)
    @Permission(permission = "affiliate:affiliatecommissionwithdrawal:del:id")
    public AffiliateCommissionWithdrawalDelResponse del(@PathVariable Long id) {
        affiliateCommissionWithdrawalService.deleteByPrimaryKey(id);
        AffiliateCommissionWithdrawalDelResponse res = new AffiliateCommissionWithdrawalDelResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
        return res;
    }

    @RequestMapping(value="/update/{id}", method=RequestMethod.POST)
    @Permission(permission = "affiliate:affiliatecommissionwithdrawal:update")
    public AffiliateCommissionWithdrawalUpdateResponse update(@PathVariable Long id, @RequestBody @Valid AffiliateCommissionWithdrawalUpdateRequest request) {
        AffiliateCommissionWithdrawal entity=request.toAffiliateCommissionWithdrawal(id);
        affiliateCommissionWithdrawalService.updateByPrimaryKeySelective(entity);
        AffiliateCommissionWithdrawalUpdateResponse res = new AffiliateCommissionWithdrawalUpdateResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
        return res;
    }


}
