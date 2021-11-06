package com.upedge.tms.modules.ship.request;

import com.upedge.tms.modules.ship.entity.ShippingAttribute;
import lombok.Data;

/**
 * @author author
 */
@Data
public class ShippingAttributeAddRequest{

    /**
    * 物流属性名称
    */
    private String attributeName;
    /**
    * 描述
    */
    private String desc;
    /**
    * 物流属性关联运输模板
    */
    private Long shippingId;
    /**
    * 赛盒属性id
    */
    private Integer saiheId;

    public ShippingAttribute toShippingAttribute(){
        ShippingAttribute shippingAttribute=new ShippingAttribute();
        shippingAttribute.setAttributeName(attributeName);
        shippingAttribute.setDesc(desc);
        shippingAttribute.setShippingId(shippingId);
        shippingAttribute.setSaiheId(saiheId);
        return shippingAttribute;
    }

}
