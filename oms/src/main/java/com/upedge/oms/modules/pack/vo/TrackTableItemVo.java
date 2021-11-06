package com.upedge.oms.modules.pack.vo;

import lombok.Data;

/**
 *
 */
@Data
public class TrackTableItemVo {


    /**
     * 运输方式名称
     */
    String transportName;
    /**
     * APP shipping method
     */
    String shippingMethod;
    /**
     * 赛盒运输id
     */
    Integer transportId;
    /**
     * 运输状态
     */
    String trackStatus;
    /**
     * 数量
     */
    Integer count;


}
