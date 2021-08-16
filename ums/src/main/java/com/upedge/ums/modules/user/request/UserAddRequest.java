package com.upedge.ums.modules.user.request;

import com.upedge.common.base.Page;
import com.upedge.ums.modules.user.entity.User;
import java.util.Date;

import com.upedge.ums.modules.user.entity.UserInfo;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author gx
 */
@Data
public class UserAddRequest{


    /**
    * 
    */
    @NotBlank
    private String loginName;
    /**
    * 
    */
    @NotBlank
    private String loginPass;

    @NotBlank
    private String username;
    /**
    * 0 超级管理员 1管理员 2普通用户
    */
    private Integer userType;
    /**
    * 1 正常 0 停用
    */
    private Integer status;
    /**
    * 
    */
    private Date createTime;

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
    * 
    */
    private Date updateTime;
    @NotNull
    private Long orgId;
    @NotNull
    private Long roleId;

    private String whatsapp;
    /**
     *
     */
    private String wechat;
    /**
     *
     */
    private String fbInfo;
    /**
     *
     */
    private String skype;


    public User toUser(Long customerId){
        Date date = new Date();
        User user=new User();
        user.setCustomerId(customerId);
        user.setLoginName(loginName);
        user.setLoginPass(loginPass);
        user.setUserType(userType);
        user.setStatus(status);
        user.setCreateTime(date);
        user.setUpdateTime(date);
        user.setLastLoginTime(null);
        user.setLoginCount(0);
        return user;
    }

    public UserInfo toUserInfo(Long customerId) {
        UserInfo userInfo = new UserInfo();
        userInfo.setAvatar(null);
        userInfo.setCountry(this.country);
        userInfo.setCustomerId(customerId);
        userInfo.setOrgId(orgId);
        userInfo.setEmail(this.email);
        userInfo.setMobile(this.mobile);
        userInfo.setUsername(this.username);
        userInfo.setCreateTime(new Date());
        userInfo.setUpdateTime(new Date());
        userInfo.setWhatsapp(whatsapp);
        userInfo.setWechat(wechat);
        userInfo.setFbInfo(fbInfo);
        userInfo.setSkype(skype);
        return userInfo;
    }

}
