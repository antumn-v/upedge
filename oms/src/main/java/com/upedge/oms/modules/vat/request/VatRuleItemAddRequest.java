package com.upedge.oms.modules.vat.request;

import com.upedge.oms.modules.vat.entity.VatRuleItem;
import lombok.Data;

import java.util.Date;

/**
 * @author author
 */
@Data
public class VatRuleItemAddRequest{

    /**
    * 
    */
    private Long ruleId;
    /**
    * 
    */
    private Long areaId;
    /**
    * 
    */
    private Date createTime;
    /**
    * 
    */
    private Date updateTime;

    public VatRuleItem toVatRuleItem(){
        VatRuleItem vatRuleItem=new VatRuleItem();
        vatRuleItem.setRuleId(ruleId);
        vatRuleItem.setAreaId(areaId);
        vatRuleItem.setCreateTime(createTime);
        vatRuleItem.setUpdateTime(updateTime);
        return vatRuleItem;
    }

}
