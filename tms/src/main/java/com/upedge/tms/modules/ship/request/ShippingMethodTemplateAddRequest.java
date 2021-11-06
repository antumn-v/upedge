package com.upedge.tms.modules.ship.request;

import com.upedge.common.utils.IdGenerate;
import com.upedge.tms.modules.ship.entity.ShippingMethodTemplate;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author author
 */
@Data
public class ShippingMethodTemplateAddRequest{

    /**
    * 可以运输的模板id
    */
    @NotNull
    private Long shippingId;
    /**
    * 运输方式id
    */
    @NotNull
    private Long methodId;
    /**
    * 
    */
    @NotNull
    private Integer sort;

    public ShippingMethodTemplate toShippingMethodTemplate(){
        ShippingMethodTemplate shippingMethodTemplate=new ShippingMethodTemplate();
        shippingMethodTemplate.setId(IdGenerate.nextId());
        shippingMethodTemplate.setShippingId(shippingId);
        shippingMethodTemplate.setMethodId(methodId);
        shippingMethodTemplate.setSort(sort);
        return shippingMethodTemplate;
    }

}
