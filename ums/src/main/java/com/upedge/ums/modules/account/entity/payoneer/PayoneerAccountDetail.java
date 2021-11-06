package com.upedge.ums.modules.account.entity.payoneer;

import lombok.Data;

import java.util.Date;

@Data
public class PayoneerAccountDetail {
    private Integer id;

    private String accountId;

    private String first_name;
    private String last_name;

    private String email;

    private String mobile;

    private String phone;

    private Date createTime;


}