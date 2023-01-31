package com.upedge.ums.modules.affiliate.vo;

import lombok.Data;

@Data
public class RefereeMonthCommissionVo {

    private Long refereeId;

    private String createTime;

    private Double commission;

    private long orderCount;
}
