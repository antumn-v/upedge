package com.upedge.tms.modules.ship.vo;

import lombok.Data;

@Data
public class MethodTemplateVo {

    /**
     *
     */
    private Long id;
    /**
     * 运输模板id
     */
    private Long shippingId;
    /**
     * 运输方式id
     */
    private Long methodId;
    /**
     *运输方式名称
     */
    private String methodName;

    private String description;
    /**
     *排序
     */
    private Integer sort;

    private String shipName;
}
