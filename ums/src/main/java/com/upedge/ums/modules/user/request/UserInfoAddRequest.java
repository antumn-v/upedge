package com.upedge.ums.modules.user.request;

import com.upedge.common.base.Page;
import com.upedge.ums.modules.user.entity.UserInfo;
import java.util.Date;
import lombok.Data;
/**
 * @author gx
 */
@Data
public class UserInfoAddRequest{

    /**
    * 
    */
    private Long customerId;
    /**
    * 
    */
    private Long orgId;
    /**
    * 姓名
    */
    private String username;
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
    private Date createTime;
    /**
    * 
    */
    private Date updateTime;
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

    public UserInfo toUserInfo(){
        UserInfo userInfo=new UserInfo();
        userInfo.setCustomerId(customerId);
        userInfo.setOrgId(orgId);
        userInfo.setUsername(username);
        userInfo.setAvatar(avatar);
        userInfo.setMobile(mobile);
        userInfo.setEmail(email);
        userInfo.setCountry(country);
        userInfo.setCreateTime(createTime);
        userInfo.setUpdateTime(updateTime);
        userInfo.setWhatsapp(whatsapp);
        userInfo.setWechat(wechat);
        userInfo.setFbInfo(fbInfo);
        userInfo.setSkype(skype);
        userInfo.setSex(sex);
        return userInfo;
    }

}
