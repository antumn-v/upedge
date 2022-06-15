package com.upedge.common.model.user.vo;

import lombok.Data;

import java.util.Date;

/**
 * Created by guoxing on 2020/11/9.
 */
@Data
public class UserVo  {
    private Long id;

    private Long customerId;

    private String loginName;

    private Integer userType;

    private Integer status;

    private Date createTime;

    private Date updateTime;

    private String remark;

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


}
