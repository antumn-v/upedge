package com.upedge.ums.modules.user.controller;

import java.util.Arrays;
import java.util.Map;

import com.upedge.common.constant.ResultCode;
import com.upedge.common.model.user.vo.Session;
import com.upedge.common.web.util.UserUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import com.upedge.common.component.annotation.Permission;
import com.upedge.ums.modules.user.entity.Role;
import com.upedge.ums.modules.user.service.RoleService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.upedge.common.constant.Constant;
import com.upedge.ums.modules.user.request.RoleAddRequest;
import com.upedge.ums.modules.user.request.RoleListRequest;
import com.upedge.ums.modules.user.request.RoleUpdateRequest;

import com.upedge.ums.modules.user.response.RoleAddResponse;
import com.upedge.ums.modules.user.response.RoleDelResponse;
import com.upedge.ums.modules.user.response.RoleInfoResponse;
import com.upedge.ums.modules.user.response.RoleListResponse;
import com.upedge.ums.modules.user.response.RoleUpdateResponse;
import javax.validation.Valid;

/**
 * 
 *
 * @author gx
 */
@Api(tags = "角色管理")
@RestController
@RequestMapping("/role")
public class RoleController {
    @Autowired
    private RoleService roleService;

    @Autowired
    RedisTemplate redisTemplate;

    @ApiOperation("角色详情")
    @RequestMapping(value="/info/{id}", method=RequestMethod.GET)
    @Permission(permission = "user:role:info:id")
    public RoleInfoResponse info(@PathVariable Long id) {
        Role result = roleService.selectByPrimaryKey(id);
        RoleInfoResponse res = new RoleInfoResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,result,id);
        return res;
    }
    @ApiOperation("角色列表")
    @RequestMapping(value="/list", method=RequestMethod.POST)
    @Permission(permission = "user:role:list")
    public RoleListResponse list(@RequestBody @Valid RoleListRequest request) {
        Session session = UserUtil.getSession(redisTemplate);
        if (request.getT() == null){
            request.setT(new Role());
        }
        request.getT().setCustomerId(session.getCustomerId());
        request.setPageSize(-1);
        List<Role> results = roleService.select(request);
        Long total = roleService.count(request);
        request.setTotal(total);
        RoleListResponse res = new RoleListResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,results,request);
        return res;
    }

    @ApiOperation("添加角色")
    @RequestMapping(value="/add", method=RequestMethod.POST)
    @Permission(permission = "user:role:add")
    public RoleAddResponse add(@RequestBody @Valid RoleAddRequest request) {
        Session session = UserUtil.getSession(redisTemplate);
        Role entity=request.toRole(session);
        roleService.insertSelective(entity);
        RoleAddResponse res = new RoleAddResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,entity,request);
        return res;
    }

    @RequestMapping(value="/del/{id}", method=RequestMethod.POST)
    @Permission(permission = "user:role:del:id")
    public RoleDelResponse del(@PathVariable Long id) {
        roleService.deleteByPrimaryKey(id);
        RoleDelResponse res = new RoleDelResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
        return res;
    }

    @RequestMapping(value="/update/{id}", method=RequestMethod.POST)
    @Permission(permission = "user:role:update")
    public RoleUpdateResponse update(@PathVariable Long id,@RequestBody @Valid RoleUpdateRequest request) {
        Role entity=request.toRole(id);
        roleService.updateByPrimaryKeySelective(entity);
        RoleUpdateResponse res = new RoleUpdateResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
        return res;
    }


}
