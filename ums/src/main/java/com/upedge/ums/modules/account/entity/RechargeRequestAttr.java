package com.upedge.ums.modules.account.entity;

import lombok.Data;

@Data
public class RechargeRequestAttr {
    private Integer id;

    private Long rechargeRequestId;

    private String attrName;

    private String attrValue;


}