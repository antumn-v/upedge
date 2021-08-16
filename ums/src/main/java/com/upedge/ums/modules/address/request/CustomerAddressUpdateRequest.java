package com.upedge.ums.modules.address.request;

import com.upedge.ums.modules.address.entity.CustomerAddress;
import java.util.Date;
import lombok.Data;
/**
 * @author gx
 */
@Data
public class CustomerAddressUpdateRequest{

    /**
     * 
     */
    private Long customerId;
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
     * 
     */
    private String countryCode;
    /**
     * 
     */
    private String provinceCode;
    /**
     * 0 默认  1 非默认
     */
    private Integer isDefault;
    /**
     * 0=普通地址，1=账单地址
     */
    private Integer addressType;
    /**
     * 
     */
    private Date createTime;
    /**
     * 
     */
    private Date updateTime;

    public CustomerAddress toCustomerAddress(Long id){
        CustomerAddress customerAddress=new CustomerAddress();
        customerAddress.setId(id);
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
        customerAddress.setCountryCode(countryCode);
        customerAddress.setProvinceCode(provinceCode);
        customerAddress.setIsDefault(isDefault);
        customerAddress.setAddressType(addressType);
        customerAddress.setCreateTime(createTime);
        customerAddress.setUpdateTime(updateTime);
        return customerAddress;
    }

}
