package com.upedge.common.model.ship.vo;

import com.upedge.common.model.old.tms.ShippingUnit;
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

    private BigDecimal price;

    private BigDecimal weight;

    private String days;

    private Integer weightType;

    /**
     * 物流單元id
     */
    private Long shippingUtilId;

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


}
