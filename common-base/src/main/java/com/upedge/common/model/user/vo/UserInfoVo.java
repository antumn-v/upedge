package com.upedge.common.model.user.vo;

import lombok.Data;

import java.util.Date;

/**
 * Created by jiaqi on 2020/11/9.
 */
@Data
public class UserInfoVo {

    private Long id;

    public Long customerId;

    public Long orgId;

    public String username;

    public String avatar;

    public String mobile;

    public String email;

    public String country;

    public String fbInfo;

    public String skype;

    public String wechat;

    public String whatsapp;

    private Date createTime;

    private Date updateTime;
}
