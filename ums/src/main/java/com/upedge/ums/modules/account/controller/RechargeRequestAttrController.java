package com.upedge.ums.modules.account.controller;

import java.util.Arrays;
import java.util.Map;

import com.upedge.common.constant.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import com.upedge.common.component.annotation.Permission;
import com.upedge.ums.modules.account.entity.RechargeRequestAttr;
import com.upedge.ums.modules.account.service.RechargeRequestAttrService;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.upedge.common.constant.Constant;
import com.upedge.ums.modules.account.request.RechargeRequestAttrAddRequest;
import com.upedge.ums.modules.account.request.RechargeRequestAttrListRequest;
import com.upedge.ums.modules.account.request.RechargeRequestAttrUpdateRequest;

import com.upedge.ums.modules.account.response.RechargeRequestAttrAddResponse;
import com.upedge.ums.modules.account.response.RechargeRequestAttrDelResponse;
import com.upedge.ums.modules.account.response.RechargeRequestAttrInfoResponse;
import com.upedge.ums.modules.account.response.RechargeRequestAttrListResponse;
import com.upedge.ums.modules.account.response.RechargeRequestAttrUpdateResponse;
import javax.validation.Valid;

/**
 * 
 *
 * @author gx
 */
@RestController
@RequestMapping("/rechargeRequestAttr")
public class RechargeRequestAttrController {
    @Autowired
    private RechargeRequestAttrService rechargeRequestAttrService;


    @RequestMapping(value="/info/{id}", method=RequestMethod.GET)
    @Permission(permission = "account:rechargerequestattr:info:id")
    public RechargeRequestAttrInfoResponse info(@PathVariable Integer id) {
        RechargeRequestAttr result = rechargeRequestAttrService.selectByPrimaryKey(id);
        RechargeRequestAttrInfoResponse res = new RechargeRequestAttrInfoResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,result,id);
        return res;
    }

    @RequestMapping(value="/list", method=RequestMethod.POST)
    @Permission(permission = "account:rechargerequestattr:list")
    public RechargeRequestAttrListResponse list(@RequestBody @Valid RechargeRequestAttrListRequest request) {
        List<RechargeRequestAttr> results = rechargeRequestAttrService.select(request);
        Long total = rechargeRequestAttrService.count(request);
        request.setTotal(total);
        RechargeRequestAttrListResponse res = new RechargeRequestAttrListResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,results,request);
        return res;
    }

    @RequestMapping(value="/add", method=RequestMethod.POST)
    @Permission(permission = "account:rechargerequestattr:add")
    public RechargeRequestAttrAddResponse add(@RequestBody @Valid RechargeRequestAttrAddRequest request) {
        RechargeRequestAttr entity=request.toRechargeRequestAttr();
        rechargeRequestAttrService.insertSelective(entity);
        RechargeRequestAttrAddResponse res = new RechargeRequestAttrAddResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,entity,request);
        return res;
    }

    @RequestMapping(value="/del/{id}", method=RequestMethod.POST)
    @Permission(permission = "account:rechargerequestattr:del:id")
    public RechargeRequestAttrDelResponse del(@PathVariable Integer id) {
        rechargeRequestAttrService.deleteByPrimaryKey(id);
        RechargeRequestAttrDelResponse res = new RechargeRequestAttrDelResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
        return res;
    }

    @RequestMapping(value="/update/{id}", method=RequestMethod.POST)
    @Permission(permission = "account:rechargerequestattr:update")
    public RechargeRequestAttrUpdateResponse update(@PathVariable Integer id,@RequestBody @Valid RechargeRequestAttrUpdateRequest request) {
        RechargeRequestAttr entity=request.toRechargeRequestAttr(id);
        rechargeRequestAttrService.updateByPrimaryKeySelective(entity);
        RechargeRequestAttrUpdateResponse res = new RechargeRequestAttrUpdateResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
        return res;
    }


}
