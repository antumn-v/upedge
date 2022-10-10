package com.upedge.ums.modules.user.controller;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.component.annotation.Permission;
import com.upedge.common.constant.Constant;
import com.upedge.common.constant.ResultCode;
import com.upedge.common.model.user.vo.RoleVo;
import com.upedge.common.model.user.vo.Session;
import com.upedge.common.web.util.UserUtil;
import com.upedge.ums.modules.user.entity.Role;
import com.upedge.ums.modules.user.request.RoleAddRequest;
import com.upedge.ums.modules.user.request.RoleListRequest;
import com.upedge.ums.modules.user.request.RoleUpdateRequest;
import com.upedge.ums.modules.user.response.RoleDelResponse;
import com.upedge.ums.modules.user.response.RoleInfoResponse;
import com.upedge.ums.modules.user.response.RoleListResponse;
import com.upedge.ums.modules.user.service.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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
        RoleVo result = roleService.selectRoleInfo(id);
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
    public BaseResponse add(@RequestBody @Valid RoleAddRequest request) {
        Session session = UserUtil.getSession(redisTemplate);
        return roleService.addRole(request,session);
    }

    @RequestMapping(value="/del/{id}", method=RequestMethod.POST)
    @Permission(permission = "user:role:del:id")
    public RoleDelResponse del(@PathVariable Long id) {
        roleService.deleteByPrimaryKey(id);
        RoleDelResponse res = new RoleDelResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
        return res;
    }

    @RequestMapping(value="/update", method=RequestMethod.POST)
    @Permission(permission = "user:role:update")
    public BaseResponse update(@RequestBody @Valid RoleUpdateRequest request) {

        return roleService.updateRole(request,request.getId());
    }


}
