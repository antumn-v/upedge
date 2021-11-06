package com.upedge.common.model.manager.vo;

import lombok.Data;

@Data
public class ManagerInfoVo {

    private Long customerSignupUserId;

    private String managerCode;

    private Long customerId;


    private String managerName;
    /**
     * 赛盒来源渠道id
     */
    private Integer orderSourceId;
    /**
     * 赛盒来源渠道名称
     */
    private String orderSourceName;

    /**
     * 0=客户经理，1=助理
     */
    private Integer managerType;
    /**
     * 1=正常 0=停用
     */
    private Integer managerState;
    /**
     * 助理所属的客户经理ID
     */
    private String assistantSupeior;
}
