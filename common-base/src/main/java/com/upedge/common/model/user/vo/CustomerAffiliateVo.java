package com.upedge.common.model.user.vo;

import lombok.Data;

@Data
public class CustomerAffiliateVo {

    /**
     * 推荐人名称
     */
    private String affiliateName;

    /**
     * 推荐人登录名
     */
    private String affiliateLoginName;

    /**
     * 推荐人customerId
     */
    private Long affiliateId;

    /**
     * 推荐人邮箱
     */
    private String affiliateEmail;
}
