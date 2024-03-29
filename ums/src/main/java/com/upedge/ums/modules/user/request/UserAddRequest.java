package com.upedge.ums.modules.user.request;

import com.upedge.ums.modules.user.entity.User;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
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
    /**
    * 0 超级管理员 1管理员 2普通用户
    */
    @NotNull
    private Integer userType;
    /**
    * 1 正常 0 停用
    */
    @NotNull
    private Integer status;
    /**
    * 姓名
    */
    @NotBlank
    private String username;

    @NotNull
    private Long roleId;
    /**
    * 
    */
    private String avatar;
    /**
    * 
    */
    private String mobile;
    /**
    * 
    */
    private String email;
    /**
    * 国家
    */
    private String country;
    /**
    * 
    */
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
    /**
    * 1=男，0=女
    */
    private Integer sex;
    /**
    * 登录次数
    */
    private Integer loginCount;
    /**
    * 
    */
    private Date createTime;
    /**
    * 
    */
    private Date updateTime;
    /**
    * 最近登录时间
    */
    private Date lastLoginTime;

    public User toUser(Long customerId){
        User user=new User();
        user.setCustomerId(customerId);
        user.setLoginName(loginName);
        user.setLoginPass(loginPass);
        user.setUserType(userType);
        user.setStatus(status);
        user.setUsername(username);
        user.setAvatar(avatar);
        user.setMobile(mobile);
        user.setEmail(email);
        user.setCountry(country);
        user.setWhatsapp(whatsapp);
        user.setWechat(wechat);
        user.setFbInfo(fbInfo);
        user.setSkype(skype);
        user.setSex(sex);
        user.setLoginCount(0);
        user.setCreateTime(new Date());
        user.setUpdateTime(new Date());
        user.setLastLoginTime(new Date());
        return user;
    }

}
