package com.upedge.ums.modules.account.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class RecordVo {
    /**
     *
     */
    private Integer id;
    /**
     *
     */
    private Long relateId;
    /**
     *
     */
    private BigDecimal amount= BigDecimal.ZERO;
    /**
     *
     */
    private Date createTime = new Date();

    private Integer seq;

    private Integer source;

}
