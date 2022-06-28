package com.upedge.oms.modules.vat.request;

import com.upedge.oms.modules.vat.entity.VatRule;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author author
 */
@Data
public class VatRuleUpdateRequest{

    /**
     * 最低价格
     */
    private BigDecimal minAmount;
    /**
     * 最高价格
     */
    private BigDecimal maxAmount;
    /**
     * 申请方式   1订单金额（订单产品金额+运费金额）      2订单产品金额
     */
    private Integer methodType;
    /**
     * 申报比例
     */
    private BigDecimal ratio;
    /**
     * 货币
     */
    private String currency;
    /**
     * 
     */
    private String adminUser;

    private Integer vatType;

    public VatRule toVatRule(Long id, Long userCode){
        VatRule vatRule=new VatRule();
        vatRule.setId(id);
        vatRule.setMinAmount(minAmount);
        vatRule.setMaxAmount(maxAmount);
        vatRule.setMethodType(methodType);
        vatRule.setRatio(ratio);
        vatRule.setUpdateTime(new Date());
        vatRule.setCreatorId(userCode);
        vatRule.setVatType(vatType);
        vatRule.setCurrency("USD");
        return vatRule;
    }

}
