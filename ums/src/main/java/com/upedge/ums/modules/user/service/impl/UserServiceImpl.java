package com.upedge.ums.modules.user.service.impl;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.base.Page;
import com.upedge.common.constant.BaseCode;
import com.upedge.common.constant.Constant;
import com.upedge.common.constant.ResultCode;
import com.upedge.common.constant.key.RedisKey;
import com.upedge.common.enums.CustomerExceptionEnum;
import com.upedge.common.exception.CustomerException;
import com.upedge.common.feign.OmsFeignClient;
import com.upedge.common.model.order.request.CustomerSyncUnpaidOrderRequest;
import com.upedge.common.model.user.vo.*;
import com.upedge.common.utils.IdGenerate;
import com.upedge.common.utils.TokenUtil;
import com.upedge.common.web.util.UserUtil;
import com.upedge.ums.modules.account.dao.AccountUserMapper;
import com.upedge.ums.modules.account.entity.Account;
import com.upedge.ums.modules.account.entity.AccountUser;
import com.upedge.ums.modules.account.service.AccountService;
import com.upedge.ums.modules.affiliate.service.AffiliateService;
import com.upedge.ums.modules.application.entity.Application;
import com.upedge.ums.modules.application.entity.Menu;
import com.upedge.ums.modules.application.service.ApplicationService;
import com.upedge.ums.modules.application.service.MenuService;
import com.upedge.ums.modules.manager.service.CustomerManagerService;
import com.upedge.ums.modules.organization.entity.Organization;
import com.upedge.ums.modules.organization.entity.OrganizationMenu;
import com.upedge.ums.modules.organization.entity.OrganizationUser;
import com.upedge.ums.modules.organization.service.OrganizationMenuService;
import com.upedge.ums.modules.organization.service.OrganizationRoleService;
import com.upedge.ums.modules.organization.service.OrganizationService;
import com.upedge.ums.modules.organization.service.OrganizationUserService;
import com.upedge.ums.modules.store.entity.Store;
import com.upedge.ums.modules.store.service.StoreService;
import com.upedge.ums.modules.user.dao.UserDao;
import com.upedge.ums.modules.user.entity.*;
import com.upedge.ums.modules.user.request.*;
import com.upedge.ums.modules.user.response.CustomerSignUpResponse;
import com.upedge.ums.modules.user.response.UserProfileResponse;
import com.upedge.ums.modules.user.response.UserSignInResponse;
import com.upedge.ums.modules.user.service.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Collectors;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Value("${customer.need.approve:false}")
    private Boolean needApprove;

    @Autowired
    CustomerService customerService;

    @Autowired
    OrganizationService organizationService;

    @Autowired
    RoleService roleService;

    @Autowired
    ApplicationService applicationService;

    @Autowired
    UserApplicationService userApplicationService;

    @Autowired
    UserInfoService userInfoService;

    @Autowired
    OrganizationUserService organizationUserService;

    @Autowired
    UserRoleService userRoleService;

    @Autowired
    OrganizationRoleService organizationRoleService;

    @Autowired
    OrganizationMenuService organizationMenuService;

    @Autowired
    AccountService accountService;


    @Autowired
    CustomerApplicationService customerApplicationService;

    @Autowired
    CustomerSettingService customerSettingService;

    @Autowired
    RolePermissionService rolePermissionService;

    @Autowired
    MenuService menuService;

    @Autowired
    RoleMenuService roleMenuService;

    @Autowired
    AccountUserMapper accountUserMapper;

    @Autowired
    StoreService storeService;

    @Autowired
    AffiliateService affiliateService;

    @Autowired
    OmsFeignClient omsFeignClient;

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    CustomerManagerService customerManagerService;

    @Autowired
    ThreadPoolExecutor threadPoolExecutor;


    /**
     *
     */
    @Transactional
    public int deleteByPrimaryKey(Long id) {
        User record = new User();
        record.setId(id);
        return userDao.deleteByPrimaryKey(record);
    }

    /**
     *
     */
    @Transactional
    public int insert(User record) {
        return userDao.insert(record);
    }

    /**
     *
     */
    @Transactional
    public int insertSelective(User record) {
        return userDao.insert(record);
    }


    @Override
    public User selectByLoginName(String loginName) {
        if (StringUtils.isBlank(loginName)){
            return null;
        }
        return userDao.selectByLoginName(loginName);
    }

    @Override
    public UserProfileResponse profile() {

        Session session = UserUtil.getSession(redisTemplate);
        if (session.getUstatus().intValue() == BaseCode.USER_OFF) {
            return new UserProfileResponse(ResultCode.FAIL_CODE, "user is disabled!");
        }
        Customer customer = customerService.selectByPrimaryKey(session.getCustomerId());
        User user = userDao.selectByPrimaryKey(session.getId());
        List<Menu> menus = roleMenuService.selectRoleMenuByApplication(session.getRole().getId(), session.getApplicationId());
        UserProfileVo userData = new UserProfileVo();
        userData.setVipLevel(customer.getVipLevel());
        UserInfoVo userInfoVo = new UserInfoVo();
        BeanUtils.copyProperties(user, userInfoVo);
        userData.setUserinfo(userInfoVo);
        userData.setRoleVo(session.getRole());
        List<MenuVo> menuVos = menus.stream().map(menu -> {
            MenuVo menuVo = new MenuVo();
            BeanUtils.copyProperties(menu, menuVo);
            return menuVo;
        }).collect(Collectors.toList());
        userData.setMenus(menuVos);

        userData.setPermissions(session.getPermissions());
        return new UserProfileResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS, userData);
    }

    @Override
    public BaseResponse userUpdatePassword(UserUpdatePwdRequest request, Session current) {
        //验证旧密码
        if (!current.getLoginpass().equalsIgnoreCase(
                UserUtil.encryptPassword(request.getOldPass()))) {
            new BaseResponse(ResultCode.FAIL_CODE, "old password error!");
        }
        User user = new User();
        user.setId(current.getId());
        user.setLoginPass(UserUtil.encryptPassword(request.getNewPass(), current.getLoginname()));
        updateByPrimaryKeySelective(user);
        current.setLoginpass(user.getLoginPass());
        UserUtil.updateUser(redisTemplate, current);
        return new BaseResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS);

    }

    /**
     * 注册，创建部门，账户，角色，用户等信息
     *
     * @param request
     * @return
     */
    @Transactional
    @Override
    public CustomerSignUpResponse signUp(CustomerSignUpRequest request) throws CustomerException {
        Application application = applicationService.selectByPrimaryKey(request.getApplicationId());

        if (null == application) {
            throw new CustomerException(CustomerExceptionEnum.FIND_NULL);
        }
        User user = userDao.selectUserByLoginName(request.getLoginName());
        if (null != user) {
            throw new CustomerException(CustomerExceptionEnum.LOGIN_NAME_HAS_BEEN_REGISTERED);
        }
//        UserInfo userInfo = userInfoService.selectByEmail(request.getEmail());
//        if (userInfo != null) {
//            throw new CustomerException(CustomerExceptionEnum.MAILBOX_HAS_BEEN_REGISTERED);
//        }
        user = userSignUp(request);
        if (request.getAutoLogin()) {
            Map<String, Object> result = userSignIn(user, request.getApplicationId());
            return new CustomerSignUpResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS, result);
        }

        return new CustomerSignUpResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS);
    }

    /**
     * 登陆，验证授权和密码，返回token，保存授权信息到redis
     *
     * @param request
     * @return
     */
    @Transactional
    @Override
    public UserSignInResponse signIn(UserSignInRequest request) {
        Long applicationId = request.getApplicationId();
        if (null == applicationId) {
            return new UserSignInResponse(ResultCode.FAIL_CODE, "auth error");
        }
        Application application = applicationService.selectByPrimaryKey(applicationId);
        if (null == application) {
            return new UserSignInResponse(ResultCode.FAIL_CODE, "auth error");
        }

        User user = userDao.selectUserByLoginName(request.getLoginName());
        if (null == user) {
            return new UserSignInResponse(ResultCode.FAIL_CODE, "user or password error");
        }
        if (user.getStatus().intValue() == BaseCode.USER_OFF) {
            return new UserSignInResponse(ResultCode.FAIL_CODE, "user not enabled!");
        }
        //判断用户能否登陆此应用

        List<Long> applicationIds = userApplicationService.selectApplicationIdsByUser(user.getId());
        if (!applicationIds.contains(request.getApplicationId())) {
            return new UserSignInResponse(401, "The user does not have permission for the app");
        }

        Customer customer = customerService.selectByPrimaryKey(user.getCustomerId());
        if (customer.getCstatus().intValue() != BaseCode.CUSTOMER_ON) {
            return new UserSignInResponse(ResultCode.FAIL_CODE, "user not enabled!");
        }
        if (!user.getLoginPass().equalsIgnoreCase(
                UserUtil.encryptPassword(request.getPassword(), user.getLoginName()))) {
            return new UserSignInResponse(ResultCode.FAIL_CODE, "user or password error");
        }
        Map<String, Object> result = userSignIn(user, applicationId);
        CompletableFuture<Void> completableFuture = CompletableFuture.runAsync(new Runnable() {
            @Override
            public void run() {
                CustomerSyncUnpaidOrderRequest customerSyncUnpaidOrderRequest = new CustomerSyncUnpaidOrderRequest();
                customerSyncUnpaidOrderRequest.setCustomerId(customer.getId());
                omsFeignClient.customerSync(customerSyncUnpaidOrderRequest);
                List<Store> stores = storeService.selectStoreByCustomer(customer.getId());
                storeService.getStoreData(null,stores);
            }
        },threadPoolExecutor);
        return new UserSignInResponse(ResultCode.SUCCESS_CODE, "login success!", result);
    }

    /**
     *
     */
    public User selectByPrimaryKey(Long id) {
        return userDao.selectByPrimaryKey(id);
    }

    /**
     *
     */
    @Transactional
    public int updateByPrimaryKeySelective(User record) {
        return userDao.updateByPrimaryKeySelective(record);
    }

    /**
     *
     */
    @Transactional
    public int updateByPrimaryKey(User record) {
        return userDao.updateByPrimaryKey(record);
    }

    /**
     *
     */
    public List<User> select(Page<User> record) {
        record.initFromNum();
        return userDao.select(record);
    }

    /**
     *
     */
    public long count(Page<User> record) {
        return userDao.count(record);
    }

    @Transactional
    @Override
    public BaseResponse userRecoverPassword(UserRecoverPasswordRequest request) {
        String email = request.getEmail();

        User user = userDao.selectUserByLoginName(email);
        if (null == user) {
            return BaseResponse.failed("email error");
        }

        user = userDao.selectByPrimaryKey(user.getId());
        user.setLoginPass(UserUtil.encryptPassword(request.getNewPass(), user.getLoginName()));
        userDao.resetUserPassword(user);
        redisTemplate.opsForHash().delete(RedisKey.HASH_USER_NEED_RESET_PASSWTORD,user.getLoginName());
        Map<String, Object> result = userSignIn(user, request.getApplicationId());
        return new UserSignInResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS, result);
    }

    @Override
    public List<User> selectAllDefaultCustomerInfo() {
        return userDao.selectAllDefaultCustomerInfo();
    }


    public User userSignUp(CustomerSignUpRequest request) {
        Long applicationId = request.getApplicationId();

        Customer customer = request.toCustomer(needApprove);
        Long userId = customer.getId();
        customer.setCustomerSignupUserId(userId);
        customer.setVipLevel(0);
        customerService.insert(customer);

        //添加默认组织
        Organization organization = request.toOrganization(customer);
        organizationService.insert(organization);

        User user = request.toUser(customer);
        user.setId(userId);
        user.setIsDefault(true);
        insert(user);

        //添加默认角色
        Role role = roleService.selectApplicationDefaultRole(applicationId);
        if (null == role) {
            role = request.toRole(customer.getId());
            role.setApplicationId(applicationId);
            roleService.insertSelective(role);
        }
        Long roleId = role.getId();

        OrganizationMenu organizationMenu = new OrganizationMenu();
        organizationMenu.setOrgId(organization.getId());
        organizationMenu.setMenuId(0L);
        organizationMenuService.insert(organizationMenu);

        Account account = request.toAccount(customer);
        account.setName(user.getUsername());
        account.setStatus(1);
        account.setIsDefault(true);
        accountService.insert(account);

        CustomerApplication customerApplicationKey = new CustomerApplication();
        customerApplicationKey.setApplicationId(applicationId);
        customerApplicationKey.setCustomerId(customer.getId());
        customerApplicationService.insert(customerApplicationKey);
        userBindAccountOrgApp(userId,applicationId,account.getId(),organization.getId(),roleId);

        customerSettingService.saveNewSetting(customer.getId());

        UserVo userVo = new UserVo();
        BeanUtils.copyProperties(user,userVo);
        redisTemplate.opsForHash().put(RedisKey.STRING_CUSTOMER_INFO,String.valueOf(customer.getId()),userVo);

        CompletableFuture affiliateBind = CompletableFuture.runAsync(new Runnable() {
            @Override
            public void run() {
                affiliateService.affiliateBind(request.getReferrerToken(),user.getCustomerId());
            }
        },threadPoolExecutor);

        CompletableFuture inviteCodeBindCustomer = CompletableFuture.runAsync(new Runnable() {
            @Override
            public void run() {
                customerManagerService.inviteCodeBindCustomer(request.getManagerInviteToken(), customer.getId());
            }
        },threadPoolExecutor);

        try {
            CompletableFuture.allOf(affiliateBind,inviteCodeBindCustomer).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return user;
    }

    @Transactional
    @Override
    public BaseResponse addUser(UserAddRequest request, Session session) {
        Long customerId = session.getCustomerId();
        Long userId = IdGenerate.nextId();
        String pass = UserUtil.encryptPassword(request.getLoginPass(),request.getLoginName());
        User user = request.toUser(session.getCustomerId());
        user.setLoginPass(pass);
        user.setId(userId);
        user.setIsDefault(true);
        insert(user);

        Account account = accountService.selectCustomerDefaultAccount(customerId);

        userBindAccountOrgApp(userId,session.getApplicationId(),account.getId(),session.getParentOrganization().getId(), request.getRoleId());

        return BaseResponse.success();
    }

    @Override
    public void userBindAccountOrgApp(Long userId, Long applicationId, Long accountId, Long organizationId,Long roleId){
        AccountUser accountUser = new AccountUser();
        accountUser.setAccountId(accountId);
        accountUser.setUserId(userId);
        accountUserMapper.insert(accountUser);

        OrganizationUser organizationUser = new OrganizationUser();
        organizationUser.setOrgId(organizationId);
        organizationUser.setUserId(userId);
        organizationUserService.insert(organizationUser);

        UserApplication userApplicationKey = new UserApplication();
        userApplicationKey.setApplicationId(applicationId);
        userApplicationKey.setUserId(userId);
        userApplicationService.insert(userApplicationKey);

        //用户初始角色
        UserRole userRole = new UserRole();
        userRole.setRoleId(roleId);
        userRole.setUserId(userId);
        userRoleService.insert(userRole);
    }


    public Map<String, Object> userSignIn(User user, Long applicationId) {
        Long userId = user.getId();
        Map<String, Object> result = new HashMap<>();
        String token = TokenUtil.genToken(userId);
        result.put("token", token);
        UserVo userVo = new UserVo();
        BeanUtils.copyProperties(user, userVo);
        Session session = new Session(userVo);
        //组织
        List<Long> orgIds = organizationUserService.selectOrgIdsByUserId(userId);
        session.setOrgIds(orgIds);
        Organization organization = organizationUserService.selectUserParentOrganization(userId);
        if (organization != null) {
            OrganizationVo organizationVo = new OrganizationVo();
            BeanUtils.copyProperties(organization, organizationVo);
            session.setParentOrganization(organizationVo);
        }
        //角色权限
        Role role = roleService.selectRoleByUser(userId);
        RoleVo roleVo = new RoleVo();
        BeanUtils.copyProperties(role, roleVo);
        session.setApplicationId(applicationId);
        session.setRole(roleVo);

        List<String> permissions = rolePermissionService.selectPermissionByRole(role.getId());
        session.setPermissions(permissions);

        Account account = accountService.selectCustomerDefaultAccount(session.getCustomerId());
        if (null != account) {
            session.setAccountId(account.getId());
        }
//        UserInfo userInfo = userInfoService.selectByPrimaryKey(userId);
//        session.setUserName(userInfo.getUsername());
//        session.setLoginpass(user.getLoginPass());
        UserUtil.setUser(redisTemplate, token, session,applicationId);
        user.setLastLoginTime(new Date());
        userDao.refreshLoginData(user);
        Boolean guideNotice = guideNotice(userId);
        result.put("guideNotice",guideNotice);
        return result;
    }

    @Override
    public Map<String, Object> userSignIn(User user, Long applicationId,Integer source) {
        Long userId = user.getId();
        Map<String, Object> result = new HashMap<>();
        String token = String.valueOf(IdGenerate.nextId());
        result.put("token", token);
        UserVo userVo = new UserVo();
        BeanUtils.copyProperties(user, userVo);
        Session session = new Session(userVo);
        //组织
        List<Long> orgIds = organizationUserService.selectOrgIdsByUserId(userId);
        session.setOrgIds(orgIds);
        Organization organization = organizationUserService.selectUserParentOrganization(userId);
        if (organization != null) {
            OrganizationVo organizationVo = new OrganizationVo();
            BeanUtils.copyProperties(organization, organizationVo);
            session.setParentOrganization(organizationVo);
        }
        //角色权限
        Role role = roleService.selectRoleByUser(userId);
        RoleVo roleVo = new RoleVo();
        BeanUtils.copyProperties(role, roleVo);
        session.setApplicationId(applicationId);
        session.setRole(roleVo);

        List<String> permissions = rolePermissionService.selectPermissionByRole(role.getId());
        session.setPermissions(permissions);

        Account account = accountService.selectCustomerDefaultAccount(session.getCustomerId());
        if (null != account) {
            session.setAccountId(account.getId());
        }
        session.setUserName(user.getUsername());
        session.setLoginpass(user.getLoginPass());
        UserUtil.setUser(redisTemplate, token, session);
        result.put("guideNotice",false);
        return result;
    }


    public Boolean guideNotice(Long userId){
        Long createTime = (Long) redisTemplate.opsForHash().get(RedisKey.HASH_CUSTOMER_LOGIN_NOTICE,userId.toString());
        if (createTime != null){
            return false;
        }
        createTime = System.currentTimeMillis();
        redisTemplate.opsForHash().put(RedisKey.HASH_CUSTOMER_LOGIN_NOTICE,userId.toString(),createTime);
        return true;
    }

//    public List<CustomerSetting> saveCustomerSetting(Long customerId) {
//
//        List<CustomerSetting> settings = customerSettingService.selectSettingByCustomerId(customerId);
//
//        List<String> settingNames = new ArrayList<>();
//
//        settings.forEach(customerSetting -> {
//            settingNames.add(customerSetting.getSettingName());
//        });
//
//        List<CustomerSetting> customerSettings = new ArrayList<>();
//        for (CustomerSettingEnum customerSettingEnum : CustomerSettingEnum.values()) {
//            if (settingNames.contains(customerSettingEnum.name())) {
//                continue;
//            }
//            CustomerSetting customerSetting = new CustomerSetting();
//            customerSetting.setCustomerId(customerId);
//            customerSetting.setSettingName(customerSettingEnum.name());
//            customerSetting.setSettingValue(customerSettingEnum.getValue());
//            customerSettings.add(customerSetting);
//        }
//        if (0 < customerSettings.size()) {
//            customerSettingService.insertByBatch(customerSettings);
//        }
//        return customerSettings;
//    }
}