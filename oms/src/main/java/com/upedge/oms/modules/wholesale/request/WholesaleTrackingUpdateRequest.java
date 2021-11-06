package com.upedge.oms.modules.wholesale.request;

import com.upedge.oms.modules.wholesale.entity.WholesaleTracking;
import lombok.Data;

import java.util.Date;

/**
 * @author author
 */
@Data
public class WholesaleTrackingUpdateRequest{

    /**
     * 回传单号
     */
    private String trackingCode;
    /**
     * 
     */
    private Date updateTime;
    /**
     * 订单ID
     */
    private Long orderId;
    /**
     * 
     */
    private String shippingMethodName;
    /**
     * 追踪类型 0:真实追踪号 1:物流商单号
     */
    private Integer trackType;
    /**
     * 赛盒运输id
     */
    private Integer transportId;
    /**
     * 发货时间
     */
    private Date createTime;

    public WholesaleTracking toWholesaleTracking(Long id){
        WholesaleTracking wholesaleTracking=new WholesaleTracking();
        wholesaleTracking.setId(id);
        wholesaleTracking.setTrackingCode(trackingCode);
        wholesaleTracking.setUpdateTime(updateTime);
        wholesaleTracking.setOrderId(orderId);
        wholesaleTracking.setShippingMethodName(shippingMethodName);
        wholesaleTracking.setTrackType(trackType);
        wholesaleTracking.setTransportId(transportId);
        wholesaleTracking.setCreateTime(createTime);
        return wholesaleTracking;
    }

}
