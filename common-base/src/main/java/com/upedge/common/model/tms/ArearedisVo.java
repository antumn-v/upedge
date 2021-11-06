package com.upedge.common.model.tms;

import lombok.Data;

import java.util.Date;

@Data
public class ArearedisVo {
    /**
     *
     */
    private Long id;
    /**
     * 地区名称
     */
    private String name;
    /**
     * 英文名
     */
    private String enName;
    /**
     * 地区代码
     */
    private String areaCode;
    /**
     *
     */
    private Date updateTime;
}
