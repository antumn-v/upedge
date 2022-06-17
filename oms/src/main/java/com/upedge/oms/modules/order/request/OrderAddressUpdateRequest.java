package com.upedge.oms.modules.order.request;

import com.upedge.oms.modules.order.entity.OrderAddress;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class OrderAddressUpdateRequest {

    @NotNull
    private String firstName;
    /**
     *
     */
    @NotNull
    private String lastName;
    /**
     *
     */
    private String email;
    /**
     *
     */
    private String phone;
    /**
     *
     */
    @NotNull
    private String country;
    /**
     *
     */
    @NotNull
    private String province;
    /**
     *
     */
    @NotNull
    private String city;
    /**
     *
     */
    @NotNull
    private String address1;
    /**
     *
     */
    private String address2;
    /**
     *
     */
    @NotNull
    private String zip;
    /**
     *
     */
    @NotNull
    private String countryCode;
    /**
     *
     */
    private String provinceCode;
    /**
     *
     */
    private Long orderId;

    public OrderAddress toOrderAddress(Long id){
        OrderAddress orderAddress=new OrderAddress();
        orderAddress.setId(id);
        orderAddress.setName(firstName + " " + lastName);
        orderAddress.setFirstName(firstName);
        orderAddress.setLastName(lastName);
        orderAddress.setEmail(email);
        orderAddress.setPhone(phone);
        orderAddress.setCountry(country);
        orderAddress.setProvince(province);
        orderAddress.setCity(city);
        orderAddress.setAddress1(address1);
        orderAddress.setAddress2(address2);
        orderAddress.setZip(zip);
        orderAddress.setCountryCode(countryCode);
        orderAddress.setProvinceCode(provinceCode);
        orderAddress.setOrderId(orderId);
        orderAddress.setIfEdited(true);
        return orderAddress;
    }
}
