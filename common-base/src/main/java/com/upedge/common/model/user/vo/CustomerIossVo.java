package com.upedge.common.model.user.vo;

import lombok.Data;

import java.util.Date;

@Data
public class CustomerIossVo {
    private Long id;
    /**
     *
     */
    private Long customerId;
    /**
     *
     */
    private String iossNum;
    /**
     *
     */
    private Integer state;
    /**
     *
     */
    private Date createTime;
    /**
     *
     */
    private Date updateTime;
}
