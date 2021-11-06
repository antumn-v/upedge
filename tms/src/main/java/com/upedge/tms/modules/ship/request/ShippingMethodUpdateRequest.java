package com.upedge.tms.modules.ship.request;

import com.upedge.tms.modules.ship.entity.ShippingMethod;
import lombok.Data;

/**
 * @author author
 */
@Data
public class ShippingMethodUpdateRequest{

    /**
     * 运输方式名称
     */
    private String name;
    /**
     * 描述
     */
    private String desc;
    /**
     * 
     */
    private Integer saiheTransportId;
    /**
     * 
     */
    private String saiheTransportName;
    /**
     * 
     */
    private String trackingUrl;
    /**
     * 0:实重 1:体积重
     */
    private Integer weightType;
    /**
     * 追踪类型 0:真实追踪号 1:物流商单号
     */
    private Integer trackType;
    /**
     * 
     */
    private String paypalCarrierEnum;

    public ShippingMethod toShippingMethod(Long id){
        ShippingMethod shippingMethod=new ShippingMethod();
        shippingMethod.setId(id);
        shippingMethod.setName(name);
        shippingMethod.setDesc(desc);
        shippingMethod.setSaiheTransportId(saiheTransportId);
        shippingMethod.setSaiheTransportName(saiheTransportName);
        shippingMethod.setTrackingUrl(trackingUrl);
        shippingMethod.setWeightType(weightType);
        shippingMethod.setTrackType(trackType);
        shippingMethod.setPaypalCarrierEnum(paypalCarrierEnum);
        return shippingMethod;
    }

}
