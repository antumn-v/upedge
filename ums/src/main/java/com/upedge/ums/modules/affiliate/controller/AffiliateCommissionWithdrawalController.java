package com.upedge.ums.modules.affiliate.controller;

import java.util.Arrays;
import java.util.Map;

import com.upedge.common.constant.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import com.upedge.common.component.annotation.Permission;
import com.upedge.ums.modules.affiliate.entity.AffiliateCommissionWithdrawal;
import com.upedge.ums.modules.affiliate.service.AffiliateCommissionWithdrawalService;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.upedge.common.constant.Constant;
import com.upedge.ums.modules.affiliate.request.AffiliateCommissionWithdrawalAddRequest;
import com.upedge.ums.modules.affiliate.request.AffiliateCommissionWithdrawalListRequest;
import com.upedge.ums.modules.affiliate.request.AffiliateCommissionWithdrawalUpdateRequest;

import com.upedge.ums.modules.affiliate.response.AffiliateCommissionWithdrawalAddResponse;
import com.upedge.ums.modules.affiliate.response.AffiliateCommissionWithdrawalDelResponse;
import com.upedge.ums.modules.affiliate.response.AffiliateCommissionWithdrawalInfoResponse;
import com.upedge.ums.modules.affiliate.response.AffiliateCommissionWithdrawalListResponse;
import com.upedge.ums.modules.affiliate.response.AffiliateCommissionWithdrawalUpdateResponse;
import javax.validation.Valid;

/**
 * 
 *
 * @author gx
 */
@RestController
@RequestMapping("/affiliateCommissionWithdrawal")
public class AffiliateCommissionWithdrawalController {
    @Autowired
    private AffiliateCommissionWithdrawalService affiliateCommissionWithdrawalService;


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
    public AffiliateCommissionWithdrawalUpdateResponse update(@PathVariable Long id,@RequestBody @Valid AffiliateCommissionWithdrawalUpdateRequest request) {
        AffiliateCommissionWithdrawal entity=request.toAffiliateCommissionWithdrawal(id);
        affiliateCommissionWithdrawalService.updateByPrimaryKeySelective(entity);
        AffiliateCommissionWithdrawalUpdateResponse res = new AffiliateCommissionWithdrawalUpdateResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
        return res;
    }


}
