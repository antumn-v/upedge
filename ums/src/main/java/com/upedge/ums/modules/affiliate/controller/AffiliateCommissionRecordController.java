package com.upedge.ums.modules.affiliate.controller;

import java.util.Arrays;
import java.util.Map;

import com.upedge.common.constant.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import com.upedge.common.component.annotation.Permission;
import com.upedge.ums.modules.affiliate.entity.AffiliateCommissionRecord;
import com.upedge.ums.modules.affiliate.service.AffiliateCommissionRecordService;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.upedge.common.constant.Constant;
import com.upedge.ums.modules.affiliate.request.AffiliateCommissionRecordAddRequest;
import com.upedge.ums.modules.affiliate.request.AffiliateCommissionRecordListRequest;
import com.upedge.ums.modules.affiliate.request.AffiliateCommissionRecordUpdateRequest;

import com.upedge.ums.modules.affiliate.response.AffiliateCommissionRecordAddResponse;
import com.upedge.ums.modules.affiliate.response.AffiliateCommissionRecordDelResponse;
import com.upedge.ums.modules.affiliate.response.AffiliateCommissionRecordInfoResponse;
import com.upedge.ums.modules.affiliate.response.AffiliateCommissionRecordListResponse;
import com.upedge.ums.modules.affiliate.response.AffiliateCommissionRecordUpdateResponse;
import javax.validation.Valid;

/**
 * 佣金记录表
 *
 * @author gx
 */
@RestController
@RequestMapping("/affiliateCommissionRecord")
public class AffiliateCommissionRecordController {
    @Autowired
    private AffiliateCommissionRecordService affiliateCommissionRecordService;


    @RequestMapping(value="/info/{id}", method=RequestMethod.GET)
    @Permission(permission = "affiliate:affiliatecommissionrecord:info:id")
    public AffiliateCommissionRecordInfoResponse info(@PathVariable Long id) {
        AffiliateCommissionRecord result = affiliateCommissionRecordService.selectByPrimaryKey(id);
        AffiliateCommissionRecordInfoResponse res = new AffiliateCommissionRecordInfoResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,result,id);
        return res;
    }

    @RequestMapping(value="/list", method=RequestMethod.POST)
    @Permission(permission = "affiliate:affiliatecommissionrecord:list")
    public AffiliateCommissionRecordListResponse list(@RequestBody @Valid AffiliateCommissionRecordListRequest request) {
        List<AffiliateCommissionRecord> results = affiliateCommissionRecordService.select(request);
        Long total = affiliateCommissionRecordService.count(request);
        request.setTotal(total);
        AffiliateCommissionRecordListResponse res = new AffiliateCommissionRecordListResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,results,request);
        return res;
    }

    @RequestMapping(value="/add", method=RequestMethod.POST)
    @Permission(permission = "affiliate:affiliatecommissionrecord:add")
    public AffiliateCommissionRecordAddResponse add(@RequestBody @Valid AffiliateCommissionRecordAddRequest request) {
        AffiliateCommissionRecord entity=request.toAffiliateCommissionRecord();
        affiliateCommissionRecordService.insertSelective(entity);
        AffiliateCommissionRecordAddResponse res = new AffiliateCommissionRecordAddResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,entity,request);
        return res;
    }

    @RequestMapping(value="/del/{id}", method=RequestMethod.POST)
    @Permission(permission = "affiliate:affiliatecommissionrecord:del:id")
    public AffiliateCommissionRecordDelResponse del(@PathVariable Long id) {
        affiliateCommissionRecordService.deleteByPrimaryKey(id);
        AffiliateCommissionRecordDelResponse res = new AffiliateCommissionRecordDelResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
        return res;
    }

    @RequestMapping(value="/update/{id}", method=RequestMethod.POST)
    @Permission(permission = "affiliate:affiliatecommissionrecord:update")
    public AffiliateCommissionRecordUpdateResponse update(@PathVariable Long id,@RequestBody @Valid AffiliateCommissionRecordUpdateRequest request) {
        AffiliateCommissionRecord entity=request.toAffiliateCommissionRecord(id);
        affiliateCommissionRecordService.updateByPrimaryKeySelective(entity);
        AffiliateCommissionRecordUpdateResponse res = new AffiliateCommissionRecordUpdateResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
        return res;
    }


}
