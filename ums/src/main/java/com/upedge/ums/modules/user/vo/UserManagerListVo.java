package com.upedge.ums.modules.user.vo;

import lombok.Data;

import java.util.Date;

@Data
public class UserManagerListVo {

    private Long id;

    private Long customerId;

    private String userName;

    private String loginName;

    private Integer status;

    private Date createTime;

    private Date lastLoginTime;

    private Integer loginCount;

    private String customerManager;

    /**
     *
     */
    private String customerName;
    /**
     *
     */
    private String fbInfo;
    /**
     *
     */
    private String skype;
    /**
     *
     */
    private String whatsapp;
    /**
     *
     */
    private String wechat;
    /**
     * 备注
     */
    private String remark;


}
