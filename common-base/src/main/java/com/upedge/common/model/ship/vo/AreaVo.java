package com.upedge.common.model.ship.vo;

import lombok.Data;

@Data
public class AreaVo {

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

}
