package com.upedge.ums.modules.log.controller;

import com.upedge.common.component.annotation.Permission;
import com.upedge.common.constant.Constant;
import com.upedge.common.constant.ResultCode;
import com.upedge.common.model.user.vo.Session;
import com.upedge.common.web.util.UserUtil;
import com.upedge.ums.modules.log.entity.UserOpLog;
import com.upedge.ums.modules.log.request.UserOpLogListRequest;
import com.upedge.ums.modules.log.response.UserOpLogInfoResponse;
import com.upedge.ums.modules.log.response.UserOpLogListResponse;
import com.upedge.ums.modules.log.service.UserOpLogService;
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
//@RequestMapping("/userOpLog")
public class UserOpLogController {
    @Autowired
    private UserOpLogService userOpLogService;
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @RequestMapping(value="/info/{id}", method=RequestMethod.GET)
    @Permission(permission = "log:useroplog:info:id")
    public UserOpLogInfoResponse info(@PathVariable Long id) {
        UserOpLog result = userOpLogService.selectByPrimaryKey(id);
        UserOpLogInfoResponse res = new UserOpLogInfoResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,result,id);
        return res;
    }

    @RequestMapping(value="/list", method=RequestMethod.POST)
    @Permission(permission = "log:useroplog:list")
    public UserOpLogListResponse list(@RequestBody @Valid UserOpLogListRequest request) {
        Session session= UserUtil.getSession(redisTemplate);
        List<UserOpLog> results = userOpLogService.select(request);
        if(request.getT()==null){
           request.getT().setCustomerId(session.getCustomerId());
        }
        Long total = userOpLogService.count(request);
        request.setTotal(total);
        UserOpLogListResponse res = new UserOpLogListResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,results,request);
        return res;
    }



}
