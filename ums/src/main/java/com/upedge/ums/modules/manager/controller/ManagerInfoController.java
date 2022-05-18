package com.upedge.ums.modules.manager.controller;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.component.annotation.Permission;
import com.upedge.common.constant.Constant;
import com.upedge.common.constant.ResultCode;
import com.upedge.common.constant.key.RedisKey;
import com.upedge.common.model.manager.vo.ManagerInfoVo;
import com.upedge.common.model.user.vo.Session;
import com.upedge.common.web.util.RequestUtil;
import com.upedge.common.web.util.UserUtil;
import com.upedge.ums.modules.manager.entity.ManagerInfo;
import com.upedge.ums.modules.manager.request.ManagerInfoAddRequest;
import com.upedge.ums.modules.manager.request.ManagerInfoListRequest;
import com.upedge.ums.modules.manager.response.ManagerInfoInfoResponse;
import com.upedge.ums.modules.manager.response.ManagerInfoListResponse;
import com.upedge.ums.modules.manager.service.ManagerInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

/**
 * 
 *
 * @author gx
 */
@Api(tags = "客户经理信息")
@RestController
@RequestMapping("/managerInfo")
public class ManagerInfoController {
    @Autowired
    private ManagerInfoService managerInfoService;

    @Autowired
    RedisTemplate redisTemplate;

    @ApiOperation("客户经理详情")
    @RequestMapping(value="/info/{id}", method=RequestMethod.GET)
    @Permission(permission = "manager:managerinfo:info:id")
    public ManagerInfoInfoResponse info(@PathVariable Long id) {
        ManagerInfo result = managerInfoService.selectByPrimaryKey(id);
        ManagerInfoInfoResponse res = new ManagerInfoInfoResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,result,id);
        return res;
    }

    @ApiOperation("客户经理列表")
    @RequestMapping(value="/list", method=RequestMethod.POST)
    @Permission(permission = "manager:managerinfo:list")
    public ManagerInfoListResponse list(@RequestBody @Valid ManagerInfoListRequest request) {
        List<ManagerInfoVo> managerInfoVos = managerInfoService.selectManagerDetail();
        ManagerInfoListResponse res = new ManagerInfoListResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,managerInfoVos,request);
        return res;
    }

    @ApiOperation("创建客户经理")
    @RequestMapping(value="/create", method=RequestMethod.POST)
    @Permission(permission = "manager:managerinfo:add")
    public BaseResponse add(@RequestBody @Valid ManagerInfoAddRequest request) {
        Session session = UserUtil.getSession(redisTemplate);
        return managerInfoService.create(request,session);
    }


    @GetMapping("/bind/{inviteToken}")
    public BaseResponse ipBindInviteToken(@PathVariable String inviteToken){

        ManagerInfo managerInfo = managerInfoService.selectByInviteCode(inviteToken);
        if (null == managerInfo){
            return BaseResponse.failed();
        }

        HttpServletRequest request = RequestUtil.getRequest();
        String ip = RequestUtil.getIpAddress(request);
        if (ip.equals("unknown")){
            return BaseResponse.success();
        }
        redisTemplate.opsForHash().put(RedisKey.HASH_IP_MANAGER_INVITE_TOKEN,ip,inviteToken);

        return new BaseResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS);
    }


//    @RequestMapping(value="/update/{id}", method=RequestMethod.POST)
//    @Permission(permission = "manager:managerinfo:update")
//    public ManagerInfoUpdateResponse update(@PathVariable Long id,@RequestBody @Valid ManagerInfoUpdateRequest request) {
//        ManagerInfo entity=request.toManagerInfo(id);
//        managerInfoService.updateByPrimaryKeySelective(entity);
//        ManagerInfoUpdateResponse res = new ManagerInfoUpdateResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
//        return res;
//    }


}
