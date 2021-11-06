package com.upedge.oms.modules.rules.request;

import com.upedge.oms.modules.rules.entity.OrderShipRule;
import com.upedge.oms.modules.rules.vo.OrderShipRuleCountryVo;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

/**
 * @author author
 */
@Data
public class OrderShipRuleAddRequest{


    /**
    * 
    */
    @NotNull
    private String title;
    /**
    * 
    */
    private Double orderAmountMin = 0.00;
    /**
    * 
    */
    private Double orderAmountMax = 9999.00;
    /**
    * 
    */
    private Double freightMin = 0.00;
    /**
    * 
    */
    private Double freightMax = 9999.00;
    /**
    * 0=禁用 1= 启用 2=已删除
    */
    @NotNull
    private Integer state;
    /**
    * 
    */
    @NotNull
    private Long shippingMethodId;

    @NotNull
    private String shippingMethodName;

    /**
    * 
    */
    private Integer sequence = 1;

    @Size(min = 1)
    private List<OrderShipRuleCountryVo> countries;


    public OrderShipRule toOrderShipRule(Long customerId){
        OrderShipRule orderShipRules=new OrderShipRule();
        orderShipRules.setCustomerId(customerId);
        orderShipRules.setTitle(title);
        orderShipRules.setOrderAmountMin(orderAmountMin);
        orderShipRules.setOrderAmountMax(orderAmountMax);
        orderShipRules.setFreightMin(freightMin);
        orderShipRules.setFreightMax(freightMax);
        orderShipRules.setState(state);
        orderShipRules.setShippingMethodId(shippingMethodId);
        orderShipRules.setShippingMethodName(shippingMethodName);
        orderShipRules.setCreateTime(new Date());
        orderShipRules.setSequence(sequence);
        return orderShipRules;
    }

}
