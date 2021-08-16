package com.upedge.ums.modules.affiliate.controller;

import java.util.Arrays;
import java.util.Map;

import com.upedge.common.constant.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import com.upedge.common.component.annotation.Permission;
import com.upedge.ums.modules.affiliate.entity.Affiliate;
import com.upedge.ums.modules.affiliate.service.AffiliateService;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.upedge.common.constant.Constant;
import com.upedge.ums.modules.affiliate.request.AffiliateAddRequest;
import com.upedge.ums.modules.affiliate.request.AffiliateListRequest;
import com.upedge.ums.modules.affiliate.request.AffiliateUpdateRequest;

import com.upedge.ums.modules.affiliate.response.AffiliateAddResponse;
import com.upedge.ums.modules.affiliate.response.AffiliateDelResponse;
import com.upedge.ums.modules.affiliate.response.AffiliateInfoResponse;
import com.upedge.ums.modules.affiliate.response.AffiliateListResponse;
import com.upedge.ums.modules.affiliate.response.AffiliateUpdateResponse;
import javax.validation.Valid;

/**
 * 
 *
 * @author gx
 */
@RestController
@RequestMapping("/affiliate")
public class AffiliateController {
    @Autowired
    private AffiliateService affiliateService;


    @RequestMapping(value="/info/{id}", method=RequestMethod.GET)
    @Permission(permission = "affiliate:affiliate:info:id")
    public AffiliateInfoResponse info(@PathVariable Long id) {
        Affiliate result = affiliateService.selectByPrimaryKey(id);
        AffiliateInfoResponse res = new AffiliateInfoResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,result,id);
        return res;
    }

    @RequestMapping(value="/list", method=RequestMethod.POST)
    @Permission(permission = "affiliate:affiliate:list")
    public AffiliateListResponse list(@RequestBody @Valid AffiliateListRequest request) {
        List<Affiliate> results = affiliateService.select(request);
        Long total = affiliateService.count(request);
        request.setTotal(total);
        AffiliateListResponse res = new AffiliateListResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,results,request);
        return res;
    }

    @RequestMapping(value="/add", method=RequestMethod.POST)
    @Permission(permission = "affiliate:affiliate:add")
    public AffiliateAddResponse add(@RequestBody @Valid AffiliateAddRequest request) {
        Affiliate entity=request.toAffiliate();
        affiliateService.insertSelective(entity);
        AffiliateAddResponse res = new AffiliateAddResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,entity,request);
        return res;
    }

    @RequestMapping(value="/del/{id}", method=RequestMethod.POST)
    @Permission(permission = "affiliate:affiliate:del:id")
    public AffiliateDelResponse del(@PathVariable Long id) {
        affiliateService.deleteByPrimaryKey(id);
        AffiliateDelResponse res = new AffiliateDelResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
        return res;
    }

    @RequestMapping(value="/update/{id}", method=RequestMethod.POST)
    @Permission(permission = "affiliate:affiliate:update")
    public AffiliateUpdateResponse update(@PathVariable Long id,@RequestBody @Valid AffiliateUpdateRequest request) {
        Affiliate entity=request.toAffiliate(id);
        affiliateService.updateByPrimaryKeySelective(entity);
        AffiliateUpdateResponse res = new AffiliateUpdateResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
        return res;
    }


}
