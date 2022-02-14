package com.upedge.tms.modules.ship.request;

import com.upedge.tms.modules.ship.entity.ShippingMethod;
import lombok.Data;

import javax.validation.constraints.Size;
import java.util.List;

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

    private String trackingCompany;
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

    private String warehouseCode;

    @Size(min = 1)
    private List<Long> templateIds;

    public ShippingMethod toShippingMethod(Long id){
        ShippingMethod shippingMethod=new ShippingMethod();
        shippingMethod.setId(id);
        shippingMethod.setName(name);
        shippingMethod.setDesc(desc);
        shippingMethod.setSaiheTransportId(saiheTransportId);
        shippingMethod.setSaiheTransportName(saiheTransportName);
        shippingMethod.setTrackingUrl(trackingUrl);
        shippingMethod.setTrackingCompany(trackingCompany);
        shippingMethod.setWeightType(weightType);
        shippingMethod.setTrackType(trackType);
        shippingMethod.setPaypalCarrierEnum(paypalCarrierEnum);
        return shippingMethod;
    }

}
