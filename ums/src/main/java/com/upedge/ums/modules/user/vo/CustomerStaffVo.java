package com.upedge.ums.modules.user.vo;

import lombok.Data;

import java.util.Date;

@Data
public class CustomerStaffVo {

    private String staffName;

    private String staffLoginName;

    private Date createTime;

    private int state;

}
