package com.upedge.tms.modules.ship.request;

import com.upedge.tms.modules.ship.entity.ShippingUnit;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author author
 */
@Data
public class ShippingUnitUpdateRequest{

    /**
     * 运输方式id
     */
    private Long methodId;
    /**
     * 
     */
    private String methodName;
    /**
     * 始发地id
     */
    private String fromAreaId;
    /**
     * 目的地id
     */
    private String toAreaId;
    /**
     * 区间开始重量
     */
    private BigDecimal startWeight;
    /**
     * 区间结束重量
     */
    private BigDecimal endWeight;
    /**
     * 固定费+挂号费
     */
    private BigDecimal fixedFee;
    /**
     * 各重费
     */
    private BigDecimal weightCharge;
    /**
     * 预计到达时间
     */
    private Integer deliveryMinDay;
    /**
     * 
     */
    private Integer deliveryMaxDay;
    /**
     * 折扣
     */
    private BigDecimal discount;
    /**
     * 
     */
    private String remarks;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 
     */
    private Integer state;

    public ShippingUnit toShippingUnit(Long id){
        ShippingUnit shippingUnit=new ShippingUnit();
        shippingUnit.setId(id);
        shippingUnit.setMethodId(methodId);
        shippingUnit.setMethodName(methodName);
        shippingUnit.setFromAreaId(fromAreaId);
        shippingUnit.setToAreaId(toAreaId);
        shippingUnit.setStartWeight(startWeight);
        shippingUnit.setEndWeight(endWeight);
        shippingUnit.setFixedFee(fixedFee);
        shippingUnit.setWeightCharge(weightCharge);
        shippingUnit.setDeliveryMinDay(deliveryMinDay);
        shippingUnit.setDeliveryMaxDay(deliveryMaxDay);
        shippingUnit.setDiscount(discount);
        shippingUnit.setRemarks(remarks);
        shippingUnit.setCreateTime(createTime);
        shippingUnit.setState(state);
        return shippingUnit;
    }

}
