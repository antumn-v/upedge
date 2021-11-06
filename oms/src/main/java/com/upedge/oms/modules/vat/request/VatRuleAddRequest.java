package com.upedge.oms.modules.vat.request;

import com.upedge.oms.modules.vat.entity.VatRule;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author author
 */
@Data
public class VatRuleAddRequest{

    /**
    * 最低价格
    */
    @NotNull
    private BigDecimal minAmount;
    /**
    * 最高价格
    */
    @NotNull
    private BigDecimal maxAmount;
    /**
    * 申请方式   1订单金额（订单产品金额+运费金额）      2订单产品金额
    */
    @NotNull
    private Integer methodType;
    /**
    * 申报比例
    */
    @NotNull
    private BigDecimal ratio;

    public VatRule toVatRule(String adminUser){
        VatRule vatRule=new VatRule();
        vatRule.setMinAmount(minAmount);
        vatRule.setMaxAmount(maxAmount);
        vatRule.setMethodType(methodType);
        vatRule.setRatio(ratio);
        vatRule.setCurrency("USD");
        vatRule.setCreateTime(new Date());
        vatRule.setUpdateTime(new Date());
        vatRule.setAdminUser(adminUser);
        return vatRule;
    }

}
