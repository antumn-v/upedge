package com.upedge.ums.modules.address.request;

import com.upedge.ums.modules.address.entity.CustomerAddress;
import lombok.Data;

import java.util.Date;

/**
 * @author gx
 */
@Data
public class CustomerAddressAddRequest{


    /**
    * 
    */
    private String firstName;
    /**
    * 
    */
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
    private String country;
    /**
    * 
    */
    private String province;
    /**
    * 
    */
    private String city;
    /**
    * 
    */
    private String address1;
    /**
    * 
    */
    private String address2;
    /**
    * 
    */
    private String zip;
    /**
    * 0 默认  1 非默认
    */
    private Boolean isDefault;


    public CustomerAddress toCustomerAddress(Integer addressType,Long customerId){
        CustomerAddress customerAddress=new CustomerAddress();
        customerAddress.setCustomerId(customerId);
        customerAddress.setFirstName(firstName);
        customerAddress.setLastName(lastName);
        customerAddress.setEmail(email);
        customerAddress.setPhone(phone);
        customerAddress.setCountry(country);
        customerAddress.setProvince(province);
        customerAddress.setCity(city);
        customerAddress.setAddress1(address1);
        customerAddress.setAddress2(address2);
        customerAddress.setZip(zip);
        customerAddress.setIsDefault(isDefault);
        customerAddress.setAddressType(addressType);
        customerAddress.setCreateTime(new Date());
        customerAddress.setUpdateTime(new Date());
        return customerAddress;
    }

}
