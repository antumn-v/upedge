package com.upedge.ums.modules.account.controller;

import com.upedge.common.constant.Constant;
import com.upedge.common.constant.ResultCode;
import com.upedge.common.model.user.vo.Session;
import com.upedge.common.web.util.UserUtil;
import com.upedge.ums.modules.account.entity.AdminCredit;
import com.upedge.ums.modules.account.request.AdminCreditApplyRequest;
import com.upedge.ums.modules.account.request.AdminCreditListRequest;
import com.upedge.ums.modules.account.response.*;
import com.upedge.ums.modules.account.service.AdminCreditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * 
 *
 * @author author
 */
//@RestController
//@RequestMapping("/adminCredit")
public class AdminCreditController {
    @Autowired
    private AdminCreditService adminCreditService;
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 信用额度信息列表
     * @param request
     * @return
     */
    @RequestMapping(value="/listInfo", method=RequestMethod.POST)
    public AdminCreditListResponse listInfo(@RequestBody @Valid AdminCreditListRequest request) {
        if(request.getT()==null){
            request.setT(new AdminCredit());
        }
        //0:申请中 1:已确认 2:已驳回
        request.getT().setState(0);
        List<AdminCredit> results = adminCreditService.select(request);
        Long total = adminCreditService.count(request);
        request.setTotal(total);
        AdminCreditListResponse res = new AdminCreditListResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,results,request);
        return res;
    }

    /**
     * 历史信用额度信息列表
     * @param request
     * @return
     */
    @RequestMapping(value="/historyInfo", method=RequestMethod.POST)
    public AdminCreditListResponse historyInfo(@RequestBody @Valid AdminCreditListRequest request) {
        if(request.getT()==null){
            request.setT(new AdminCredit());
        }
        //0:申请中 1:已确认 2:已驳回
        request.getT().setGteState(1);
        List<AdminCredit> results = adminCreditService.select(request);
        Long total = adminCreditService.count(request);
        request.setTotal(total);
        AdminCreditListResponse res = new AdminCreditListResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,results,request);
        return res;
    }

    /**
     * 申请信用额度上限
     */
    @RequestMapping(value = "/applyCreditLimit", method=RequestMethod.POST)
    public AdminCreditApplyResponse applyCreditLimit(@RequestBody @Valid AdminCreditApplyRequest request) {
        Session session= UserUtil.getSession(redisTemplate);
        return adminCreditService.applyCreditLimit(request,session);
    }

    /**
     * 确认信用额度申请
     * @param id
     * @return
     */
    @RequestMapping(value="/confirmApply/{id}", method=RequestMethod.POST)
    public AdminCreditUpdateResponse confirmApply(@PathVariable Long id) {
        Session session= UserUtil.getSession(redisTemplate);
        return adminCreditService.confirmApply(id,session);
    }

    /**
     * 驳回信用额度申请
     * @param id
     * @return
     */
    @RequestMapping(value="/rejectApply/{id}", method=RequestMethod.POST)
    public AdminCreditUpdateResponse rejectApply(@PathVariable Long id) {
        Session session= UserUtil.getSession(redisTemplate);
        return adminCreditService.rejectApply(id,session);
    }

}
