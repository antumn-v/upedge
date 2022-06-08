package com.upedge.tms.modules.ship.request;

import com.upedge.tms.modules.ship.entity.ShippingUnit;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author gx
 */
@Data
public class ShippingUnitAddRequest{


    /**
    * 运输方式id
    */
    @NotNull
    private Long methodId;
    /**
    * 
    */
    private String methodName;
    /**
    * 目的地id
    */
    @NotNull
    private String toAreaId;
    /**
    * 区间开始重量
    */
    @NotNull
    private BigDecimal startWeight;
    /**
    * 区间结束重量
    */
    @NotNull
    private BigDecimal endWeight;
    /**
    * 固定费+挂号费
    */
    @NotNull
    private BigDecimal fixedFee;
    /**
    * 首重
    */
    @NotNull
    private BigDecimal firstWeight;
    /**
    * 首重运费
    */
    @NotNull
    private BigDecimal firstFreight;
    /**
    * 续重单位重量
    */
    @NotNull
    private BigDecimal continueUnitWeight;
    /**
    * 续重单价
    */
    @NotNull
    private BigDecimal continueUnitPrice;
    /**
    * 预计到达时间
    */
    @NotNull
    private Integer deliveryMinDay;
    /**
    * 
    */
    @NotNull
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
    * 0:禁用 1:启用
    */
    private Integer state;

    public ShippingUnit toShippingUnit(){
        ShippingUnit shippingUnit=new ShippingUnit();
        shippingUnit.setMethodId(methodId);
        shippingUnit.setMethodName(methodName);
        shippingUnit.setToAreaId(toAreaId);
        shippingUnit.setStartWeight(startWeight);
        shippingUnit.setEndWeight(endWeight);
        shippingUnit.setFixedFee(fixedFee);
        shippingUnit.setFirstWeight(firstWeight);
        shippingUnit.setFirstFreight(firstFreight);
        shippingUnit.setContinueUnitWeight(continueUnitWeight);
        shippingUnit.setContinueUnitPrice(continueUnitPrice);
        shippingUnit.setDeliveryMinDay(deliveryMinDay);
        shippingUnit.setDeliveryMaxDay(deliveryMaxDay);
        if (discount == null){
            shippingUnit.setDiscount(BigDecimal.ONE);
        }else {
            shippingUnit.setDiscount(discount);
        }
        shippingUnit.setRemarks(remarks);
        shippingUnit.setCreateTime(createTime);
        shippingUnit.setState(1);
        return shippingUnit;
    }

}
