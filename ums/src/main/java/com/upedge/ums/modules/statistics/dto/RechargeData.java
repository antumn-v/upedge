package com.upedge.ums.modules.statistics.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class RechargeData {


    Long id;

    //客户姓名
    String userName;

    //客户账号
    String loginName;

    //客户id
    Long customerId;

    //充值金额
    BigDecimal amount;

    //类型
    Integer rechargeType;

    //提交时间
    Date createTime;

    //状态 0=审核中，3=payoneer待提交付款请求
    Integer status;

    String payUrl;

}
