package com.upedge.ums.modules.affiliate.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class AdminAffiliateVo {

    /**
     *
     */
    private Long id;
    /**
     * 推荐人ID
     */
    private Long referrerId;
    /**
     * 推荐人姓名
     */
    private String referrerName;
    /**
     * 推荐人邮箱
     */
    private String referrerEmail;
    /**
     *推荐人账号
     */
    private String referrerLoginName;

    /**
     * 被推荐人ID
     */
    private Long refereeId;
    /**
     * 被推荐人姓名
     */
    private String refereeName;
    /**
     * 被推荐人邮箱
     */
    private String refereeEmail;
    /**
     *被推荐人账号
     */
    private String refereeLoginName;
    /**
     * 被推荐人的提成
     */
    private BigDecimal refereeCommission;
    /**
     *
     */
    private Date createTime;
    /**
     *
     */
    private Date updateTime;
    /**
     * 来源 0:app 1:admin
     */
    private Integer source;

}
