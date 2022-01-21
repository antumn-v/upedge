package com.upedge.ums.modules.user.controller;

import com.alibaba.fastjson.JSONObject;
import com.upedge.common.base.BaseResponse;
import com.upedge.common.component.annotation.Permission;
import com.upedge.common.constant.Constant;
import com.upedge.common.constant.ResultCode;
import com.upedge.common.exception.CustomerException;
import com.upedge.common.model.user.vo.Session;
import com.upedge.common.utils.TokenUtil;
import com.upedge.common.web.util.UserUtil;
import com.upedge.ums.modules.store.entity.Store;
import com.upedge.ums.modules.store.service.StoreService;
import com.upedge.ums.modules.user.entity.User;
import com.upedge.ums.modules.user.request.*;
import com.upedge.ums.modules.user.response.*;
import com.upedge.ums.modules.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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
@Slf4j
@Api(tags = "用户管理")
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    StoreService storeService;

    @Autowired
    RedisTemplate redisTemplate;


    @ApiOperation("注册")
    @PostMapping("/signup")
    public CustomerSignUpResponse customerSingUp(@RequestBody @Valid CustomerSignUpRequest request) throws CustomerException {
        String loginName = request.getLoginName();
        if (StringUtils.isBlank(request.getOrgName())) {
            request.setOrgName(loginName);
        }
        if (StringUtils.isBlank(request.getUsername())) {
            request.setUsername(loginName);
        }
        CustomerSignUpResponse customerSignUpResponse = userService.signUp(request);
        if(StringUtils.isNotBlank(request.getState())
                && customerSignUpResponse.getCode() == ResultCode.SUCCESS_CODE){
            JSONObject jsonObject = JSONObject.parseObject(JSONObject.toJSONString(customerSignUpResponse.getData()));
            String token = jsonObject.getString("token");
            Session session = (Session) redisTemplate.opsForValue().get(TokenUtil.getTokenKey(token));
            Store store = (Store) redisTemplate.opsForValue().get(request.getState());
            storeService.updateShopifyStore(store.getStoreUrl(),store.getApiToken(),session);
        }
        return customerSignUpResponse;
    }

    @ApiOperation("登录")
    @PostMapping("/signin")
    public UserSignInResponse userSignIn(@RequestBody @Valid UserSignInRequest request) {
        UserSignInResponse userSignInResponse = userService.signIn(request);
        if(StringUtils.isNotBlank(request.getState())
        && userSignInResponse.getCode() == ResultCode.SUCCESS_CODE){
            JSONObject jsonObject = JSONObject.parseObject(JSONObject.toJSONString(userSignInResponse.getData()));
            String token = jsonObject.getString("token");
            Session session = (Session) redisTemplate.opsForValue().get(TokenUtil.getTokenKey(token));
            Store store = (Store) redisTemplate.opsForValue().get(request.getState());
            if (store != null){
                storeService.updateShopifyStore(store.getStoreUrl(),store.getApiToken(),session);
            }
        }
        return userSignInResponse;
    }

    @ApiOperation("客户个人信息")
    @GetMapping("/profile")
    public BaseResponse profile(){
        return userService.profile();
    }

    @ApiOperation("验证客户登录名是否可用")
    @GetMapping("/loginNameAvailable")
    public BaseResponse loginNameAvailable(@RequestParam String loginName){
        User user = userService.selectByLoginName(loginName);
        if(null != user){
            return new BaseResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,false);
        }
        return new BaseResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,true);
    }

    @ApiOperation("修改密码")
    @PostMapping("/updatePass")
    public BaseResponse userUpdatePassword(@RequestBody @Valid UserUpdatePwdRequest request){
        Session session = UserUtil.getSession(redisTemplate);
        return userService.userUpdatePassword(request,session);
    }

        @RequestMapping(value="/info/{id}", method=RequestMethod.GET)
    @Permission(permission = "user:user:info:id")
    public UserInfoResponse info(@PathVariable Long id) {
        User result = userService.selectByPrimaryKey(id);
        UserInfoResponse res = new UserInfoResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,result,id);
        return res;
    }

    @RequestMapping(value="/list", method=RequestMethod.POST)
    @Permission(permission = "user:user:list")
    public UserListResponse list(@RequestBody @Valid UserListRequest request) {
        List<User> results = userService.select(request);
        Long total = userService.count(request);
        request.setTotal(total);
        UserListResponse res = new UserListResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,results,request);
        return res;
    }

    @RequestMapping(value="/add", method=RequestMethod.POST)
    @Permission(permission = "user:user:add")
    public UserAddResponse add(@RequestBody @Valid UserAddRequest request) {
        Session session = UserUtil.getSession(redisTemplate);
        User entity=request.toUser(session.getCustomerId());
        userService.insertSelective(entity);
        UserAddResponse res = new UserAddResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,entity,request);
        return res;
    }

    @RequestMapping(value="/del/{id}", method=RequestMethod.POST)
    @Permission(permission = "user:user:del:id")
    public UserDelResponse del(@PathVariable Long id) {
        userService.deleteByPrimaryKey(id);
        UserDelResponse res = new UserDelResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
        return res;
    }

    @RequestMapping(value="/update/{id}", method=RequestMethod.POST)
    @Permission(permission = "user:user:update")
    public UserUpdateResponse update(@PathVariable Long id,@RequestBody @Valid UserUpdateRequest request) {
        User entity=request.toUser(id);
        userService.updateByPrimaryKeySelective(entity);
        UserUpdateResponse res = new UserUpdateResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
        return res;
    }


}
