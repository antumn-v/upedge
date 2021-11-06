package com.upedge.ums.modules.manager.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ManagerCreateRequest {

    @NotNull
    private String loginName;

    @NotNull
    private String userName;

    @NotNull
    private String password;

    /**
     * 0=客户经理，1=助理
     */
    @NotNull
    private Integer managerType;


    private Integer orderSourceId;
    /**
     * 赛盒来源渠道名称
     */
    private String orderSourceName;
    /**
     * 客户经理别名
     */
    private String managerName;
    /**
     * 助理所属的客户经理ID
     */
    private String assistantSupeior;

    private String managerCode;

    private String skype;
    private String whatsapp;
    private String wechat;
    private String facebook;

}
