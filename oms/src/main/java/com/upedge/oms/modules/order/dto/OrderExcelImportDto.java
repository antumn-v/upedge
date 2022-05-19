package com.upedge.oms.modules.order.dto;

import com.upedge.common.exception.CustomerException;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.lang.reflect.Field;

@Data
public class OrderExcelImportDto {

    @NotNull(message = "Order Number can not be null!")
    private String orderNumber;
    @NotNull(message = "Email can not be null!")
    private String email;
    @NotNull(message = "Name can not be null!")
    private String name;

    /**
     *
     */
    @NotNull(message = "Phone can not be null!")
    private String phone;
    /**
     *
     */
    @NotNull(message = "Country can not be null!")
    private String country;
    /**
     *
     */
    @NotNull(message = "Province can not be null!")
    private String province;
    /**
     *
     */
    @NotNull(message = "City can not be null!")
    private String city;
    /**
     *
     */
    @NotNull(message = "Address can not be null!")
    private String address1;
    /**
     *
     */
    private String address2;
    /**
     *
     */
    private String zip;
    @NotNull(message = "SKU Number can not be null!")
    private String sku;

    private String image;

    private String sellingLink;
    @NotNull(message = "Quantity Number can not be null!")
    private Integer quantity;


    public void checkNotNullFiled() throws CustomerException {
        Field[] fields = this.getClass().getDeclaredFields();
        for (Field field : fields) {
            NotNull annotation = field.getAnnotation(NotNull.class);
            if(annotation == null){
                continue;
            }
            try {
                Object value = field.get(this);
                if (value == null){
                    throw new CustomerException(annotation.message());
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

        }
    }

}
