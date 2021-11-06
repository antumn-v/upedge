package com.upedge.ums.modules.affiliate.controller;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.component.annotation.Permission;
import com.upedge.common.constant.Constant;
import com.upedge.common.constant.ResultCode;
import com.upedge.common.model.user.request.OrderBenefitsRequest;
import com.upedge.ums.modules.affiliate.entity.AffiliateCommissionRecord;
import com.upedge.ums.modules.affiliate.request.AffiliateCommissionRecordAddRequest;
import com.upedge.ums.modules.affiliate.request.AffiliateCommissionRecordListRequest;
import com.upedge.ums.modules.affiliate.request.AffiliateCommissionRecordUpdateRequest;
import com.upedge.ums.modules.affiliate.response.*;
import com.upedge.ums.modules.affiliate.service.AffiliateCommissionRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 
 *
 * @author author
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
    public AffiliateCommissionRecordUpdateResponse update(@PathVariable Long id, @RequestBody @Valid AffiliateCommissionRecordUpdateRequest request) {
        AffiliateCommissionRecord entity=request.toAffiliateCommissionRecord(id);
        affiliateCommissionRecordService.updateByPrimaryKeySelective(entity);
        AffiliateCommissionRecordUpdateResponse res = new AffiliateCommissionRecordUpdateResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
        return res;
    }

    @PostMapping("/packageMonthOrderBenefitsListAmount")
    public BaseResponse packageMonthOrderBenefitsListAmount(@RequestBody OrderBenefitsRequest orderBenefitsRequest){
        return  affiliateCommissionRecordService.packageMonthOrderBenefitsListAmount(orderBenefitsRequest);
    };

    @PostMapping("/affiliateCommissionRecord/packageMonthWholeSaleOrderBenefitsListAmount")
    public BaseResponse packageMonthWholeSaleOrderBenefitsListAmount(@RequestBody OrderBenefitsRequest orderBenefitsRequest){
        return  affiliateCommissionRecordService.packageMonthWholeSaleOrderBenefitsListAmount(orderBenefitsRequest);
    };

}
