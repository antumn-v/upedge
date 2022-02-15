package com.upedge.common.model.ship.vo;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * @author 海桐
 */
@Data
public class ShipDetail {

    @NotNull
    private Long methodId;

    private String methodName;

    private String methodCode;

    private String warehouseCode;

    private BigDecimal price;

    private BigDecimal weight;

    private String days;

    private Integer weightType;

    /**
     * 物流單元id
     */
    private Long shippingUtilId;

    private BigDecimal vatAmount;

    private BigDecimal serviceFee;

    @Override
    public boolean equals(Object o) {
        ShipDetail that = (ShipDetail) o;
        return methodId.equals(that.methodId) &&
                price.equals(that.price) &&
                weight.equals(that.weight);
    }

    @Override
    public int hashCode() {
        return Objects.hash(methodId, price, weight);
    }

    public ShipDetail(ShippingMethodRedis shippingMethodRedis) {
        this.methodId = shippingMethodRedis.getId();
        this.methodName = shippingMethodRedis.getName();
        this.shippingUtilId = 0L;
        this.warehouseCode = shippingMethodRedis.getWarehouseCode();
        this.methodCode = shippingMethodRedis.getMethodCode();
    }

    public ShipDetail() {
    }
}
