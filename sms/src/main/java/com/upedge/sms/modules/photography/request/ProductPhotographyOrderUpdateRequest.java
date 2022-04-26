package com.upedge.sms.modules.photography.request;

import com.upedge.sms.modules.photography.entity.ProductPhotographyOrder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;
/**
 * @author gx
 */
@Data
public class ProductPhotographyOrderUpdateRequest{

    @NotNull
    private Long id;

    @NotNull
    private BigDecimal amount;


    public ProductPhotographyOrder toProductPhotographyOrder(){
        ProductPhotographyOrder productPhotographyOrder=new ProductPhotographyOrder();
        productPhotographyOrder.setId(id);
        productPhotographyOrder.setPayAmount(amount);
        productPhotographyOrder.setUpdateTime(new Date());
        return productPhotographyOrder;
    }

}
