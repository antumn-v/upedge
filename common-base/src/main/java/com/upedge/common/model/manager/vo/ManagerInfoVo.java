package com.upedge.common.model.manager.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class ManagerInfoVo {

    private Long id;
    /**
     *
     */
    private Long userId;
    /**
     * 赛盒来源渠道id
     */
    private Integer orderSourceId;
    /**
     * 赛盒来源渠道名称
     */
    private String orderSourceName;
    /**
     *
     */
    private String managerCode;
    /**
     * 0=客户经理，1=助理
     */
    private Integer managerType;
    /**
     * 1=正常 0=停用,2=删除
     */
    private Integer managerState;
    /**
     * 客户经理英文名
     */
    private String managerName;
    /**
     * 助理所属的客户经理代码
     */
    private String assistantSuperior;
    /**
     *
     */
    private Date createTime;
    /**
     * 创建者
     */
    private Long creatorId;
    /**
     * 邀请注册码
     */
    private String inviteCode;

    private BigDecimal perCommission;

    private String avatar;
    /**
     *
     */
    private String mobile;
    /**
     *
     */
    private String email;
    /**
     *
     */
    private String whatsapp;
    /**
     *
     */
    private String wechat;
    /**
     *
     */
    private String fbInfo;
    /**
     *
     */
    private String skype;
}
