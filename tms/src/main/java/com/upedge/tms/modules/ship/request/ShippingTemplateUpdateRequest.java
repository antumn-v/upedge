package com.upedge.tms.modules.ship.request;

import com.upedge.tms.modules.ship.entity.ShippingTemplate;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.Date;

/**
 * @author author
 */
@Data
public class ShippingTemplateUpdateRequest{

    /**
     * 模板名称
     */
    @NotBlank
    private String name;
    /**
     * 描述
     */

    private String desc;

    private Integer saiheId;

    private Integer seq;

    public ShippingTemplate toShippingTemplate(Long id){
        ShippingTemplate shippingTemplate=new ShippingTemplate();
        shippingTemplate.setId(id);
        shippingTemplate.setName(name);
        shippingTemplate.setDesc(desc);
        shippingTemplate.setSaiheId(saiheId);
        shippingTemplate.setUpdateTime(new Date());
        shippingTemplate.setSeq(seq);
        return shippingTemplate;
    }

}
