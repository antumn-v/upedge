package com.upedge.tms.modules.ship.request;

import com.upedge.common.utils.IdGenerate;
import com.upedge.tms.modules.ship.entity.ShippingTemplate;
import lombok.Data;

import java.util.Date;

/**
 * @author author
 */
@Data
public class ShippingTemplateAddRequest{

    /**
    * 模板名称
    */
    private String name;
    /**
    * 描述
    */
    private String desc;
    /**
    * 1:启用 0:禁用
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

    private Integer saiheId;

    private Integer seq;

    public ShippingTemplate toShippingTemplate(){
        ShippingTemplate shippingTemplate=new ShippingTemplate();
        shippingTemplate.setId(IdGenerate.nextId());
        shippingTemplate.setName(name);
        shippingTemplate.setDesc(desc);
        shippingTemplate.setState(1);
        shippingTemplate.setCreateTime(new Date());
        shippingTemplate.setUpdateTime(new Date());
        shippingTemplate.setSaiheId(saiheId);
        shippingTemplate.setSeq(seq);
        return shippingTemplate;
    }

}
