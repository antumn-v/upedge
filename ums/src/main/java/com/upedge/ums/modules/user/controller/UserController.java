package com.upedge.ums.modules.user.controller;

import com.alibaba.fastjson.JSONObject;
import com.upedge.common.base.BaseResponse;
import com.upedge.common.component.annotation.Permission;
import com.upedge.common.constant.BaseCode;
import com.upedge.common.constant.Constant;
import com.upedge.common.constant.EmailTemplate;
import com.upedge.common.constant.ResultCode;
import com.upedge.common.constant.key.RedisKey;
import com.upedge.common.exception.CustomerException;
import com.upedge.common.model.user.request.UserInfoSelectRequest;
import com.upedge.common.model.user.vo.Session;
import com.upedge.common.utils.EmailUtils;
import com.upedge.common.utils.TokenUtil;
import com.upedge.common.web.util.RequestUtil;
import com.upedge.common.web.util.UserUtil;
import com.upedge.ums.modules.affiliate.service.AffiliateService;
import com.upedge.ums.modules.store.entity.Store;
import com.upedge.ums.modules.store.service.StoreService;
import com.upedge.ums.modules.user.entity.Customer;
import com.upedge.ums.modules.user.entity.User;
import com.upedge.ums.modules.user.request.*;
import com.upedge.ums.modules.user.response.*;
import com.upedge.ums.modules.user.service.CustomerService;
import com.upedge.ums.modules.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

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

    @Autowired
    CustomerService customerService;

    @Autowired
    AffiliateService affiliateService;


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
        request = getReferrerToken(request);
        request.setDefault(true);
        CustomerSignUpResponse customerSignUpResponse = userService.signUp(request);
        try {
            if(StringUtils.isNotBlank(request.getState())
                    && customerSignUpResponse.getCode() == ResultCode.SUCCESS_CODE){
                Store store = (Store) redisTemplate.opsForValue().get(request.getState());
                if (store != null){
                    JSONObject jsonObject = JSONObject.parseObject(JSONObject.toJSONString(customerSignUpResponse.getData()));
                    String token = jsonObject.getString("token");
                    Session session = (Session) redisTemplate.opsForValue().get(TokenUtil.getTokenKey(token));
                    storeService.updateShopifyStore(store.getStoreUrl(),store.getApiToken(),session);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return customerSignUpResponse;
    }

    private CustomerSignUpRequest getReferrerToken(CustomerSignUpRequest request){
        HttpServletRequest httpServletRequest = RequestUtil.getRequest();
        String ip = RequestUtil.getIpAddress(httpServletRequest);
        if (ip.equals("unknown")){
            return request;
        }
        String token = (String) redisTemplate.opsForHash().get(RedisKey.HASH_IP_REFERRER_TOKEN,ip);
        request.setReferrerToken(token);
        redisTemplate.opsForHash().delete(RedisKey.HASH_IP_REFERRER_TOKEN,ip);

        String managerInviteToken = (String) redisTemplate.opsForHash().get(RedisKey.HASH_IP_MANAGER_INVITE_TOKEN,ip);
        request.setManagerInviteToken(managerInviteToken);
        redisTemplate.opsForHash().delete(RedisKey.HASH_IP_MANAGER_INVITE_TOKEN,ip);
        return request;
    }



    @ApiOperation("登录")
    @PostMapping("/signin")
    public UserSignInResponse userSignIn(@RequestBody @Valid UserSignInRequest request) {
        UserSignInResponse userSignInResponse = userService.signIn(request);
        try {
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
        } catch (Exception e) {
            e.printStackTrace();
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

    @ApiOperation("获取app用户登陆token")
    @PostMapping("/getToken/{customerId}")
    public BaseResponse getCustometLoginToken(@PathVariable Long customerId){
        Session session = UserUtil.getSession(redisTemplate);
        if (session.getUserType() != BaseCode.USER_ROLE_SUPERADMIN){
            return BaseResponse.failed("权限不足,超级管理员可操作");
        }
        Customer customer = customerService.selectByPrimaryKey(customerId);
        if (customer == null){
            return BaseResponse.failed("用户不存在");
        }
        User user = userService.selectByPrimaryKey(customer.getCustomerSignupUserId());
        Map<String, Object> map = userService.userSignIn(user,Constant.APP_APPLICATION_ID,2);
        return BaseResponse.success(map);
    }

    @ApiOperation("邮箱发送验证码")
    @PostMapping("/sendEmail/verifyCode")
    public BaseResponse sendUserEmailVerifyCode(@RequestBody @Valid UserEmailSendCodeRequest request) {
        String email = request.getEmail();
        String key = RedisKey.STRING_EMAIL_SEND_CODE + email;

        UserInfoSelectRequest infoSelectRequest = new UserInfoSelectRequest();
        infoSelectRequest.setEmail(email);
        User user = userService.selectByLoginName(email);
        if (null == user) {
            return BaseResponse.failed("email error");
        }
        char[] codeSeq = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
        Random random = new Random();
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            String r = String.valueOf(codeSeq[random.nextInt(codeSeq.length)]);
            code.append(r);
        }
        boolean b = EmailUtils.sendEmailByAli(email, "Verification code", EmailTemplate.Verification_Code.replace("verifyCode", code));
        if(b){
            redisTemplate.opsForValue().set(key,code,1, TimeUnit.HOURS);
            return new BaseResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
        }
        return BaseResponse.failed();

    }

    @ApiOperation("找回密码")
    @PostMapping("/recoverPassword")
    public BaseResponse userRecoverPassword(@RequestBody@Valid UserRecoverPasswordRequest request){
        return userService.userRecoverPassword(request);
    }

    @ApiOperation("检查邮箱验证码")
    @PostMapping("/emailCodeCheck")
    public BaseResponse emailCodeCheck(@RequestBody@Valid EmailCodeCheckRequest request){
        String key = RedisKey.STRING_EMAIL_SEND_CODE + request.getEmail();
        String code = (String) redisTemplate.opsForValue().get(key);
        if (code == null
        || !code.equals(request.getCode())){
            return BaseResponse.failed("code error");
        }
        return BaseResponse.success();
    }


}
