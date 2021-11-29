package com.upedge.oms.modules.vat.controller;

import com.upedge.common.constant.Constant;
import com.upedge.common.constant.ResultCode;
import com.upedge.common.model.user.vo.Session;
import com.upedge.common.web.util.UserUtil;
import com.upedge.oms.modules.vat.entity.VatRule;
import com.upedge.oms.modules.vat.request.VatRuleAddRequest;
import com.upedge.oms.modules.vat.request.VatRuleListRequest;
import com.upedge.oms.modules.vat.request.VatRuleUpdateRequest;
import com.upedge.oms.modules.vat.response.VatRuleAddResponse;
import com.upedge.oms.modules.vat.response.VatRuleInfoResponse;
import com.upedge.oms.modules.vat.response.VatRuleListResponse;
import com.upedge.oms.modules.vat.response.VatRuleUpdateResponse;
import com.upedge.oms.modules.vat.service.VatRuleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import javax.validation.Valid;
import java.math.BigDecimal;
import org.springframework.web.bind.annotation.*;
/**
 * 
 *
 * @author author
 */
@Api("vat管理")
@RestController
@RequestMapping("/vatRule")
public class VatRuleController {
    @Autowired
    private VatRuleService vatRuleService;
    @Autowired
    RedisTemplate<String, Object> redisTemplate;


    //========================admin======================

    /**
     * admin添加VAT规则
     * @param request
     * @return
     */
    @ApiOperation("添加VAT规则")
    @RequestMapping(value="/add", method=RequestMethod.POST)
    public VatRuleAddResponse addVatRule(@RequestBody @Valid VatRuleAddRequest request) {
        Session session= UserUtil.getSession(redisTemplate);
        return vatRuleService.addVatRule(request,session);
    }

    /**
     * 查询单个VAT规则
     * @param id
     * @return
     */
    @ApiOperation("查询VAT规则")
    @RequestMapping(value="/info/{id}", method=RequestMethod.POST)
    public VatRuleInfoResponse adminInfo(@PathVariable Long id) {
        VatRule result = vatRuleService.selectByPrimaryKey(id);
        BigDecimal ratio=result.getRatio().multiply(new BigDecimal(100));
        result.setRatio(ratio);
        VatRuleInfoResponse res = new VatRuleInfoResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,result,id);
        return res;
    }

    /**
     * VAT规则列表
     * @param request
     * @return
     */
    @ApiOperation("VAT规则列表")
    @RequestMapping(value="/list", method=RequestMethod.POST)
    public VatRuleListResponse adminList(@RequestBody @Valid VatRuleListRequest request) {
        return vatRuleService.adminList(request);
    }

    /**
     * 更新VAT规则
     * @param id
     * @param request
     * @return
     */
    @ApiOperation("更新VAT规则")
    @RequestMapping(value="/update/{id}", method=RequestMethod.POST)
    public VatRuleUpdateResponse adminUpdate(@PathVariable Long id, @RequestBody @Valid VatRuleUpdateRequest request) {
        Session session= UserUtil.getSession(redisTemplate);
        return vatRuleService.adminUpdate(id,request,session);
    }
}
