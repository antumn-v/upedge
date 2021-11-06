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
    @RequestMapping(value="/admin/add", method=RequestMethod.POST)
    public VatRuleAddResponse addVatRule(@RequestBody @Valid VatRuleAddRequest request) {
        Session session= UserUtil.getSession(redisTemplate);
        return vatRuleService.addVatRule(request,session);
    }

    /**
     * 查询单个VAT规则
     * @param id
     * @return
     */
    @RequestMapping(value="/admin/info/{id}", method=RequestMethod.POST)
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
    @RequestMapping(value="/admin/list", method=RequestMethod.POST)
    public VatRuleListResponse adminList(@RequestBody @Valid VatRuleListRequest request) {
        return vatRuleService.adminList(request);
    }

    /**
     * 更新VAT规则
     * @param id
     * @param request
     * @return
     */
    @RequestMapping(value="/admin/update/{id}", method=RequestMethod.POST)
    public VatRuleUpdateResponse adminUpdate(@PathVariable Long id, @RequestBody @Valid VatRuleUpdateRequest request) {
        Session session= UserUtil.getSession(redisTemplate);
        return vatRuleService.adminUpdate(id,request,session);
    }
}
