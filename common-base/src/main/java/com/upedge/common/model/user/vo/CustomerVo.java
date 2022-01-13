package com.upedge.common.model.user.vo;

import lombok.Data;

import java.util.Date;

@Data
public class CustomerVo extends UserInfoVo {

    private String loginName;

    private String customerManager;

    private Date createTime;


    private Long id;
    /**
     *
     */
    private String cname;
    /**
     * 0 正常 1 未激活 2 锁定 3注销
     */
    private Integer cstatus;
    /**
     * 客户注册后创建的默认用户ID
     */
    private Long customerSignupUserId;

}
