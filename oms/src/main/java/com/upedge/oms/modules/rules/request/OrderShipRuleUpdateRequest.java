package com.upedge.oms.modules.rules.request;

import com.upedge.oms.modules.rules.entity.OrderShipRule;
import com.upedge.oms.modules.rules.vo.OrderShipRuleCountryVo;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * @author author
 */
@Data
public class OrderShipRuleUpdateRequest{

    /**
     *
     */
    @NotNull
    private String title;
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

    @NotNull
    private Long shipTemplateId;

    /**
     *
     */
    private Integer sequence = 1;

    private List<OrderShipRuleCountryVo> countries;

    public OrderShipRule toOrderShipRule(Long id){
        OrderShipRule orderShipRules=new OrderShipRule();
        orderShipRules.setId(id);
        orderShipRules.setTitle(title);
        orderShipRules.setOrderAmountMin(0.00);
        orderShipRules.setOrderAmountMax(99999.00);
        orderShipRules.setFreightMin(0.00);
        orderShipRules.setFreightMax(99999.00);
        orderShipRules.setState(state);
        orderShipRules.setShippingMethodId(shippingMethodId);
        orderShipRules.setShippingMethodName(shippingMethodName);
        orderShipRules.setUpdateTime(new Date());
        orderShipRules.setSequence(sequence);
        orderShipRules.setShipTemplateId(shipTemplateId);
        return orderShipRules;
    }

}
