package com.upedge.tms.modules.ship.dto;

import com.upedge.common.constant.Constant;
import com.upedge.tms.modules.ship.entity.ShippingUnit;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class ShipUnitExitImportDto {
    /**
     * 国家
     */
    private String country;
    /**
     * 时间区间
     */
    private String timeliness;
    /**
     * 重量区间
     */
    private String startWeight;
    private String endWeight;
    /**
     * 最低计费重
     * 首重=最低计费重*1000
     * 首重运费=单价*最低计费重
     */
    private String minBillingWeight;
    /**
     * 运费
     * 单价=运费/1000
     */
    private BigDecimal shipPrice;
    /**
     * 挂号费
     */
    private BigDecimal fixFee;

    public ShippingUnit toShipUnit(String toAreaId){
        ShippingUnit shippingUnit = new ShippingUnit();
        shippingUnit.setToAreaId(toAreaId);
        //日期区间
        String deliveryMinDay = timeliness.substring(0,timeliness.indexOf("-"));
        String deliveryMaxDay = timeliness.substring(timeliness.indexOf("-")+1);
        shippingUnit.setDeliveryMinDay(Integer.valueOf(deliveryMinDay));
        shippingUnit.setDeliveryMaxDay(Integer.valueOf(deliveryMaxDay));
        //重量区间
        shippingUnit.setStartWeight(new BigDecimal(startWeight));
        shippingUnit.setEndWeight(new BigDecimal(endWeight));
        //续重单位重量
        shippingUnit.setContinueUnitWeight(BigDecimal.ONE);
        //单价
        BigDecimal continueUnitPrice = shipPrice.divide(Constant.ONE_THOUSAND);
        shippingUnit.setContinueUnitPrice(continueUnitPrice);
        //首重
        BigDecimal firstWeight = new BigDecimal(minBillingWeight);
        shippingUnit.setFirstWeight(firstWeight);
        //首重运费=单价*首重
        BigDecimal firstFreight = continueUnitPrice.multiply(firstWeight);
        shippingUnit.setFirstFreight(firstFreight);
        //挂号费
        shippingUnit.setFixedFee(fixFee);
        //折扣
        shippingUnit.setDiscount(BigDecimal.ONE);
        shippingUnit.setCreateTime(new Date());
        shippingUnit.setState(1);
        return shippingUnit;

    }

    public static void main(String[] args) {
//        String weight = "0<W≤0.1";
//        String firstWeight = weight.substring(0,weight.indexOf("<"));
//        String endWeight = weight.substring(weight.indexOf("≤") +1);
//        System.out.println(firstWeight);
//        System.out.println(endWeight);
//        String timeliness = "6-12";
//        String deliveryMinDay = timeliness.substring(0,timeliness.indexOf("-"));
//        String deliveryMaxDay = timeliness.substring(timeliness.indexOf("-")+1);
//        System.out.println(deliveryMinDay);
//        System.out.println(deliveryMaxDay);
    }
}
