package com.upedge.ums.modules.user.request;


import com.upedge.common.constant.BaseCode;
import com.upedge.common.constant.Constant;
import com.upedge.common.utils.IdGenerate;
import com.upedge.common.web.util.UserUtil;
import com.upedge.ums.modules.account.entity.Account;
import com.upedge.ums.modules.organization.entity.Organization;
import com.upedge.ums.modules.user.entity.Customer;
import com.upedge.ums.modules.user.entity.Role;
import com.upedge.ums.modules.user.entity.User;
import com.upedge.ums.modules.user.entity.UserInfo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class CustomerSignUpRequest {

    /**
     * 组织名称
     */
    private String orgName;

    /**
     * 登录名
     */
    @NotNull
    private String loginName;

    /**
     * 密码
     */
    @NotNull
    private String password;

    /**
     * 真实姓名
     */
    private String username;

    /**
     * 邮件-用于收取通知
     */
    private String email;

    /**
     * 移动电话-用于收取短信
     */
    private String mobile;

    /**
     * 国家编码
     */
    private String country;

    /**
     * 证件类型
     */
    private int idtype;

    /**
     * 证件号码
     */
    private String idnum;

    /**
     * 注册时间
     */
    private Date createTime = new Date();

    /**
     * 邀请码-未必生效
     */
    private String permission = null;

    /**
     * 应用编码
     */
    @NotNull
    private Long applicationId;

    @ApiModelProperty(value = "注册成功后是否直接登陆,默认为true",required = false)
    private Boolean autoLogin = true;

    private String state;

    private String managerCode;

    private String referrerToken;

    private String managerInviteToken;

    /**
     * 获取注册用的Customer对象
     * @return
     */
    public Customer toCustomer(Boolean needApprove) {
        Customer customer = new Customer();
        customer.setId(IdGenerate.nextId());
        customer.setCname(this.username);
        customer.setCstatus(needApprove ? BaseCode.CUSTOMER_WAITING : BaseCode.CUSTOMER_ON);
        customer.setCreateTime(this.createTime);
        return customer;
    }

    /**
     * 获取注册用的部门信息
     * @return
     */
    public Organization toOrganization(Customer customer) {
        Organization organization = new Organization();
        organization.setId(IdGenerate.nextId());
        organization.setCustomerId(customer.getId());
        organization.setOrgName(orgName);
        organization.setOrgParent(Constant.ROOT_ORGANIZATION);
        organization.setOrgPath(String.valueOf(customer.getId()));
        organization.setCreateTime(customer.getCreateTime());
        organization.setUpdateTime(customer.getCreateTime());
        return organization;
    }

    /**
     * 获取注册用的角色信息
     * @param customer
     * @param organization
     * @return
     */
    public Role toRole(Long customerId) {
        Role role = new Role();
        role.setId(IdGenerate.nextId());
        role.setCustomerId(customerId);
        role.setRoleName(Constant.ORG_DEFAULT_ROLE);
        role.setCreateTime(this.createTime);
        role.setUpdateTime(this.createTime);
        return role;
    }

    /**
     * 获取注册用的登录信息
     * @param customer
     * @return
     */
    public User toUser(Customer customer) {
        User user = new User();
        user.setId(IdGenerate.nextId());
        user.setLoginName(loginName);
        user.setLoginPass(UserUtil.encryptPassword(password,loginName));
        user.setCustomerId(customer.getId());
        user.setUserType(BaseCode.USER_ROLE_ADMIN);
        user.setStatus(BaseCode.USER_ON);
        user.setCountry(country);
        user.setAvatar(Constant.AVATAR);
        user.setEmail(email);
        user.setMobile(mobile);
        user.setUsername(username);
        user.setLoginCount(0);
        user.setCreateTime(this.createTime);
        user.setUpdateTime(this.createTime);
        user.setLastLoginTime(new Date());
        return user;
    }

    /**
     * 获取注册用的用户信息
     * @param user
     * @return
     */
    public UserInfo toUserInfo(User user) {
        UserInfo userInfo = new UserInfo();
        userInfo.setId(user.getId());
        userInfo.setCountry(country);
        userInfo.setAvatar(Constant.AVATAR);
        userInfo.setCustomerId(user.getCustomerId());
        userInfo.setEmail(email);
        userInfo.setMobile(mobile);
        userInfo.setUsername(username);
        userInfo.setCreateTime(this.createTime);
        userInfo.setUpdateTime(this.createTime);
        return userInfo;
    }

    /**
     * 获取注册用的账户信息
     * @param customer
     * @return
     */
    public Account toAccount(Customer customer) {
        Account account = new Account();
        account.setId(IdGenerate.nextId());
        account.setCustomerId(customer.getId());
        account.setBalance(new BigDecimal(0));
        account.setCredit(new BigDecimal(0));
        account.setAffiliateRebate(new BigDecimal(0));
        account.setVipRebate(BigDecimal.ZERO);
        return account;
    }



}
