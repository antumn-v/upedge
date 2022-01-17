package com.upedge.tms.modules.ship.request;

import com.upedge.tms.modules.ship.entity.ShippingMethod;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

/**
 * @author author
 */
@Data
public class ShippingMethodAddRequest{

    /**
    * 运输方式名称
    */
    @NotBlank
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
     * 物流公司
     */
    private String trackingCompany;
    /**
    * 0:实重 1:体积重
    */
    @NotNull
    private Integer weightType;
    /**
    * 追踪类型 0:真实追踪号 1:物流商单号
    */
    @NotNull
    private Integer trackType;
    /**
    * 
    */
    private String paypalCarrierEnum;
    /**
    * 0:禁用 1:启用
    */
    private Integer state;
    /**
    * 创建时间
    */
    private Date createTime;
    /**
    * 更新时间
    */
    private Date updateTime;

    @Size(min = 1)
    private List<Long> templateIds;

    public ShippingMethod toShippingMethod(){
        ShippingMethod shippingMethod=new ShippingMethod();
        shippingMethod.setName(name);
        shippingMethod.setDesc(desc);
        shippingMethod.setSaiheTransportId(saiheTransportId);
        shippingMethod.setSaiheTransportName(saiheTransportName);
        shippingMethod.setTrackingUrl(trackingUrl);
        shippingMethod.setTrackingCompany(trackingCompany);
        shippingMethod.setWeightType(weightType);
        shippingMethod.setTrackType(trackType);
        shippingMethod.setPaypalCarrierEnum(paypalCarrierEnum);
        shippingMethod.setState(state);
        shippingMethod.setCreateTime(new Date());
        shippingMethod.setUpdateTime(updateTime);
        return shippingMethod;
    }

}
