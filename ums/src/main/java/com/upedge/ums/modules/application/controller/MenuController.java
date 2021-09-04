package com.upedge.ums.modules.application.controller;

import java.util.Arrays;
import java.util.Map;

import com.upedge.common.constant.ResultCode;
import com.upedge.common.model.user.vo.Session;
import com.upedge.common.web.util.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import com.upedge.common.component.annotation.Permission;
import com.upedge.ums.modules.application.entity.Menu;
import com.upedge.ums.modules.application.service.MenuService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.upedge.common.constant.Constant;
import com.upedge.ums.modules.application.request.MenuAddRequest;
import com.upedge.ums.modules.application.request.MenuListRequest;
import com.upedge.ums.modules.application.request.MenuUpdateRequest;

import com.upedge.ums.modules.application.response.MenuAddResponse;
import com.upedge.ums.modules.application.response.MenuDelResponse;
import com.upedge.ums.modules.application.response.MenuInfoResponse;
import com.upedge.ums.modules.application.response.MenuListResponse;
import com.upedge.ums.modules.application.response.MenuUpdateResponse;
import javax.validation.Valid;

/**
 * 
 *
 * @author gx
 */
@RestController
@RequestMapping("/menu")
public class MenuController {
    @Autowired
    private MenuService menuService;

    @Autowired
    RedisTemplate redisTemplate;


    @RequestMapping(value="/info/{id}", method=RequestMethod.GET)
    @Permission(permission = "application:menu:info:id")
    public MenuInfoResponse info(@PathVariable Long id) {
        Menu result = menuService.selectByPrimaryKey(id);
        MenuInfoResponse res = new MenuInfoResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,result,id);
        return res;
    }

    @RequestMapping(value="/list", method=RequestMethod.POST)
    @Permission(permission = "application:menu:list")
    public MenuListResponse list(@RequestBody @Valid MenuListRequest request) {
        List<Menu> results = menuService.select(request);
        Long total = menuService.count(request);
        request.setTotal(total);
        MenuListResponse res = new MenuListResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,results,request);
        return res;
    }

    @RequestMapping(value="/add", method=RequestMethod.POST)
    @Permission(permission = "application:menu:add")
    public MenuAddResponse add(@RequestBody @Valid MenuAddRequest request) {
        Session session = UserUtil.getSession(redisTemplate);
        Menu entity=request.toMenu(session);
        menuService.insertSelective(entity);
        MenuAddResponse res = new MenuAddResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,entity,request);
        return res;
    }

    @RequestMapping(value="/del/{id}", method=RequestMethod.POST)
    @Permission(permission = "application:menu:del:id")
    public MenuDelResponse del(@PathVariable Long id) {
        menuService.deleteByPrimaryKey(id);
        MenuDelResponse res = new MenuDelResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
        return res;
    }

    @RequestMapping(value="/update/{id}", method=RequestMethod.POST)
    @Permission(permission = "application:menu:update")
    public MenuUpdateResponse update(@PathVariable Long id,@RequestBody @Valid MenuUpdateRequest request) {
        Menu entity=request.toMenu(id);
        menuService.updateByPrimaryKeySelective(entity);
        MenuUpdateResponse res = new MenuUpdateResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
        return res;
    }


}
